package com.kevlar.antipiracy.dsl.settings.scan

import com.kevlar.antipiracy.dsl.language.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.language.DslBuilder

/**
 * Contains global scan details
 * */
public class ScanConfiguration(
    public val pirate: PirateSoftwareScan,
    public val stores: PirateStoreScan,
    public val collateral: CollateralScan,
    public val custom: CustomScan,
) {

    public companion object {
        public fun default(): ScanConfiguration = ScanConfiguration(
            PirateSoftwareScan(enabled = false),
            PirateStoreScan(enabled = false),
            CollateralScan(enabled = false),
            CustomScan(enabled = false)
        )
    }
}


@AntipiracyDslMarker
public class ScanConfigurationsBuilder : DslBuilder<ScanConfiguration>() {
    private var pirate = PirateSoftwareScan(enabled = false)
    private var store = PirateStoreScan(enabled = false)
    private var collateral = CollateralScan(enabled = false)
    private var custom = CustomScan(enabled = false)

    public fun pirate(block: PirateSoftwareScanBuilder.() -> Unit = {}) {
        pirate = PirateSoftwareScanBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun store(block: PirateStoreScanBuilder.() -> Unit = {}) {
        store = PirateStoreScanBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun collateral(block: CollateralScanBuilder.() -> Unit = {}) {
        collateral = CollateralScanBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun custom(block: CustomScanBuilder.() -> Unit) {
        custom = CustomScanBuilder().apply {
            block()
            enable()
        }.build()
    }

    /**
     * Builds the scan configuration
     * */
    override fun build(): ScanConfiguration = ScanConfiguration(pirate, store, collateral, custom)
}
