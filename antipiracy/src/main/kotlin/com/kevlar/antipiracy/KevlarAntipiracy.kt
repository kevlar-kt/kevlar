package com.kevlar.antipiracy

import android.content.Context
import com.kevlar.antipiracy.attestator.AntipiracyAttestator
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import java.util.concurrent.atomic.AtomicInteger

/**
 * Main class for `:antipiracy` package.
 * */
public class KevlarAntipiracy(
    block: AntipiracySettingsBuilder.() -> Unit = DefaultAntipiracySettings
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

public val DefaultAntipiracySettings: AntipiracySettingsBuilder.() -> Unit = {
    this.run {
        scan {
            pirate()
        }
    }
}