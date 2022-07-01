package com.kevlar.antipiracy

import android.content.Context
import java.util.concurrent.atomic.AtomicInteger

public class KevlarAntipiracy(
    block: AntipiracyArmamentBuilder.() -> Unit
) {
    private val armament: AntipiracyArmament = AntipiracyArmamentBuilder().apply(block).build()

    /**
     * Asynchronously produces an [AntipiracyAttestation]
     * */
    public suspend fun attestate(context: Context): AntipiracyAttestation = Attestator.attestate(armament, context, index.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val index = AtomicInteger(0)

        public fun blankAttestation(): AntipiracyAttestation = AntipiracyAttestation.Blank(0)
    }
}
