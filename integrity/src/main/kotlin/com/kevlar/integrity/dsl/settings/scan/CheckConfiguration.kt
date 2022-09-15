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

import com.kevlar.integrity.dsl.language.DslBuilder
import com.kevlar.integrity.dsl.language.IntegrityDslMarker
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedFingerprint
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedSignatures
import com.kevlar.integrity.hardcoded.HardcodedPackageName

public class CheckConfiguration(
    public val signature: SignatureCheck,
    public val packageName: PackageNameCheck,
    public val debug: DebuggableCheck,
    public val installer: InstallerCheck,
) {

    public companion object {
        public fun default(): CheckConfiguration = CheckConfiguration(
            signature = SignatureCheck.default(),
            packageName = PackageNameCheck.default(),
            debug = DebuggableCheck.default(),
            installer = InstallerCheck.default(),
        )
    }
}


@IntegrityDslMarker
public class CheckConfigurationBuilder : DslBuilder<CheckConfiguration>() {
    private var signature = SignatureCheck.default()
    private var packageName = PackageNameCheck.default()
    private var debug = DebuggableCheck.default()
    private var installer = InstallerCheck.default()

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
    override fun build(): CheckConfiguration =
        CheckConfiguration(signature, packageName, debug, installer)
}
