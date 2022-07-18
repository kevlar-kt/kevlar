package com.kevlar.integrity.dsl.settings

import com.kevlar.integrity.dsl.language.IntegrityDslMarker
import com.kevlar.integrity.dsl.language.DslBuilder
import com.kevlar.integrity.dsl.settings.scan.CheckConfiguration
import com.kevlar.integrity.dsl.settings.scan.CheckConfigurationBuilder

/**
 * Holds settings for [KevlarIntegrity]
 * */
public data class IntegritySettings(
    val checks: CheckConfiguration
)

/**
 * Builds settings holder
 * */
@IntegrityDslMarker
public class IntegritySettingsBuilder : DslBuilder<IntegritySettings>() {
    private var scanConfiguration: CheckConfiguration = CheckConfiguration.default()

    public fun checks(block: CheckConfigurationBuilder.() -> Unit) {
        scanConfiguration = CheckConfigurationBuilder().apply(block).build()
    }

    override fun build(): IntegritySettings = IntegritySettings(scanConfiguration)
}