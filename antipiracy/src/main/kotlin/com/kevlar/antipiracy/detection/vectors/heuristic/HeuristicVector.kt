package com.kevlar.antipiracy.detection.vectors.heuristic

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.detection.vectors.AntipiracyVector
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector

internal class HeuristicVector(private val inputVector: InputVector) :
    AntipiracyVector(inputVector) {

    private fun runDetection(
        applicationInfo: ApplicationInfo,
        targets: Array<MatchableHeuristicDatasetEntry>
    ): OutputVector {
        targets.forEach { dataSetUnit ->
            // Can have multiple detection policies
            dataSetUnit.detectionPolicies.forEach { detectionPolicy ->
                when (detectionPolicy) {
                    is DetectionPolicy.PackageNameDetection -> {
                        // We match the blacklisted package names and check if any is present in applicationInfo
                        if (detectionPolicy.packageNames.any { it == applicationInfo.packageName }) {
                            return OutputVector(dataSetUnit.datasetEntry)
                        }
                    }

                    is DetectionPolicy.PackageNameRegex -> {
                        // We match (with a regex) the blacklisted package name and check if any is present in applicationInfo
                        if (detectionPolicy.regex.toRegex().matches(applicationInfo.packageName)) {
                            return OutputVector(dataSetUnit.datasetEntry)
                        }
                    }

                    is DetectionPolicy.ClassNameNameRegex -> {
                        // We match (with a regex) the blacklisted class name and check if any is present in applicationInfo
                        val className: String? = applicationInfo.className

                        if (className != null && detectionPolicy.regex.toRegex().matches(applicationInfo.className)) {
                            return OutputVector(dataSetUnit.datasetEntry)
                        }
                    }

                    is DetectionPolicy.LabelNameRegex -> {
                        // We match (with a regex) the blacklisted label name and check if any is present in applicationInfo
                        if (detectionPolicy.regex.toRegex().matches(applicationInfo.nonLocalizedLabel)) {
                            return OutputVector(dataSetUnit.datasetEntry)
                        }
                    }
                }
            }
        }

        return OutputVector(matchingDataset = null)
    }

    override suspend fun probe(applicationInfo: ApplicationInfo): OutputVector {
        if (inputVector.scanConfiguration.pirate.enabled) {
            runDetection(applicationInfo, HeuristicDataset.identifiableHeuristicsPirateApps).let {
                if (it.isNotEmpty()) {
                    return it
                }
            }

            runDetection(applicationInfo, HeuristicDataset.nonIdentifiableHeuristicApps).let {
                if (it.isNotEmpty()) {
                    return it
                }
            }
        }

        if (inputVector.scanConfiguration.stores.enabled) {
            runDetection(applicationInfo, HeuristicDataset.identifiableHeuristicStores).let {
                if (it.isNotEmpty()) {
                    return it
                }
            }
        }

        if (inputVector.scanConfiguration.collateral.enabled) {
            runDetection(applicationInfo, HeuristicDataset.identifiableHeuristicStores).let {
                if (it.isNotEmpty()) {
                    return it
                }
            }
        }

        if (inputVector.scanConfiguration.custom.enabled) {
            // TODO custom analysis
        }

        return OutputVector(matchingDataset = null)
    }

    companion object {
        private const val TAG = "HeuristicVector"
    }
}