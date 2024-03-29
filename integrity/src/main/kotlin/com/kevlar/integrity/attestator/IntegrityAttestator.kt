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

package com.kevlar.integrity.attestator

import android.content.Context
import com.kevlar.integrity.checks.debug.isDebugBuild
import com.kevlar.integrity.checks.installer.matchesAllowedInstallerPackageNames
import com.kevlar.integrity.checks.packagename.matchesHardcodedPackageName
import com.kevlar.integrity.checks.signature.matchesHardcodedSignature
import com.kevlar.integrity.dataset.IntegrityElement
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.integrity.dsl.settings.IntegritySettings
import com.kevlar.integrity.dsl.settings.scan.CheckResult
import kotlinx.coroutines.*

internal object IntegrityAttestator {

    /**
     * Holds the pair check result and element.
     * */
    data class CheckOutputSpecter(
        /**
         * The [IntegrityElement] kevlar is checking for.
         * */
        val element: IntegrityElement,

        /**
         * Whether the given element has passed the test (it checks out, everything is
         * as normal) or not (and we have to report it.
         *
         * This is internally used to filter out tests which have not been requested.
         * If a test isn't requested, then we consider that it has automatically passed,
         * as its result will not be included in the attestation.
         * */
        val hasPassedTest: Boolean,

        /**
         * Whether the check has been run or not.
         * Used to tell passed test from non-requested ones apart.
         * */
        val isEnabled: Boolean,
    )

    suspend fun attestate(
        settings: IntegritySettings,
        context: Context,
        index: Int
    ): IntegrityAttestation = withContext(Dispatchers.Default) {
        val checkSettings = settings.checks
        val specters: MutableList<CheckOutputSpecter> = mutableListOf()

        // Here we run all the checks and see which ones fail
        val signature: Deferred<CheckOutputSpecter> = async {
            if (checkSettings.signature.enabled) {
                // If enabled is true, we can assume that at least one check has been requested

                val hardcodedSignature = checkSettings.signature.hardcodedBase64EncodedSignatures

                assert(hardcodedSignature.valid) {
                    "Invalid configuration detected: both hardcoded signature and fingerprint data are invalid."
                }

                val matchesSignature = if (hardcodedSignature.valid) {
                    matchesHardcodedSignature(
                        hardcodedBase64EncodedSignatures = hardcodedSignature,
                        context
                    )
                } else {
                    true // Signature test has not been requested
                }


                /*
                 * One check is enabled, so we return isEnabled to true,
                 * If one test has not been requested, its result is true
                 * which when and-ed with the other(s) has no effect.
                 * If one test has been requested, and fails, then we
                 * are able to detect that.
                 * */
                CheckOutputSpecter(
                    IntegrityElement.MATCH_HARDCODED_SIGNATURE,
                    hasPassedTest = matchesSignature,
                    isEnabled = true
                )
            } else {
                CheckOutputSpecter(
                    IntegrityElement.MATCH_HARDCODED_SIGNATURE,
                    hasPassedTest = true,
                    isEnabled = false
                )
            }
        }



        val packageName: Deferred<CheckOutputSpecter> = async {
            if (checkSettings.packageName.enabled) {
                val hardcodedPackageName = checkSettings.packageName.hardcodedPackageName

                assert(hardcodedPackageName.valid) {
                    "Invalid packageName provided: got hardcoded [${checkSettings.packageName.hardcodedPackageName}], current [${context.packageName}]"
                }

                CheckOutputSpecter(
                    IntegrityElement.MATCH_HARDCODED_PACKAGE_NAME,
                    hasPassedTest = matchesHardcodedPackageName(
                        hardcodedPackageName = hardcodedPackageName.packageName,
                        context
                    ),
                    isEnabled = true
                )
            } else {
                CheckOutputSpecter(
                    IntegrityElement.MATCH_HARDCODED_PACKAGE_NAME,
                    hasPassedTest = true,
                    isEnabled = false
                )
            }
        }


        val debug: Deferred<CheckOutputSpecter> = async {
            if (checkSettings.debug.enabled) {
                CheckOutputSpecter(
                    IntegrityElement.DEBUG_BUILD,
                    // We do not want debug builds, so we negate it
                    hasPassedTest = !isDebugBuild(context),
                    isEnabled = true
                )
            } else {
                CheckOutputSpecter(
                    IntegrityElement.DEBUG_BUILD,
                    hasPassedTest = true,
                    isEnabled = false
                )
            }
        }


        val installer: Deferred<CheckOutputSpecter> = async {
            if (checkSettings.installer.enabled) {
                CheckOutputSpecter(
                    IntegrityElement.UNAUTHORIZED_INSTALLER,
                    hasPassedTest = matchesAllowedInstallerPackageNames(
                        checkSettings.installer.allowedInstallers,
                        context
                    ),
                    isEnabled = true
                )
            } else {
                CheckOutputSpecter(
                    IntegrityElement.UNAUTHORIZED_INSTALLER,
                    hasPassedTest = true,
                    isEnabled = false
                )
            }
        }

        awaitAll(signature, packageName, debug, installer)

        specters.addAll(
            listOf(
                signature.await(), packageName.await(), debug.await(), installer.await()
            )
        )

        return@withContext craftAttestation(specters, index)
    }


    private fun craftAttestation(
        specters: List<CheckOutputSpecter>,
        index: Int
    ): IntegrityAttestation {
        val detectedElements: Set<CheckOutputSpecter> =
            specters.filter { it.isEnabled && !it.hasPassedTest }.toSet()

        return when {
            detectedElements.isEmpty() -> {
                IntegrityAttestation.Clear(index)
            }

            else -> {
                IntegrityAttestation.Failed(
                    index,
                    CheckResult(detectedElements.map { it.element })
                )
            }
        }
    }


    private const val TAG = "IntegrityAttestator"
}