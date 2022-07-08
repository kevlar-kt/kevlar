package com.kevlar.rooting.dsl.settings.target

import com.kevlar.rooting.dataset.SystemModificationsTargetDataset

/**
 * Holds the found system modifications. This will be included in the [RootingAttestation]
 * */
public data class TargetResult(
    val detectedEntries: Set<SystemModificationsTargetDataset>
) {
    public companion object {
        public fun empty(): TargetResult = TargetResult(setOf())
    }

    public fun isClear(): Boolean = detectedEntries.isEmpty()
}