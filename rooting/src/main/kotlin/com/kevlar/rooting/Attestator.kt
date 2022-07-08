@file:Suppress("NOTHING_TO_INLINE")

package com.kevlar.rooting

import android.content.Context
import com.kevlar.rooting.dsl.attestation.RootingAttestation
import com.kevlar.rooting.dsl.settings.RootingSettings
import kotlinx.coroutines.*

/**
 * Package queries, scan initializer, vector specter manager & attestation producer
 * */
public object Attestator {

    public suspend fun attestate(
        armament: RootingSettings,
        context: Context,
        index: Int
    ): RootingAttestation = withContext(Dispatchers.Default) {
        TODO()
    }

    private const val TAG = "Attestator"
}