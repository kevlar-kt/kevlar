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

import com.kevlar.rooting.dsl.language.RootingDslMarker
import kotlin.annotation.Target

/**
 * [Target] structure: it represents a single identifiable system modification
 * */
public abstract class Status {
    public abstract val enabled: Boolean
}


public data class TestKeysStatus(
    override val enabled: Boolean
) : Status()

public data class EmulatorStatus(
    override val enabled: Boolean
) : Status()

public data class SelinuxStatus(
    override val enabled: Boolean,

    /**
     * By default only disabled selinux status is flagged and reported.
     * You can override this and also have kevlar report a hit if selinux is permissive
     * by setting this parameter to true.
     */
    val flagPermissive: Boolean,
) : Status()


@RootingDslMarker
public class TestKeysStatusBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): TestKeysStatus = TestKeysStatus(enabled)
}

@RootingDslMarker
public class EmulatorStatusBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): EmulatorStatus = EmulatorStatus(enabled)
}

@RootingDslMarker
public class SelinuxStatusBuilder {
    private var enabled: Boolean = false
    private var flagPermissive: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun flagPermissive() {
        flagPermissive = true
    }

    public fun build(): SelinuxStatus = SelinuxStatus(enabled, flagPermissive)
}