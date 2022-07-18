package com.kevlar.integrity.dsl.settings.scan

import com.kevlar.integrity.dataset.IntegrityElement

public data class CheckResult(
    val detectedElements: Set<IntegrityElement>
) {
    public constructor(detectedEntries: List<IntegrityElement>) : this(detectedEntries.toSet())

    public companion object {
        public fun empty(): CheckResult = CheckResult(setOf())
    }

    public fun isClear(): Boolean = detectedElements.isEmpty()
}