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
import com.kevlar.antipiracy.dataset.Threat
import com.kevlar.antipiracy.dataset.ScanEntry
import com.kevlar.antipiracy.detection.vectors.AntipiracyVector
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector

internal class HeuristicVector(private val inputVector: InputVector) : AntipiracyVector(inputVector) {

    private fun runDetectionForPackage(
        packageToAnalyze: ApplicationInfo,
        heuristics: List<HeuristicThreatDetectionSuite>
    ): OutputVector {
        val packageNameToAnalyze = packageToAnalyze.packageName

        // First thing first, we check whitelists / blacklists
        val whitelist = inputVector.scanConfiguration.whitelist
        val blacklist = inputVector.scanConfiguration.blacklist

        if (whitelist.contains(packageToAnalyze.packageName)) {
            return OutputVector(scanEntry = null)
        }

        if (blacklist.contains(packageToAnalyze.packageName)) {
            return OutputVector(scanEntry = ScanEntry(Threat.BLACKLIST, packageToAnalyze.packageName,"Manually blacklisted package: [packageName=${packageToAnalyze.packageName} in blacklist=${blacklist}]"))
        }

        // We take our target detections and run each one on the analyzed package
        heuristics.forEach { detectionSuite ->
            val threat = detectionSuite.threat

            // Can have multiple detection policies
            detectionSuite.detectors.forEach { detector ->
                when (detector) {
                    is DetectionStrategy.PackageNameDetection -> {
                        // We match the blacklisted package names and check if any is present in applicationInfo
                        detector.packageNames.filter { it == packageToAnalyze.packageName }.forEach {
                            return OutputVector(
                                scanEntry = ScanEntry(
                                    threat,
                                    packageNameToAnalyze,
                                    "Found [packageName=$packageNameToAnalyze] through PackageNameDetection"
                                )
                            )
                        }
                    }

                    is DetectionStrategy.PackageNameRegex -> {
                        // We match (with a regex) the blacklisted package name and check if any is present in applicationInfo
                        if (detector.pnRegex.toRegex().matches(packageToAnalyze.packageName)) {
                            return OutputVector(scanEntry = ScanEntry(
                                threat,
                                packageNameToAnalyze,
                                "Found [packageName=$packageNameToAnalyze] through PackageNameRegex(regex=${detector.pnRegex})"
                            ))
                        }
                    }

                    is DetectionStrategy.ClassNameNameRegex -> {
                        // We match (with a regex) the blacklisted class name and check if any is present in applicationInfo
                        val className: String? = packageToAnalyze.className

                        if (className != null && detector.cnRegex.toRegex().matches(packageToAnalyze.className)) {
                            return OutputVector(scanEntry = ScanEntry(
                                threat,
                                packageNameToAnalyze,
                                "Found [className=${packageToAnalyze.className}, packageName=$packageNameToAnalyze] through ClassNameNameRegex(regex=${detector.cnRegex})"
                            ))
                        }
                    }

                    is DetectionStrategy.LabelNameRegex -> {
                        // We match (with a regex) the blacklisted label name and check if any is present in applicationInfo
                        if (detector.lnRegex.toRegex().matches(packageToAnalyze.nonLocalizedLabel)) {
                            return OutputVector(scanEntry = ScanEntry(
                                threat,
                                packageNameToAnalyze,
                                "Found [label=${packageToAnalyze.nonLocalizedLabel}, packageName=$packageNameToAnalyze] through LabelNameRegex(regex=${detector.lnRegex})"
                            ))
                        }
                    }
                }
            }
        }

        return OutputVector(scanEntry = null)
    }

    override suspend fun probe(applicationInfo: ApplicationInfo): OutputVector {
        val heuristics: List<HeuristicThreatDetectionSuite> = buildList {
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

        return runDetectionForPackage(applicationInfo, heuristics)
    }

    companion object {
        private const val TAG = "HeuristicVector"
    }
}