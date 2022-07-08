package com.kevlar.antipiracy.detection.vectors.heuristic

import com.kevlar.antipiracy.dataset.DatasetEntry
import com.kevlar.antipiracy.dataset.MatchableDatasetEntry

/**
 * Associates a [DatasetEntry] with a list of [DetectionPolicy]
 * for heuristic scans.
 * */
internal data class MatchableHeuristicDatasetEntry(
    override val datasetEntry: DatasetEntry,
    val detectionPolicies: List<DetectionPolicy>
) : MatchableDatasetEntry()