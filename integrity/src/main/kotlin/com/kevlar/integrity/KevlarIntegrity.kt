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

package com.kevlar.integrity

import android.content.Context
import com.kevlar.integrity.attestator.IntegrityAttestator
import com.kevlar.integrity.checks.obtainBase64EncodedSignatures
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.integrity.dsl.settings.IntegritySettings
import com.kevlar.integrity.dsl.settings.IntegritySettingsBuilder
import java.util.concurrent.atomic.AtomicInteger

public class KevlarIntegrity(
    block: IntegritySettingsBuilder.() -> Unit = DefaultIntegritySettings
) {
    private val settings: IntegritySettings = IntegritySettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces an attestation
     * */
    public suspend fun attestate(context: Context): IntegrityAttestation =
        IntegrityAttestator.attestate(settings, context, index.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val index = AtomicInteger(0)

        /**
         * Produces a blank attestation.
         * */
        public fun blankAttestation(index: Int = 0): IntegrityAttestation = IntegrityAttestation.Blank(index)

        /**
         * Returns the current running application (oldest) signature.
         * */
        public fun obtainCurrentAppSignature(context: Context): String = obtainCurrentAppSignatures(context)[0]

        /**
         * Returns the current running application signatures.
         * */
        public fun obtainCurrentAppSignatures(context: Context): List<String> = obtainBase64EncodedSignatures(context)
    }
}

public val DefaultIntegritySettings: IntegritySettingsBuilder.() -> Unit = {
    this.run {
        checks {
            signature()
            packageName()
            debug()
            // installer()
        }
    }
}