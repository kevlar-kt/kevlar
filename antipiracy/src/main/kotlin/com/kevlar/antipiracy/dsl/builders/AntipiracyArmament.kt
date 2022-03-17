package com.kevlar.antipiracy.dsl.builders

import com.kevlar.antipiracy.dsl.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.DslBuilder

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