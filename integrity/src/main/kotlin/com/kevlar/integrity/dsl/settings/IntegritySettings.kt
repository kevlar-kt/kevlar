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