package com.kevlar.antipiracy

import com.kevlar.antipiracy.dsl.language.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.language.DslBuilder
import com.kevlar.antipiracy.dsl.settings.scan.ScanConfiguration
import com.kevlar.antipiracy.dsl.settings.scan.ScanConfigurationsBuilder

/**
 * Holds settings for [KevlarAntipiracy].
 *
 * Current available settings include:
 * - Scan parameters (allows to target the detection of specific pirate software)
 * */
public data class AntipiracySettings(
    val scanConfiguration: ScanConfiguration
)

/**
 * Builds settings holder.
 *
 * This is the core of the DSL, all the functions inside this class can be used
 * as dsl construction blocks to create parameters which will be finalized in
 * the [AntipiracySettings] build.
 * */
@AntipiracyDslMarker
public class AntipiracySettingsBuilder : DslBuilder<AntipiracySettings>() {
    private var scanConfiguration: ScanConfiguration = ScanConfiguration.default()

    public fun scan(block: ScanConfigurationsBuilder.() -> Unit) {
        scanConfiguration = ScanConfigurationsBuilder().apply(block).build()
    }

    override fun build(): AntipiracySettings = AntipiracySettings(scanConfiguration)
}