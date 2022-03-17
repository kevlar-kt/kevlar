package com.kevlar.antipiracy.dsl.builders

import com.kevlar.antipiracy.dsl.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.DslBuilder

/**
 * Contains global scan details
 * */
public class ScanConfiguration(
    private val pirate: PirateSoftwareScan,
    private val stores: PirateStoreScan,
    private val custom: CustomScan,
) {

    public companion object {
        public fun default(): ScanConfiguration = ScanConfiguration(
            PirateSoftwareScan(enabled = false),
            PirateStoreScan(enabled = false),
            CustomScan(enabled = false)
        )
    }
}


@AntipiracyDslMarker
public class ScanConfigurationsBuilder : DslBuilder<ScanConfiguration>() {
    private var pirate = PirateSoftwareScan(enabled = false)
    private var store = PirateStoreScan(enabled = false)
    private var custom = CustomScan(enabled = false)

    public fun pirate(block: PirateSoftwareScanBuilder.() -> Unit) {
        pirate = PirateSoftwareScanBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun store(block: PirateStoreScanBuilder.() -> Unit) {
        store = PirateStoreScanBuilder().apply {
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

    override fun build(): ScanConfiguration = ScanConfiguration(pirate, store, custom)
}
