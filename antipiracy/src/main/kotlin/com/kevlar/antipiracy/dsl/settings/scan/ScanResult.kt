package com.kevlar.antipiracy.dsl.settings.scan

import com.kevlar.antipiracy.dataset.DatasetEntry

/**
 * Holds the found pirate software
 * */
public data class ScanResult(
    val detectedEntries: Set<DatasetEntry>
) {
    public companion object {
        public fun empty(): ScanResult = ScanResult(setOf())
    }

    public fun isClear(): Boolean = detectedEntries.isEmpty()
}