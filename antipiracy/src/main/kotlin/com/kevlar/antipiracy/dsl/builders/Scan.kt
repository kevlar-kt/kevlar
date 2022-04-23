package com.kevlar.antipiracy.dsl.builders

import com.kevlar.antipiracy.detection.dataset.DatasetEntry
import com.kevlar.antipiracy.dsl.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.DslBuilder

/**
 * [Scan] structure
 * */
public abstract class Scan {
    public abstract val enabled: Boolean
}


public data class PirateSoftwareScan(
    override val enabled: Boolean
) : Scan()

public data class PirateStoreScan(
    override val enabled: Boolean
): Scan()

public data class CollateralScan(
    override val enabled: Boolean
): Scan()

public data class CustomScan(
    override val enabled: Boolean
): Scan()




public data class ScanResult(
    val detectedEntries: Set<DatasetEntry>
) {
    public companion object {
        public fun empty(): ScanResult = ScanResult(setOf())
    }

    public fun isClear(): Boolean = detectedEntries.isEmpty()
}



@AntipiracyDslMarker
public class PirateSoftwareScanBuilder() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): PirateSoftwareScan = PirateSoftwareScan(enabled)
}


@AntipiracyDslMarker
public class PirateStoreScanBuilder() : DslBuilder<PirateStoreScan>() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public override fun build(): PirateStoreScan = PirateStoreScan(enabled)
}


@AntipiracyDslMarker
public class CollateralScanBuilder() : DslBuilder<CollateralScan>() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public override fun build(): CollateralScan = CollateralScan(enabled)
}


@AntipiracyDslMarker
public class CustomScanBuilder() : DslBuilder<CustomScan>() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public override fun build(): CustomScan = CustomScan(enabled)
}