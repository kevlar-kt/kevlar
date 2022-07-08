package com.kevlar.rooting

import android.content.Context
import com.kevlar.rooting.dsl.attestation.status.StatusRootingAttestation
import com.kevlar.rooting.dsl.attestation.target.TargetRootingAttestation
import com.kevlar.rooting.dsl.settings.RootingSettings
import com.kevlar.rooting.dsl.settings.RootingSettingsBuilder
import java.util.concurrent.atomic.AtomicInteger

public class KevlarRooting(
    block: RootingSettingsBuilder.() -> Unit
) {
    private val settings: RootingSettings = RootingSettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces a [TargetRootingAttestation]
     * */
    public suspend fun attestateSystemModifications(context: Context): TargetRootingAttestation = TargetsAttestator.attestate(settings, context, targetIndex.getAndIncrement())


    /**
     * Asynchronously produces a [TargetRootingAttestation]
     * */
    public suspend fun attestateSystemStatus(context: Context): StatusRootingAttestation = StatusAttestator.attestate(settings, context, statusIndex.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val targetIndex = AtomicInteger(0)
        private val statusIndex = AtomicInteger(0)

        public fun blankAttestation(): TargetRootingAttestation = TargetRootingAttestation.Blank(0)
    }
}