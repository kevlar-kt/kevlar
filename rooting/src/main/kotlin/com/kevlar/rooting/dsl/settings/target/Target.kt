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

package com.kevlar.rooting.dsl.settings.target

import com.kevlar.rooting.dsl.language.RootingDslMarker

/**
 * [Target] structure: it represents a single identifiable system modification
 * */
public abstract class Target {
    public abstract val enabled: Boolean
}


public data class RootTarget(
    override val enabled: Boolean
) : Target()

public data class BusyboxTarget(
    override val enabled: Boolean
): Target()

public data class ToyboxTarget(
    override val enabled: Boolean
): Target()

public data class MagiskTarget(
    override val enabled: Boolean
): Target()

public data class XposedTarget(
    override val enabled: Boolean
): Target()





@RootingDslMarker
public class RootTargetBuilder {
    private var enabled: Boolean = false
    private var allowExplicitRootCheck: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    /**
     * By default kevlar does not run the `su` binary. Instead, it passively analyzes the
     * system environment to detect root access.
     *
     * If you don't mind having your application ask for root permission, you can enable this
     * flag, so that kevlar will be trying to acquire root access as an additional check.
     * */
    public fun allowExplicitRootCheck() {
        allowExplicitRootCheck = true
    }

    public fun build(): RootTarget = RootTarget(enabled)
}

@RootingDslMarker
public class BusyboxTargetBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): BusyboxTarget = BusyboxTarget(enabled)
}

@RootingDslMarker
public class ToyboxTargetBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): ToyboxTarget = ToyboxTarget(enabled)
}



@RootingDslMarker
public class MagiskTargetBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): MagiskTarget = MagiskTarget(enabled)
}


@RootingDslMarker
public class XposedTargetBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): XposedTarget = XposedTarget(enabled)
}


