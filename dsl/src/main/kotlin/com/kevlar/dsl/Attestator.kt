@file:Suppress("NOTHING_TO_INLINE")

package com.kevlar.dsl

import android.content.Context
import com.kevlar.dsl.attestation.Attestation
import com.kevlar.dsl.settings.Settings
import kotlinx.coroutines.*

/**
 * Package queries, scan initializer, vector specter manager & attestation producer
 * */
public object Attestator {

    public suspend fun attestate(
        armament: Settings,
        context: Context,
        index: Int
    ): Attestation = withContext(Dispatchers.Default) {
        return TODO()
    }

    private const val TAG = "Attestator"
}