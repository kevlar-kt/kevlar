package com.kevlar.antipiracy

import com.kevlar.antipiracy.dsl.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.DslBuilder
import com.kevlar.antipiracy.dsl.builders.ScanConfiguration
import com.kevlar.antipiracy.dsl.builders.ScanConfigurationsBuilder

/**
 *
 * */
public data class AntipiracyArmament(
    val scanConfiguration: ScanConfiguration
)

@AntipiracyDslMarker
public class AntipiracyArmamentBuilder : DslBuilder<AntipiracyArmament>() {
    private var scanConfiguration: ScanConfiguration = ScanConfiguration.default()

    public fun scan(block: ScanConfigurationsBuilder.() -> Unit) {
        scanConfiguration = ScanConfigurationsBuilder().apply(block).build()
    }

    override fun build(): AntipiracyArmament = AntipiracyArmament(scanConfiguration)
}