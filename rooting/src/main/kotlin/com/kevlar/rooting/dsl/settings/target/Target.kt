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
public class RootTargetBuilder() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): RootTarget = RootTarget(enabled)
}

@RootingDslMarker
public class BusyboxTargetBuilder() {
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
public class ToyboxTargetBuilder() {
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
public class MagiskTargetBuilder() {
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
public class XposedTargetBuilder() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): XposedTarget = XposedTarget(enabled)
}


