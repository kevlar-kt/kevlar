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

        allowExplicitRootCheck()
    }

    suspend fun attestateRoot(): TargetRootingAttestation = withContext(externalDispatcher) {
        rooting.attestateTargets(context)
    }

    suspend fun attestateStatus(): StatusRootingAttestation = withContext(externalDispatcher) {
        rooting.attestateStatus()
    }
}