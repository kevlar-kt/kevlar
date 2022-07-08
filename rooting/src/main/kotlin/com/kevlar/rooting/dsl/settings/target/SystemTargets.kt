package com.kevlar.rooting.dsl.settings.target

import com.kevlar.rooting.dsl.language.DslBuilder
import com.kevlar.rooting.dsl.language.RootingDslMarker

public class SystemTargets(
    public val root: RootTarget,
    public val busybox: BusyboxTarget,
    public val toybox: ToyboxTarget,
    public val magisk: MagiskTarget,
    public val xposed: XposedTarget,
) {
    public companion object {
        public fun default(): SystemTargets = SystemTargets(
            RootTarget(enabled = false),
            BusyboxTarget(enabled = false),
            ToyboxTarget(enabled = false),
            MagiskTarget(enabled = false),
            XposedTarget(enabled = false),
        )
    }
}


@RootingDslMarker
public class ScanConfigurationsBuilder : DslBuilder<SystemTargets>() {
    private var root: RootTarget = RootTarget(enabled = false)
    private var busybox: BusyboxTarget = BusyboxTarget(enabled = false)
    private var toybox: ToyboxTarget = ToyboxTarget(enabled = false)
    private var magisk: MagiskTarget = MagiskTarget(enabled = false)
    private var xposed: XposedTarget = XposedTarget(enabled = false)

    public fun root(block: RootTargetBuilder.() -> Unit = {}) {
        root = RootTargetBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun busybox(block: BusyboxTargetBuilder.() -> Unit = {}) {
        busybox = BusyboxTargetBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun toybox(block: ToyboxTargetBuilder.() -> Unit = {}) {
        toybox = ToyboxTargetBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun magisk(block: MagiskTargetBuilder.() -> Unit = {}) {
        magisk = MagiskTargetBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun xposed(block: XposedTargetBuilder.() -> Unit = {}) {
        xposed = XposedTargetBuilder().apply {
            block()
            enable()
        }.build()
    }


    /**
     * Builds the target configuration
     * */
    override fun build(): SystemTargets = SystemTargets(root, busybox, toybox, magisk, xposed)
}
