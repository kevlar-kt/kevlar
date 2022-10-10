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

package com.kevlar.integrity.hardcoded

/**
 * Hardcoded data holder.
 *
 * Keep in mind that data should come in a intelligible form inside this
 * class, as they will be used to match against the running application
 * ones. You should obfuscate their bytecode serialization, not the data itself.
 * */

/**
 * The theoretically correct package name for the current app.
 * */
public data class HardcodedPackageName(
    val packageName: String
) {
    /**
     * Whether the current hardcoded data holder contains valid data, and thus needs to be processed
     * */
    internal val valid: Boolean
        get() = packageName.isNotBlank()

    internal companion object {
        fun defaultInvalid() = HardcodedPackageName("")
    }
}


/**
 * List of **trusted** package signatures for the current application (usually it's only one)
 *
 * @param base64EncodedSignatures The list of trusted signatures, encoded as base-64 strings
 * */
public data class HardcodedBase64EncodedSignatures(
    val base64EncodedSignatures: List<String>
) {
    /**
     * Whether the current hardcoded data holder contains valid data, and thus needs to be processed
     * */
    internal val valid: Boolean
        get() = base64EncodedSignatures.isNotEmpty()

    internal companion object {
        fun getDefaultInvalid() = HardcodedBase64EncodedSignatures(listOf())
    }
}


/**
 * List of ++trusted++ signing certificate fingerprints for the current application (usually it's only one)
 *
 * @param base64EncodedFingerprints The list of trusted fingerprints, encoded as base-64 strings
 * */
public data class HardcodedBase64EncodedFingerprint(
    val base64EncodedFingerprints: List<String>
) {
    /**
     * Whether the current hardcoded data holder contains valid data, and thus needs to be processed
     * */
    internal val valid: Boolean
        get() = base64EncodedFingerprints.isNotEmpty()

    internal companion object {
        fun getDefaultInvalid() = HardcodedBase64EncodedFingerprint(listOf())
    }
}