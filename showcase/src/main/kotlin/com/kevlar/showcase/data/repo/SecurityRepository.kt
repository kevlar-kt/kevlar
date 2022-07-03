package com.kevlar.showcase.data.repo

import android.content.Context
import com.kevlar.antipiracy.AntipiracyAttestation
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.showcase.concurrency.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SecurityRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
    private val antipiracy by lazy {
        KevlarAntipiracy {
            scan {
                pirate()
                store()
                collateral()
            }
        }
    }

    suspend fun attestate(): AntipiracyAttestation = withContext(externalDispatcher) {
        antipiracy.attestate(context)
    }
}