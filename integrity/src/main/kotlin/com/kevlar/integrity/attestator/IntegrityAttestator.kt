package com.kevlar.integrity.attestator

import android.content.Context
import com.kevlar.integrity.checks.isDebugBuild
import com.kevlar.integrity.checks.matchesAllowedInstallerPackageNames
import com.kevlar.integrity.checks.matchesHardcodedPackageName
import com.kevlar.integrity.checks.matchesHardcodedSignature
import com.kevlar.integrity.dataset.IntegrityElement
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.integrity.dsl.settings.IntegritySettings
import com.kevlar.integrity.dsl.settings.scan.CheckResult
import com.kevlar.integrity.model.HardcodedMetadata
import kotlinx.coroutines.*

internal object IntegrityAttestator {

    data class CheckOutputSpecter(
        val element: IntegrityElement,
        val detected: Boolean
    )

    suspend fun attestate(
        hardcodedMetadata: HardcodedMetadata,
        settings: IntegritySettings,
        context: Context,
        index: Int
    ): IntegrityAttestation = withContext(Dispatchers.Default) {
        val checkSettings = settings.checks

        val specters: MutableList<CheckOutputSpecter> = mutableListOf()

        val signature: Deferred<CheckOutputSpecter> = async {
            CheckOutputSpecter(
                IntegrityElement.MATCH_HARDCODED_SIGNATURE,
                matchesHardcodedSignature(hardcodedSignature = hardcodedMetadata.signature, context)
            )
        }


        val packageName: Deferred<CheckOutputSpecter> = async {
            CheckOutputSpecter(
                IntegrityElement.MATCH_HARDCODED_PACKAGE_NAME,
                matchesHardcodedPackageName(hardcodedPackageName = hardcodedMetadata.packageName, context)
            )
        }


        val debug: Deferred<CheckOutputSpecter> = async {
            CheckOutputSpecter(
                IntegrityElement.DEBUG_BUILD,
                isDebugBuild(context)
            )
        }


        val installer: Deferred<CheckOutputSpecter> = async {
            CheckOutputSpecter(
                IntegrityElement.UNAUTHORIZED_INSTALLER,
                matchesAllowedInstallerPackageNames(checkSettings.installer.allowedInstallers, context)
            )
        }


        awaitAll(signature, packageName)

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
        val detectedElements: Set<CheckOutputSpecter> = specters.filter { it.detected }.toSet()

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