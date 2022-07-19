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

package com.kevlar.rooting.dsl.settings.status

import com.kevlar.rooting.dsl.language.DslBuilder
import com.kevlar.rooting.dsl.language.RootingDslMarker

public class SystemStatus(
    public val testKeys: TestKeysStatus,
    public val emulator: EmulatorStatus,
    public val selinux: SelinuxStatus,
) {
    public companion object {
        public fun default(): SystemStatus = SystemStatus(
            TestKeysStatus(enabled = false),
            EmulatorStatus(enabled = false),
            SelinuxStatus(enabled = false, flagPermissive = false),
        )
    }
}

@RootingDslMarker
public class SystemStatusBuilder : DslBuilder<SystemStatus>() {
    private var testKeys: TestKeysStatus = TestKeysStatus(enabled = false)
    private var emulator: EmulatorStatus = EmulatorStatus(enabled = false)
    private var selinux: SelinuxStatus = SelinuxStatus(enabled = false, flagPermissive = false)

    public fun testKeys(block: TestKeysStatusBuilder.() -> Unit = {}) {
        testKeys = TestKeysStatusBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun emulator(block: EmulatorStatusBuilder.() -> Unit = {}) {
        emulator = EmulatorStatusBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun selinux(block: SelinuxStatusBuilder.() -> Unit = {}) {
        selinux = SelinuxStatusBuilder().apply {
            block()
            enable()
        }.build()
    }


    /**
     * Builds the target configuration
     * */
    override fun build(): SystemStatus = SystemStatus(testKeys, emulator, selinux)
}
