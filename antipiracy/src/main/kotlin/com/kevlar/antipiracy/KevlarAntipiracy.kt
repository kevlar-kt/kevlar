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

package com.kevlar.antipiracy

import android.content.Context
import com.kevlar.antipiracy.attestator.AntipiracyAttestator
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.antipiracy.dsl.settings.AntipiracySettings
import com.kevlar.antipiracy.dsl.settings.AntipiracySettingsBuilder
import java.util.concurrent.atomic.AtomicInteger

/**
 * Main class for `:antipiracy` package.
 * */
public class KevlarAntipiracy(
    block: AntipiracySettingsBuilder.() -> Unit = DefaultAntipiracySettings
) {
    private val settings: AntipiracySettings = AntipiracySettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces an [AntipiracyAttestation]
     * */
    public suspend fun attestate(context: Context): AntipiracyAttestation = AntipiracyAttestator.attestate(settings, context, index.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val index = AtomicInteger(0)

        /**
         * Produces a blank attestation.
         * */
        public fun blankAttestation(index: Int = 0): AntipiracyAttestation = AntipiracyAttestation.Blank(index)
    }
}

public val DefaultAntipiracySettings: AntipiracySettingsBuilder.() -> Unit = {
    this.run {
        scan {
            pirate()
        }
    }
}