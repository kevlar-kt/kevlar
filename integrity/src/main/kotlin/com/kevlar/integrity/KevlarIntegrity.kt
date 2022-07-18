package com.kevlar.integrity

import android.content.Context
import com.kevlar.integrity.attestator.IntegrityAttestator
import com.kevlar.integrity.checks.obtainBase64EncodedSignatures
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.integrity.dsl.settings.IntegritySettings
import com.kevlar.integrity.dsl.settings.IntegritySettingsBuilder
import com.kevlar.integrity.model.HardcodedMetadata
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.sign

public class KevlarIntegrity(
    private val hardcodedMetadata: HardcodedMetadata,
    block: IntegritySettingsBuilder.() -> Unit = DefaultIntegritySettings
) {
    private val settings: IntegritySettings = IntegritySettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces an attestation
     * */
    public suspend fun attestate(context: Context): IntegrityAttestation =
        IntegrityAttestator.attestate(hardcodedMetadata, settings, context, index.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val index = AtomicInteger(0)

        public fun blankAttestation(): IntegrityAttestation = IntegrityAttestation.Blank(0)

        public fun obtainCurrentAppSignature(context: Context): String = obtainBase64EncodedSignatures(context)[0].trim()
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