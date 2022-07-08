package com.kevlar.showcase.data.repo

import android.content.Context
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.rooting.KevlarRooting
import com.kevlar.rooting.dsl.attestation.RootingAttestation
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
    }

    suspend fun attestate(): RootingAttestation = withContext(externalDispatcher) {
        rooting.attestate(context)
    }
}