@file:Suppress("NOTHING_TO_INLINE")

package com.kevlar.rooting.attestator

import android.content.Context
import com.kevlar.rooting.dataset.DetectableSystemTarget
import com.kevlar.rooting.detection.target.DumpDetector
import com.kevlar.rooting.dsl.attestation.target.TargetRootingAttestation
import com.kevlar.rooting.dsl.settings.RootingSettings
import com.kevlar.rooting.dsl.settings.target.TargetResult
import com.kevlar.rooting.shell.CombinedBinaryDump
import com.kevlar.rooting.shell.dump.CombinedBinaryDump
import com.kevlar.rooting.util.XposedUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * Package queries, scan initializer, vector specter manager & attestation producer
 *
 * This class may, at first look, seem like it has been written by a drunk developer
 * given how unconventionally some things have been written, but it all has been done
 * in the name of ease of integration and future developments.
 *
 * Generalizing into a proper OOP model and iterating does not scale well at all, given the
 * nuances and technical details that each different detection may require.
 * */
internal object TargetsAttestator {

    internal sealed class OutputSpecter

    /**
     * Automated binary's output dump and analysis
     * */
    internal data class TargetOutputDump(
        val target: DetectableSystemTarget,
        val associatedDumps: CombinedBinaryDump
    ) : OutputSpecter()

    /**
     * Custom logic for specific targets
     * */
    internal data class TargetCustomAnalysis(
        val target: DetectableSystemTarget,
        val detection: Boolean
    ) : OutputSpecter()

    /**
     * Conveys no information about the status of the associated binary.
     * Usually because its detection has not been requested.
     * */
    internal object BlankSpecter : OutputSpecter()


    suspend fun attestate(
        settings: RootingSettings,
        context: Context,
        index: Int
    ): TargetRootingAttestation = withContext(Dispatchers.Default) {
        val targets = settings.systemTargets

        // This will contain all the dump and custom analysis information
        val dumpsSpecters: MutableList<OutputSpecter> = mutableListOf()

        // Root check
        val rootTest = async {
            if (targets.root.enabled) {
                TargetOutputDump(DetectableSystemTarget.ROOT, CombinedBinaryDump("su", context.packageName, settings.allowRootCheck))
            } else {
                BlankSpecter
            }
        }

        val magiskTest = async {
            if (targets.magisk.enabled) {
                TargetOutputDump(
                    DetectableSystemTarget.MAGISK,
                    CombinedBinaryDump("magisk", context.packageName, settings.allowRootCheck)
                )
            } else {
                BlankSpecter
            }
        }

        val busyBoxTest = async {
            if (targets.busybox.enabled) {
                TargetOutputDump(
                    DetectableSystemTarget.BUSYBOX,
                    CombinedBinaryDump("busybox", context.packageName, settings.allowRootCheck)
                )
            } else {
                BlankSpecter
            }
        }

        val toyboxTest = async {
            if (targets.toybox.enabled) {
                TargetOutputDump(
                    DetectableSystemTarget.TOYBOX,
                    CombinedBinaryDump("toybox", context.packageName, settings.allowRootCheck)
                )
            } else {
                BlankSpecter
            }
        }

        val xposedTest = async {
            if (targets.xposed.enabled) {
                TargetCustomAnalysis(DetectableSystemTarget.XPOSED, detection = XposedUtil.isXposedActive)
            } else {
                BlankSpecter
            }
        }

        awaitAll(rootTest, magiskTest, busyBoxTest, toyboxTest, xposedTest)

        dumpsSpecters.addAll(
            listOf(
                rootTest.await(),
                magiskTest.await(),
                busyBoxTest.await(),
                toyboxTest.await(),
                xposedTest.await()
            )
        )

        return@withContext craftAttestation(dumpsSpecters, index)
    }

    private fun craftAttestation(
        outputDumps: List<OutputSpecter>,
        index: Int
    ): TargetRootingAttestation {
        data class IntermediateDumpState(
            val target: DetectableSystemTarget,
            val detection: Boolean
        )

        val detectedDumps: Set<IntermediateDumpState> = outputDumps
            .map {
                when (it) {
                    is TargetOutputDump -> IntermediateDumpState(it.target, DumpDetector.detect(it.associatedDumps))
                    is TargetCustomAnalysis -> IntermediateDumpState(it.target, it.detection)
                    else -> {
                        IntermediateDumpState(DetectableSystemTarget.ROOT, false)
                    }
                }
            }
            .filter { it.detection }
            .toSet()

        return when {
            detectedDumps.isEmpty() -> {
                TargetRootingAttestation.Clear(index)
            }

            else -> {
                TargetRootingAttestation.Failed(index, TargetResult(detectedDumps.map { it.target }))
            }
        }
    }

    private const val TAG = "TargetsAttestator"
}