@file:Suppress("NOTHING_TO_INLINE")

package com.kevlar.rooting

import android.content.Context
import com.kevlar.rooting.dataset.SystemTarget
import com.kevlar.rooting.detection.*
import com.kevlar.rooting.detection.BlankSpecter
import com.kevlar.rooting.detection.DumpDetector
import com.kevlar.rooting.detection.OutputSpecter
import com.kevlar.rooting.detection.TargetCustomAnalysis
import com.kevlar.rooting.detection.TargetOutputDump
import com.kevlar.rooting.dsl.attestation.RootingAttestation
import com.kevlar.rooting.dsl.settings.RootingSettings
import com.kevlar.rooting.dsl.settings.target.TargetResult
import com.kevlar.rooting.shell.CombinedBinaryDump
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
internal object Attestator {

    suspend fun attestate(
        settings: RootingSettings,
        context: Context,
        index: Int
    ): RootingAttestation = withContext(Dispatchers.Default) {
        val targets = settings.systemTargets

        // This will contain all the dump and custom analysis information
        val dumpsSpecters: MutableSet<OutputSpecter> = mutableSetOf()

        // Root check
        val rootTest = async {
            if (targets.root.enabled) {
                TargetOutputDump(SystemTarget.ROOT, CombinedBinaryDump("su", context.packageName))
            } else {
                BlankSpecter
            }
        }

        val magiskTest = async {
            if (targets.magisk.enabled) {
                TargetOutputDump(
                    SystemTarget.MAGISK,
                    CombinedBinaryDump("magisk", context.packageName)
                )
            } else {
                BlankSpecter
            }
        }

        val busyBoxTest = async {
            if (targets.busybox.enabled) {
                TargetOutputDump(
                    SystemTarget.BUSYBOX,
                    CombinedBinaryDump("busybox", context.packageName)
                )
            } else {
                BlankSpecter
            }
        }

        val toyboxTest = async {
            if (targets.toybox.enabled) {
                TargetOutputDump(
                    SystemTarget.TOYBOX,
                    CombinedBinaryDump("toybox", context.packageName)
                )
            } else {
                BlankSpecter
            }
        }

        val xposedTest = async {
            if (targets.xposed.enabled) {
                TargetCustomAnalysis(SystemTarget.XPOSED, detection = XposedUtil.isXposedActive)
            } else {
                BlankSpecter
            }
        }

        awaitAll(rootTest, magiskTest, busyBoxTest, toyboxTest, xposedTest)

        dumpsSpecters.addAll(
            setOf(
                rootTest.await(),
                magiskTest.await(),
                busyBoxTest.await(),
                toyboxTest.await(),
                xposedTest.await()
            )
        )

        return@withContext craftAttestation(dumpsSpecters, index)
    }

    data class IntermediateDumpState(
        val target: SystemTarget,
        val detection: Boolean
    )

    private fun craftAttestation(
        outputDumps: Set<OutputSpecter>,
        index: Int
    ): RootingAttestation {
        val detectedDumps: Set<IntermediateDumpState> = outputDumps
            .map {
                when (it) {
                    is TargetCustomAnalysis -> IntermediateDumpState(it.target, it.detection)
                    is TargetOutputDump -> IntermediateDumpState(it.target, DumpDetector.detect(it.associatedDumps))
                    else -> {
                        IntermediateDumpState(SystemTarget.ROOT, false)
                    }
                }
            }
            .filter { it.detection }
            .toSet()


        return when {
            detectedDumps.isEmpty() -> {
                RootingAttestation.Clear(index)
            }

            else -> {
                RootingAttestation.Failed(index, TargetResult(detectedDumps.map { it.target }))
            }
        }
    }

    private const val TAG = "Attestator"
}