package com.kevlar.antipiracy.detection.vectors.heuristic

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.detection.dataset.DatasetUnit
import com.kevlar.antipiracy.detection.dataset.DetectionPolicy
import com.kevlar.antipiracy.detection.vectors.AntipiracyVector
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector

internal class HeuristicVector(private val inputVector: InputVector) :
    AntipiracyVector(inputVector) {

    private fun runDetection(
        applicationInfo: ApplicationInfo,
        dataset: Array<DatasetUnit>
    ): OutputVector {
        dataset.forEach { dataSetUnit ->
            // Can have multiple detection policies
            dataSetUnit.detectionPolicies.forEach { detectionPolicy ->
                when (detectionPolicy) {
                    is DetectionPolicy.PackageNameDetection -> {
                        // We match the blacklisted package names and check if any is present in applicationInfo
                        if (detectionPolicy.packageNames.any { it.contains(applicationInfo.packageName) }) {
                            return OutputVector(dataSetUnit.datasetEntry)
                        }
                    }
                    is DetectionPolicy.PackageNameRegex -> {
                        // We match the blacklisted package names and check if any is present in applicationInfo
                        if (detectionPolicy.regex.toRegex().matches(applicationInfo.packageName)) {
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
            runDetection(applicationInfo, HeuristicDataset.identifiableHeuristicStores).let {
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

        if (inputVector.scanConfiguration.custom.enabled) {

        }


        return OutputVector(matchingDataset = null)
    }
}