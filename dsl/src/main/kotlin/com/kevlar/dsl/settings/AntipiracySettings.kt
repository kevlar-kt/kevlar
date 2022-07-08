package com.kevlar.dsl.settings

import com.kevlar.dsl.language.KevlarDslMarker
import com.kevlar.dsl.language.DslBuilder

/**
 * Holds settings
 * */
public data class Settings(
    val scanConfiguration: ScanConfiguration
)

/**
 * Builds settings holder
 * */
@KevlarDslMarker
public class SettingsBuilder : DslBuilder<Settings>() {
    private var scanConfiguration: ScanConfiguration = ScanConfiguration.default()

    public fun scan(block: ScanConfigurationsBuilder.() -> Unit) {
        scanConfiguration = ScanConfigurationsBuilder().apply(block).build()
    }

    override fun build(): Settings = Settings(scanConfiguration)
}