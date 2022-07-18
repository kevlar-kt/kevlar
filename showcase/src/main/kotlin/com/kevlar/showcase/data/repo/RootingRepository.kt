package com.kevlar.showcase.data.repo

import android.content.Context
import com.kevlar.rooting.KevlarRooting
import com.kevlar.rooting.dsl.attestation.status.StatusRootingAttestation
import com.kevlar.rooting.dsl.attestation.target.TargetRootingAttestation
import com.kevlar.showcase.concurrency.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class designed to hold [KevlarRooting]
 * */
class RootingRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
    private val rooting = KevlarRooting {
        targets {
            root()
            magisk()
            busybox()
            toybox()
            xposed()
        }

        status {
            testKeys()
            emulator()
            selinux {
                flagPermissive()
            }
        }

        allowRootCheck()
    }

    /**
     * This uses the default settings, only targets/root and status/emulator
     * */
    private val defaultRooting = KevlarRooting()

    suspend fun attestateRoot(): TargetRootingAttestation = withContext(externalDispatcher) {
        rooting.attestateTargets(context)
    }


    suspend fun attestateStatus(): StatusRootingAttestation = withContext(externalDispatcher) {
        rooting.attestateStatus()
    }
}