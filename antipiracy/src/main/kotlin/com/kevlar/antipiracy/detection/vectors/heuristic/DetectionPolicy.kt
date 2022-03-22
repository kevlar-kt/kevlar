package com.kevlar.antipiracy.detection.vectors.heuristic

import com.kevlar.antipiracy.detection.dataset.DatasetEntry

/**
 * Technique used to detect a single [DatasetEntry].
 * */
internal sealed class DetectionPolicy {
    /**
     * Software package name matching
     * */
    data class PackageNameDetection(
        val packageNames: List<String>
    ) : DetectionPolicy()

    /**
     * Software package name regex.
     * Separated from [PackageNameDetection] to speed up
     * package search which covers most of the use cases.
     * */
    data class PackageNameRegex(
        val regex: String
    ) : DetectionPolicy()

    /**
     * Application nonLocalizedLabel matching
     * */
    data class LabelNameRegex(
        val regex: String
    ) : DetectionPolicy()

    /**
     * Application specific main class name matching
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