package com.kevlar.rooting.dsl.settings

import com.kevlar.rooting.dsl.language.DslBuilder
import com.kevlar.rooting.dsl.language.RootingDslMarker
import com.kevlar.rooting.dsl.settings.target.SystemTargetsBuilder
import com.kevlar.rooting.dsl.settings.target.SystemTargets

/**
 * Holds settings for [KevlarRooting].
 *
 * Current available settings include:
 * - System targets (allows to target the detection of specific system modifications)
 * */
public data class RootingSettings(
    val systemTargets: SystemTargets
)

/**
 * Builds settings holder.
 *
 * This is the core of the DSL, all the functions inside this class can be used
 * as dsl construction blocks to create parameters which will be finalized in
 * the [RootingSettings] build.
 * */
@RootingDslMarker
public class RootingSettingsBuilder : DslBuilder<RootingSettings>() {
    private var systemTargets: SystemTargets = SystemTargets.default()

    public fun targets(block: SystemTargetsBuilder.() -> Unit) {
        systemTargets = SystemTargetsBuilder().apply(block).build()
    }

    override fun build(): RootingSettings = RootingSettings(systemTargets)
}