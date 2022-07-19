package com.kevlar.rooting

import android.content.Context
import com.kevlar.rooting.attestator.StatusAttestator
import com.kevlar.rooting.attestator.TargetsAttestator
import com.kevlar.rooting.dsl.attestation.status.StatusRootingAttestation
import com.kevlar.rooting.dsl.attestation.target.TargetRootingAttestation
import com.kevlar.rooting.dsl.settings.RootingSettings
import com.kevlar.rooting.dsl.settings.RootingSettingsBuilder
import java.util.concurrent.atomic.AtomicInteger

/**
 * Entry class for `:rooting` package.
 * */
public class KevlarRooting(
    block: RootingSettingsBuilder.() -> Unit = DefaultRootingSettings
) {
    private val settings: RootingSettings = RootingSettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces a [TargetRootingAttestation].
     *
     * Checks for root access, busybox, toybox, magisk or xposed framework activity.
     * */
    public suspend fun attestateTargets(context: Context): TargetRootingAttestation = TargetsAttestator.attestate(settings, context, targetIndex.getAndIncrement())


    /**
     * Asynchronously produces a [StatusRootingAttestation].
     *
     * Checks for emulator execution, non-enforcing selinux status or text keys.
     * */
    public suspend fun attestateStatus(): StatusRootingAttestation = StatusAttestator.attestate(settings, statusIndex.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number
         * */
        private val targetIndex = AtomicInteger(0)
        private val statusIndex = AtomicInteger(0)

        public fun blankTargetAttestation(): TargetRootingAttestation = TargetRootingAttestation.Blank(0)
        public fun blankStatusAttestation(): StatusRootingAttestation = StatusRootingAttestation.Blank(0)
    }
}


/**
 * Default settings for [KevlarRooting].
 * */
public val DefaultRootingSettings: RootingSettingsBuilder.() -> Unit = {
    this.run {
        targets {
            root()
        }

        status {
            emulator()
            selinux()
        }
    }
}