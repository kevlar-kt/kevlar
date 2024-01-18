/*
 * Designed and developed by Kevlar Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevlar.showcase.data.repo

import android.content.Context
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.showcase.concurrency.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class designed to hold [KevlarAntipiracy]
 * */
class AntipiracyRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
    private val antipiracyAuto = KevlarAntipiracy.Defaults.JustPirateApps()

    private val antipiracyManual = KevlarAntipiracy {
        scan {
            pirate()
            store()
            collateral()
        }
    }

    suspend fun attestate(): AntipiracyAttestation = withContext(externalDispatcher) {
        antipiracyManual.attestate(context)
    }
}