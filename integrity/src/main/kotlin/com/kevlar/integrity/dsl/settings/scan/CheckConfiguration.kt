package com.kevlar.integrity.dsl.settings.scan

import com.kevlar.integrity.dsl.language.DslBuilder
import com.kevlar.integrity.dsl.language.IntegrityDslMarker

public class CheckConfiguration(
    public val signature: SignatureCheck,
    public val packageName: PackageNameCheck,
    public val debug: DebuggableCheck,
    public val installer: InstallerCheck,
) {

    public companion object {
        public fun default(): CheckConfiguration = CheckConfiguration(
            signature = SignatureCheck(enabled = false),
            packageName = PackageNameCheck(enabled = false),
            debug = DebuggableCheck(enabled = false),
            installer = InstallerCheck(enabled = false),
        )
    }
}


@IntegrityDslMarker
public class CheckConfigurationBuilder : DslBuilder<CheckConfiguration>() {
    private var signature = SignatureCheck(enabled = false)
    private var packageName = PackageNameCheck(enabled = false)
    private var debug = DebuggableCheck(enabled = false)
    private var installer = InstallerCheck(enabled = false)

    public fun signature(block: SignatureCheckBuilder.() -> Unit = {}) {
        signature = SignatureCheckBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun packageName(block: PackageNameCheckBuilder.() -> Unit = {}) {
        packageName = PackageNameCheckBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun debug(block: DebuggableCheckBuilder.() -> Unit = {}) {
        debug = DebuggableCheckBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun installer(block: InstallerCheckBuilder.() -> Unit = {}) {
        installer = InstallerCheckBuilder().apply {
            block()
            enable()
        }.build()
    }

    /**
     * Builds the check configuration
     * */
    override fun build(): CheckConfiguration = CheckConfiguration(signature, packageName, debug, installer)
}
