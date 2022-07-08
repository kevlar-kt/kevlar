package com.kevlar.rooting.dsl.settings.target

import com.kevlar.rooting.dataset.DetectableSystemTarget

/**
 * Holds the found system modifications.
 * This will be included in failed [RootingAttestation]s.
 * */
public data class TargetResult(
    val detectedTargets: Set<DetectableSystemTarget>
) {
    public constructor(detectedTargets: List<DetectableSystemTarget>) : this(detectedTargets.toSet())

    public fun isClear(): Boolean = detectedTargets.isEmpty()

    public companion object {
        public fun empty(): TargetResult = TargetResult(setOf())
    }
}