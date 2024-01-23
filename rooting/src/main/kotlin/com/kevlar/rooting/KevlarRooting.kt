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

package com.kevlar.rooting

import android.content.Context
import com.kevlar.rooting.attestator.StatusAttestator
import com.kevlar.rooting.attestator.TargetsAttestator
import com.kevlar.rooting.dsl.attestation.status.StatusRootingAttestation
import com.kevlar.rooting.dsl.attestation.target.TargetRootingAttestation
import com.kevlar.rooting.dsl.settings.RootingSettings
import com.kevlar.rooting.dsl.settings.RootingSettingsBuilder
import java.util.concurrent.atomic.AtomicInteger

/**
 * Entry class for `:rooting` package.
 * */
public class KevlarRooting(
    block: RootingSettingsBuilder.() -> Unit
) {
    private val settings: RootingSettings = RootingSettingsBuilder().apply(block).build()

    /**
     * Asynchronously produces a [TargetRootingAttestation].
     *
     * Checks for root access, busybox, toybox, magisk or xposed framework activity.
     * */
    public suspend fun attestateTargets(context: Context): TargetRootingAttestation = TargetsAttestator.attestate(settings, context, targetIndex.getAndIncrement())

    /**
     * Asynchronously produces a [StatusRootingAttestation].
     *
     * Checks for emulator execution, non-enforcing selinux status or text keys.
     * */
    public suspend fun attestateStatus(): StatusRootingAttestation = StatusAttestator.attestate(settings, statusIndex.getAndIncrement())

    public companion object {
        /**
         * Counts the attestation number for target attestations.
         * */
        private val targetIndex = AtomicInteger(0)

        /**
         * Counts the attestation number for status attestations.
         * */
        private val statusIndex = AtomicInteger(0)

        /**
         * Produces a blank target attestation.
         * */
        public fun blankTargetAttestation(index: Int = 0): TargetRootingAttestation = TargetRootingAttestation.Blank(index)

        /**
         * Produces a blank status attestation.
         * */
        public fun blankStatusAttestation(index: Int = 0): StatusRootingAttestation = StatusRootingAttestation.Blank(index)
    }



    /**
     * Contains relevant pre-packaged configurations for automatically configuring [KevlarRooting].
     * */
    @Suppress("FunctionName", "ObjectPrivatePropertyName")
    public object Defaults {
        public fun Standard(): KevlarRooting = KevlarRooting(stdRootingSettingsDsl)
        public fun JustRooting(): KevlarRooting = KevlarRooting(rootSettingsDsl(explicit = false))
        public fun JustRootingExplicit(): KevlarRooting = KevlarRooting(rootSettingsDsl(explicit = true))
        public fun JustEmulator(): KevlarRooting = KevlarRooting(emulatorSettingsDsl)
        public fun Empty(): KevlarRooting = KevlarRooting(emptyRootingSettingsDsl)
        private fun Exhaustive(): KevlarRooting = KevlarRooting(__exhaustiveRootingSettingsDsl)


        private val stdRootingSettingsDsl: RootingSettingsBuilder.() -> Unit = {
            this.run {
                targets {
                    root()
                    magisk()
                }

                status {
                    emulator()
                    selinux {
                        // flagPermissive()
                    }
                    testKeys()
                }
            }
        }

        private fun rootSettingsDsl(explicit: Boolean): RootingSettingsBuilder.() -> Unit = {
            this.run {
                targets {
                    root()
                    magisk()
                }

                status {}

                if (explicit) {
                    allowExplicitRootCheck()
                }
            }
        }

        private val emulatorSettingsDsl: RootingSettingsBuilder.() -> Unit = {
            this.run {
                targets {}

                status {
                    emulator()
                    testKeys()
                }
            }
        }

        private val emptyRootingSettingsDsl: RootingSettingsBuilder.() -> Unit = {
            this.run {
                targets {}
                status {}
            }
        }

        private val __exhaustiveRootingSettingsDsl: RootingSettingsBuilder.() -> Unit = {
            this.run {
                targets {
                    root()
                    magisk()
                    busybox()
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
        }
    }
}