package com.kevlar

import android.content.Context
import com.kevlar.dsl.attestation.Attestation
import com.kevlar.dsl.settings.Settings
import com.kevlar.dsl.settings.SettingsBuilder
import java.util.concurrent.atomic.AtomicInteger

public class Kevlar(
    block: SettingsBuilder.() -> Unit
) {
    private val settings: Settings = SettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces an attestation
     * */
    public suspend fun attestate(context: Context): Attestation =
        Attestator.attestate(settings, context, index.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val index = AtomicInteger(0)

        public fun blankAttestation(): Attestation = Attestation.Blank(0)
    }
}
