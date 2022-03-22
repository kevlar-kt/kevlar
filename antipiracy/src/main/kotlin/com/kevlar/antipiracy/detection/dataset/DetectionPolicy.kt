package com.kevlar.antipiracy.detection.dataset

/**
 * Used to specify which technique should be used to detect a given package
 * */
internal sealed class DetectionPolicy {
    data class PackageNameDetection(
        val packageNames: List<String>
    ) : DetectionPolicy()

    data class PackageNameRegex(
        val regex: String
    ) : DetectionPolicy()

    data class LabelNameRegex(
        val regex: String
    ) : DetectionPolicy()

    data class ClassNameNameRegex(
        val regex: String
    ) : DetectionPolicy()

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