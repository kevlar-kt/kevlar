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

package com.kevlar.integrity.dsl.settings.scan

import com.kevlar.integrity.constants.IntegrityConstants
import com.kevlar.integrity.dsl.language.IntegrityDslMarker
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedFingerprint
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedSignatures
import com.kevlar.integrity.hardcoded.HardcodedPackageName

/**
 * [Check] structure
 * */
public abstract class Check {
    public abstract val enabled: Boolean
}

public data class SignatureCheck(
    override val enabled: Boolean,
    val hardcodedBase64EncodedSignatures: HardcodedBase64EncodedSignatures,
    val hardcodedBase64EncodedFingerprints: HardcodedBase64EncodedFingerprint,
) : Check() {

    internal companion object {
        fun default(): SignatureCheck = SignatureCheck(
            enabled = false,
            hardcodedBase64EncodedSignatures = HardcodedBase64EncodedSignatures.getDefaultInvalid(),
            hardcodedBase64EncodedFingerprints = HardcodedBase64EncodedFingerprint.getDefaultInvalid()
        )
    }
}


public data class PackageNameCheck(
    override val enabled: Boolean,
    val hardcodedPackageName: HardcodedPackageName
) : Check() {

    internal companion object {
        fun default(): PackageNameCheck = PackageNameCheck(
            enabled = false,
            hardcodedPackageName = HardcodedPackageName.defaultInvalid()
        )
    }
}


public data class InstallerCheck(
    override val enabled: Boolean,
    val allowedInstallers: List<String> = listOf(IntegrityConstants.PSPN)
) : Check() {
    internal companion object {
        fun default(): InstallerCheck = InstallerCheck(enabled = false)
    }
}


public data class DebuggableCheck(
    override val enabled: Boolean
) : Check() {
    internal companion object {
        fun default(): DebuggableCheck = DebuggableCheck(enabled = false)
    }
}


@IntegrityDslMarker
public class SignatureCheckBuilder {
    private var enabled: Boolean = false

    private var hardcodedBase64EncodedSignatures: HardcodedBase64EncodedSignatures =
        HardcodedBase64EncodedSignatures(listOf())

    private var hardcodedBase64EncodedFingerprints: HardcodedBase64EncodedFingerprint =
        HardcodedBase64EncodedFingerprint(listOf())

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun hardcodedSignatures(hardcodedBase64EncodedSignatures: HardcodedBase64EncodedSignatures) {
        this.hardcodedBase64EncodedSignatures = hardcodedBase64EncodedSignatures
    }

    public fun hardcodedFingerprints(hardcodedBase64EncodedFingerprints: HardcodedBase64EncodedFingerprint) {
        this.hardcodedBase64EncodedFingerprints = hardcodedBase64EncodedFingerprints
    }

    public fun build(): SignatureCheck = SignatureCheck(
        enabled,
        hardcodedBase64EncodedSignatures,
        hardcodedBase64EncodedFingerprints
    )
}


@IntegrityDslMarker
public class PackageNameCheckBuilder {
    private var enabled: Boolean = false

    private var hardcodedPackageName: HardcodedPackageName =
        HardcodedPackageName("") // invalid by default

    internal fun enable() {
        enabled = true
    }

    public fun hardcodedPackageName(hardcodedPackageName: HardcodedPackageName) {
        this.hardcodedPackageName = hardcodedPackageName
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): PackageNameCheck = PackageNameCheck(enabled, hardcodedPackageName)
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
