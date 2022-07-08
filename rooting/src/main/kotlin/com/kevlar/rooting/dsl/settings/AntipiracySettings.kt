package com.kevlar.rooting.dsl.settings

import com.kevlar.rooting.dsl.language.DslBuilder
import com.kevlar.rooting.dsl.language.RootingDslMarker
import com.kevlar.rooting.dsl.settings.target.ScanConfigurationsBuilder
import com.kevlar.rooting.dsl.settings.target.SystemTargets

/**
 * Holds settings
 * */
public data class RootingSettings(
    val systemTargets: SystemTargets
)

/**
 * Builds settings holder
 * */
@RootingDslMarker
public class RootingSettingsBuilder : DslBuilder<RootingSettings>() {
    private var systemTargets: SystemTargets = SystemTargets.default()

    public fun scan(block: ScanConfigurationsBuilder.() -> Unit) {
        systemTargets = ScanConfigurationsBuilder().apply(block).build()
    }

    override fun build(): RootingSettings = RootingSettings(systemTargets)
}