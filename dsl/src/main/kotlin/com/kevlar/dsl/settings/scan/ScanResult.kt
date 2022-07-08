package com.kevlar.dsl.settings.scan

import com.kevlar.antipiracy.detection.dataset.DatasetEntry

public data class ScanResult(
    val detectedEntries: Set<DatasetEntry>
) {
    public companion object {
        public fun empty(): ScanResult = ScanResult(setOf())
    }

    public fun isClear(): Boolean = detectedEntries.isEmpty()
}