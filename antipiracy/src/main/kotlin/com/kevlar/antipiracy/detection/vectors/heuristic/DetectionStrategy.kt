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

import com.kevlar.antipiracy.dataset.Threat

/**
 * Technique used to detect a single [Threat].
 * */
internal sealed class DetectionStrategy {
    /**
     * **Package name matching**.
     *
     * This has been separated from [PackageNameDetection]
     * to speed up package search (since we could match strings
     * with regex but it would take more: most of our dataset
     * is basic string matching) which covers most of the use cases.
     * */
    data class PackageNameDetection(
        val packageNames: List<String>
    ) : DetectionStrategy()

    /**
     * **Package name regex**.
     *
     * Runs the regex and checks if the entire package name
     * matches with the given pattern.
     * */
    data class PackageNameRegex(
        val pnRegex: String
    ) : DetectionStrategy()

    /**
     * **Label matching**
     *
     * Runs the regex and checks if the entire non-localized label (nonLocalizedLabel)
     * matches with the given pattern.
     * */
    data class LabelNameRegex(
        val lnRegex: String
    ) : DetectionStrategy()

    /**
     * **Main class name matching**
     *
     * Runs the regex and checks if the entire class name
     * matches with the given pattern.
     * */
    data class ClassNameNameRegex(
        val cnRegex: String
    ) : DetectionStrategy()

    companion object {
        /**
         * This creates a list with only 1 element, which contains a PackageNameDetection object
         * with the given packages listed in it.
         * */
        fun packageNames(
            vararg packages: String
        ): List<PackageNameDetection> = listOf(PackageNameDetection(packages.toList()))
    }
}