/*
 * Designed and developed by Kevlar Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevlar.antipiracy.detection.vectors.heuristic

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.dataset.DatasetEntry
import com.kevlar.antipiracy.detection.vectors.AntipiracyVector
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector

internal class HeuristicVector(private val inputVector: InputVector) : AntipiracyVector(inputVector) {

    private fun runDetection(
        applicationInfo: ApplicationInfo,
        targets: List<MatchableHeuristicDatasetEntry>
    ): OutputVector {
        val whitelist = inputVector.scanConfiguration.whitelist
        val blacklist = inputVector.scanConfiguration.blacklist

        if (whitelist.contains(applicationInfo.packageName)) {
            return OutputVector(matchingDataset = null)
        }

        if (blacklist.contains(applicationInfo.packageName)) {
            return OutputVector(matchingDataset = DatasetEntry.BLACKLIST)
        }

        targets.forEach { dataSetUnit ->

            // Can have multiple detection policies
            dataSetUnit.detectionPolicies.forEach { detectionPolicy ->
                val matchingDataset = dataSetUnit.datasetEntry

                when (detectionPolicy) {
                    is DetectionPolicy.PackageNameDetection -> {
                        // We match the blacklisted package names and check if any is present in applicationInfo
                        if (detectionPolicy.packageNames.any { it == applicationInfo.packageName }) {
                            return OutputVector(matchingDataset)
                        }
                    }

                    is DetectionPolicy.PackageNameRegex -> {
                        // We match (with a regex) the blacklisted package name and check if any is present in applicationInfo
                        if (detectionPolicy.regex.toRegex().matches(applicationInfo.packageName)) {
                            return OutputVector(matchingDataset)
                        }
                    }

                    is DetectionPolicy.ClassNameNameRegex -> {
                        // We match (with a regex) the blacklisted class name and check if any is present in applicationInfo
                        val className: String? = applicationInfo.className

                        if (className != null && detectionPolicy.regex.toRegex()
                                .matches(applicationInfo.className)
                        ) {
                            return OutputVector(matchingDataset)
                        }
                    }

                    is DetectionPolicy.LabelNameRegex -> {
                        // We match (with a regex) the blacklisted label name and check if any is present in applicationInfo
                        if (detectionPolicy.regex.toRegex()
                                .matches(applicationInfo.nonLocalizedLabel)
                        ) {
                            return OutputVector(matchingDataset)
                        }
                    }
                }
            }
        }

        return OutputVector(matchingDataset = null)
    }

    override suspend fun probe(applicationInfo: ApplicationInfo): OutputVector {
        val heuristics: List<MatchableHeuristicDatasetEntry> = buildList {
            if (inputVector.scanConfiguration.pirate.enabled) {
                addAll(HeuristicDataset.identifiableHeuristicsPirateApps)
                addAll(HeuristicDataset.nonIdentifiableHeuristicPirateApps)
            }

            if (inputVector.scanConfiguration.stores.enabled) {
                addAll(HeuristicDataset.identifiableHeuristicStores)
            }

            if (inputVector.scanConfiguration.collateral.enabled) {
                addAll(HeuristicDataset.collateralPirateApps)
            }

            /*
            if (inputVector.scanConfiguration.custom.enabled) {
                // implement custom scan
            }
            */
        }

        return runDetection(applicationInfo, heuristics)
    }

    companion object {
        private const val TAG = "HeuristicVector"
    }
}