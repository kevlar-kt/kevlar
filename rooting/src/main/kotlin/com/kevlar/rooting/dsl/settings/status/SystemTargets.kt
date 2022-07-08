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
