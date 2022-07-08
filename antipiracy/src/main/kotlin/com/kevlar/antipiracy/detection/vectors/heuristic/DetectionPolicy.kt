package com.kevlar.antipiracy.detection.vectors.heuristic

import com.kevlar.antipiracy.dataset.DatasetEntry

/**
 * Technique used to detect a single [DatasetEntry].
 * */
internal sealed class DetectionPolicy {
    /**
     * Package name matching.
     *
     * This has been separated from [PackageNameDetection]
     * to speed up package search (since we could match strings
     * with regex but it would take more: most of our dataset
     * is basic string matching) which covers most of the use cases.
     * */
    data class PackageNameDetection(
        val packageNames: List<String>
    ) : DetectionPolicy()

    /**
     * Package name regex.
     *
     * Runs the regex and checks if the entire package name
     * matches with the given pattern.
     * */
    data class PackageNameRegex(
        val regex: String
    ) : DetectionPolicy()

    /**
     * nonLocalizedLabel matching
     *
     * Runs the regex and checks if the entire non-localized label
     * matches with the given pattern.
     * */
    data class LabelNameRegex(
        val regex: String
    ) : DetectionPolicy()

    /**
     * Main class name matching
     *
     * Runs the regex and checks if the entire class name
     * matches with the given pattern.
     * */
    data class ClassNameNameRegex(
        val regex: String
    ) : DetectionPolicy()

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