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

package com.kevlar.antipiracy.dsl.settings

import com.kevlar.antipiracy.dsl.language.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.language.DslBuilder
import com.kevlar.antipiracy.dsl.settings.scan.ScanConfiguration
import com.kevlar.antipiracy.dsl.settings.scan.ScanConfigurationBuilder

/**
 * Holds settings for [com.kevlar.antipiracy.KevlarAntipiracy].
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

    public fun scan(block: ScanConfigurationBuilder.() -> Unit) {
        scanConfiguration = ScanConfigurationBuilder().apply(block).build()
    }

    override fun build(): AntipiracySettings = AntipiracySettings(scanConfiguration)
}