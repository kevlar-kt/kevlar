package com.kevlar.showcase.data.repo

import com.kevlar.antipiracy.AntipiracyAttestation
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.showcase.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SecurityRepository @Inject constructor(
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {

    private val antipiracy by lazy {
        KevlarAntipiracy {
            scan {

            }
        }
    }

    suspend fun attestate(): AntipiracyAttestation = withContext(externalDispatcher) {
        antipiracy.attestate()
    }

}