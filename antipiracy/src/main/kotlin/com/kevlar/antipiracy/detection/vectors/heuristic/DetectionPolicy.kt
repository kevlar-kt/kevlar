package com.kevlar.antipiracy.detection.vectors.heuristic

import com.kevlar.antipiracy.detection.dataset.DatasetEntry

/**
 * Technique used to detect a single [DatasetEntry].
 * */
internal sealed class DetectionPolicy {
    /**
     * Package name matching
     * */
    data class PackageNameDetection(
        val packageNames: List<String>
    ) : DetectionPolicy()

    /**
     * Package name regex.
     * Separated from [PackageNameDetection] to speed up
     * package search which covers most of the use cases.
     * */
    data class PackageNameRegex(
        val regex: String
    ) : DetectionPolicy()

    /**
     * nonLocalizedLabel matching
     * */
    data class LabelNameRegex(
        val regex: String
    ) : DetectionPolicy()

    /**
     * Main class name matching
     * */
    data class ClassNameNameRegex(
        val regex: String
    ) : DetectionPolicy()

    /**
     * Package name
     * */
    data class TokenizedPackageName(
        val tokens: List<PackageToken>
    ) : DetectionPolicy() {
        sealed class PackageToken {
            class Size(val size: Int) : PackageToken()
            class Content(val content: String) : PackageToken()
        }
    }

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