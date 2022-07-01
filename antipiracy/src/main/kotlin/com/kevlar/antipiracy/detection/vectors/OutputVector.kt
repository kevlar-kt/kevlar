package com.kevlar.antipiracy.detection.vectors

import com.kevlar.antipiracy.detection.dataset.DatasetEntry

/**
 * Output abstraction for vector operations
 * */
internal data class OutputVector(
    val matchingDataset: DatasetEntry?
) {
    fun isEmpty() = matchingDataset == null
    fun isNotEmpty() = matchingDataset != null
}