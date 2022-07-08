package com.kevlar.antipiracy

import com.kevlar.antipiracy.dsl.language.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.language.DslBuilder
import com.kevlar.antipiracy.dsl.settings.scan.ScanConfiguration
import com.kevlar.antipiracy.dsl.settings.scan.ScanConfigurationsBuilder

/**
 * Holds settings for [KevlarAntipiracy]
 * */
public data class AntipiracySettings(
    val scanConfiguration: ScanConfiguration
)

@AntipiracyDslMarker
public class AntipiracySettingsBuilder : DslBuilder<AntipiracySettings>() {
    private var scanConfiguration: ScanConfiguration = ScanConfiguration.default()

    public fun scan(block: ScanConfigurationsBuilder.() -> Unit) {
        scanConfiguration = ScanConfigurationsBuilder().apply(block).build()
    }

    override fun build(): AntipiracySettings = AntipiracySettings(scanConfiguration)
}