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

@file:Suppress("NOTHING_TO_INLINE")

package com.kevlar.rooting.attestator

import com.kevlar.rooting.dataset.DetectableSystemStatus
import com.kevlar.rooting.detection.status.SelinuxGetenforceStatus
import com.kevlar.rooting.detection.status.detectEmulator
import com.kevlar.rooting.detection.status.detectSelinux
import com.kevlar.rooting.detection.status.detectTestKeys
import com.kevlar.rooting.dsl.attestation.status.StatusRootingAttestation
import com.kevlar.rooting.dsl.settings.RootingSettings
import com.kevlar.rooting.dsl.settings.status.StatusResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * This class may, at first look, seem like it has been written by a drunk developer
 * given how unconventionally some things have been coded, but it all has been done
 * in the name of ease of integration and future developments.
 *
 * Generalizing into a proper OOP model and iterating does not scale well at all, given the
 * nuances and technical details that each different detection may require.
 * */
internal object StatusAttestator {

    /**
     * Holds the pair check result and [DetectableSystemStatus]
     * */
    data class StatusOutputSpecter(
        val status: DetectableSystemStatus,
        val detected: Boolean,

        /**
         * Whether the check has been run or not.
         * */
        val isEnabled: Boolean
    )

    suspend fun attestate(
        settings: RootingSettings,
        index: Int
    ): StatusRootingAttestation = withContext(Dispatchers.Default) {
        val statusRepository = settings.systemStatus

        // This will contain all the dump and custom analysis information
        val specters: MutableList<StatusOutputSpecter> = mutableListOf()

        val testKeys = async {
            StatusOutputSpecter(
                DetectableSystemStatus.TEST_KEYS,
                detected = statusRepository.testKeys.enabled && detectTestKeys(),
                isEnabled = statusRepository.testKeys.enabled
            )
        }

        val emulator = async {
            StatusOutputSpecter(
                DetectableSystemStatus.EMULATOR,
                detected = statusRepository.emulator.enabled && detectEmulator(),
                isEnabled = statusRepository.emulator.enabled
            )
        }

        val selinux = async {
            StatusOutputSpecter(
                DetectableSystemStatus.SELINUX,
                detected = statusRepository.selinux.enabled && when (detectSelinux()) {
                    SelinuxGetenforceStatus.DISABLED -> true
                    SelinuxGetenforceStatus.PERMISSIVE -> statusRepository.selinux.flagPermissive
                    SelinuxGetenforceStatus.ENFORCING -> false
                },
                isEnabled = statusRepository.selinux.enabled
            )
        }

        awaitAll(testKeys, emulator, selinux)

        specters.addAll(
            listOf(
                testKeys.await(), emulator.await(), selinux.await()
            )
        )

        return@withContext craftAttestation(specters, index)
    }

    private fun craftAttestation(
        outputDumps: List<StatusOutputSpecter>,
        index: Int
    ): StatusRootingAttestation {
        val detectedStatuses: Set<StatusOutputSpecter> = outputDumps.filter { it.detected && it.isEnabled }.toSet()

        return when {
            detectedStatuses.isEmpty() -> {
                StatusRootingAttestation.Clear(index)
            }

            else -> {
                StatusRootingAttestation.Failed(
                    index,
                    StatusResult(detectedStatuses.map { it.status })
                )
            }
        }
    }

    private const val TAG = "StatusAttestator"
}