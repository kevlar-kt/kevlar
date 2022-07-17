package com.kevlar.rooting.dsl.settings

import com.kevlar.rooting.dsl.language.DslBuilder
import com.kevlar.rooting.dsl.language.RootingDslMarker
import com.kevlar.rooting.dsl.settings.status.SystemStatus
import com.kevlar.rooting.dsl.settings.status.SystemStatusBuilder
import com.kevlar.rooting.dsl.settings.target.SystemTargets
import com.kevlar.rooting.dsl.settings.target.SystemTargetsBuilder

/**
 * Holds settings for [KevlarRooting].
 *
 * Current available settings include:
 * - System targets (allows to target the detection of specific system modifications)
 * - System status (allows to detect system conditions)
 * - Allow root check (whether kevlar will try to acquire root access to detect certain targets)
 * */
public data class RootingSettings(
    val systemTargets: SystemTargets,
    val systemStatus: SystemStatus,
    val allowRootCheck: Boolean
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
    private var systemStatus: SystemStatus = SystemStatus.default()
    private var allowRootCheck: Boolean = true

    public fun targets(block: SystemTargetsBuilder.() -> Unit) {
        systemTargets = SystemTargetsBuilder().apply(block).build()
    }

    public fun status(block: SystemStatusBuilder.() -> Unit) {
        systemStatus = SystemStatusBuilder().apply(block).build()
    }

    public fun disallowRootCheck() {
        allowRootCheck = false
    }

    override fun build(): RootingSettings = RootingSettings(systemTargets, systemStatus, allowRootCheck)
}