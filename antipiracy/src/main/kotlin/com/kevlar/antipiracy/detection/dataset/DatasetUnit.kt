package com.kevlar.antipiracy.detection.dataset

/**
 * Synthesizes and identifies a target application.
 * */
internal data class DatasetUnit(
    val datasetEntry: DatasetEntry,
    val detectionPolicies: List<DetectionPolicy>
)