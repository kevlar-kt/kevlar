package com.kevlar.antipiracy

import android.content.Context
import com.kevlar.antipiracy.attestator.AntipiracyAttestator
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import java.util.concurrent.atomic.AtomicInteger

public class KevlarAntipiracy(
    block: AntipiracySettingsBuilder.() -> Unit
) {
    private val armament: AntipiracySettings = AntipiracySettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces an [AntipiracyAttestation]
     * */
    public suspend fun attestate(context: Context): AntipiracyAttestation = AntipiracyAttestator.attestate(armament, context, index.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val index = AtomicInteger(0)

        public fun blankAttestation(): AntipiracyAttestation = AntipiracyAttestation.Blank(0)
    }
}
