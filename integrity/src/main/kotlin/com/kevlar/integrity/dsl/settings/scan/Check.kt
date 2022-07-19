package com.kevlar.integrity.dsl.settings.scan

import com.kevlar.integrity.constants.IntegrityConstants
import com.kevlar.integrity.dsl.language.IntegrityDslMarker

/**
 * [Check] structure
 * */
public abstract class Check {
    public abstract val enabled: Boolean
}

public data class SignatureCheck(
    override val enabled: Boolean
) : Check()


public data class PackageNameCheck(
    override val enabled: Boolean
) : Check()


public data class InstallerCheck(
    override val enabled: Boolean,
    val allowedInstallers: List<String> = listOf(IntegrityConstants.PSPN)
) : Check()


public data class DebuggableCheck(
    override val enabled: Boolean
) : Check()




@IntegrityDslMarker
public class SignatureCheckBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): SignatureCheck = SignatureCheck(enabled)
}


@IntegrityDslMarker
public class PackageNameCheckBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): PackageNameCheck = PackageNameCheck(enabled)
}


@IntegrityDslMarker
public class InstallerCheckBuilder {
    private var enabled: Boolean = false
    private val allowedInstallers: MutableList<String> = mutableListOf(IntegrityConstants.PSPN)

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun allowInstaller(installerPackageName: String) {
        allowedInstallers.add(installerPackageName)
    }

    public fun allowInstallers(installersPackageNames: List<String>) {
        allowedInstallers.addAll(installersPackageNames)
    }

    public fun allowInstallers(vararg installersPackageNames: String) {
        allowedInstallers.addAll(installersPackageNames)
    }

    public fun build(): InstallerCheck = InstallerCheck(enabled)
}


@IntegrityDslMarker
public class DebuggableCheckBuilder {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): DebuggableCheck = DebuggableCheck(enabled)
}
