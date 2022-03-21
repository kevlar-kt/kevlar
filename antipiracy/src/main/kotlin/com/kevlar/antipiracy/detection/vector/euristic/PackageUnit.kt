package com.kevlar.antipiracy.detection.vector.euristic

import com.kevlar.antipiracy.detection.dataset.DatasetEntry

/**
 * Synthesizes and identifies a target application.
 * */
internal data class HeuristicUnit(
    val datasetEntry: DatasetEntry,
    val detectionPolicy: List<HeuristicDetectionPolicy>
)

internal sealed class HeuristicDetectionPolicy {
    data class PackageNameDetection(
        val packageNames: List<String>
    ) : HeuristicDetectionPolicy()


    data class PackageNameRegex(
        val regex: String
    ) : HeuristicDetectionPolicy()


    companion object {

        /**
         * This creates a list with only 1 element, which contains a PackageNameDetection object
         * with the given packages listed in it.
         * */
        fun packageName(
            vararg packages: String
        ): List<PackageNameDetection> = listOf(PackageNameDetection(packages.toList()))

        fun regex(
            regex: String
        ): PackageNameRegex = PackageNameRegex(regex)
    }
}