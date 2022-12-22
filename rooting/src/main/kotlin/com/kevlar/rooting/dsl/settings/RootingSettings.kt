/*
 * Designed and developed by Kevlar Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevlar.rooting.dsl.settings

import com.kevlar.rooting.dsl.language.DslBuilder
import com.kevlar.rooting.dsl.language.RootingDslMarker
import com.kevlar.rooting.dsl.settings.status.SystemStatus
import com.kevlar.rooting.dsl.settings.status.SystemStatusBuilder
import com.kevlar.rooting.dsl.settings.target.SystemTargets
import com.kevlar.rooting.dsl.settings.target.SystemTargetsBuilder

/**
 * Holds settings for [com.kevlar.rooting.KevlarRooting].
 *
 * Current available settings include:
 * - System targets                 (allows to target the detection of specific system modifications)
 * - System status                  (allows to detect system conditions)
 * - Allow explicit root check      (whether kevlar will try to acquire explicit root access to detect
 *                                  certain targets, instead of using just passive reconnaissance)
 * */
public data class RootingSettings(
    val systemTargets: SystemTargets,
    val systemStatus: SystemStatus,
    val allowExplicitRootCheck: Boolean
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
    private var allowExplicitRootCheck: Boolean = false

    public fun targets(block: SystemTargetsBuilder.() -> Unit) {
        systemTargets = SystemTargetsBuilder().apply(block).build()
    }

    public fun status(block: SystemStatusBuilder.() -> Unit) {
        systemStatus = SystemStatusBuilder().apply(block).build()
    }

    /**
     * By default kevlar does not run the `su` binary. Instead, it passively analyzes the
     * system environment to detect root access.
     *
     * If you don't mind having your application ask for root permission, you can enable this
     * flag, so that kevlar will be trying to acquire root access as an additional check.
     * */
    public fun allowExplicitRootCheck() {
        allowExplicitRootCheck = true
    }

    override fun build(): RootingSettings = RootingSettings(systemTargets, systemStatus, allowExplicitRootCheck)
}