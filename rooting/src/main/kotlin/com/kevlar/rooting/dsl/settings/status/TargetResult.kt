package com.kevlar.rooting.dsl.settings.status

import com.kevlar.rooting.dataset.DetectableSystemStatus

/**
 * Holds the found system modifications.
 * This will be included in failed [StatusAttestation]s.
 * */
public data class StatusResult(
    val detectedStatus: Set<DetectableSystemStatus>
) {
    public constructor(list: List<DetectableSystemStatus>) : this(list.toSet())

    public fun isClear(): Boolean = detectedStatus.isEmpty()

    public companion object {
        public fun empty(): StatusResult = StatusResult(setOf())
    }
}