package com.kevlar.rooting

import android.content.Context
import com.kevlar.rooting.dsl.attestation.RootingAttestation
import com.kevlar.rooting.dsl.settings.RootingSettings
import com.kevlar.rooting.dsl.settings.RootingSettingsBuilder
import java.util.concurrent.atomic.AtomicInteger

public class KevlarRooting(
    block: RootingSettingsBuilder.() -> Unit
) {
    private val settings: RootingSettings = RootingSettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces a [RootingAttestation]
     * */
    public suspend fun attestate(context: Context): RootingAttestation = Attestator.attestate(settings, context, index.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val index = AtomicInteger(0)

        public fun blankAttestation(): RootingAttestation = RootingAttestation.Blank(0)
    }
}
