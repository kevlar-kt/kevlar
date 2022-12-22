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
 *
 */

package com.kevlar.integrity.checks.signature

import android.annotation.SuppressLint
import android.content.Context
import com.kevlar.integrity.checks.encodeAsBase64
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedSignatures

/**
 * Checks the application signature(s) and matches them against the hardcoded one.
 *
 * @return whether the hardcoded signature matches the running application's one(s).
 * */
@SuppressLint("PackageManagerGetSignatures")
internal fun matchesHardcodedSignature(
    hardcodedBase64EncodedSignatures: HardcodedBase64EncodedSignatures,
    context: Context
): Boolean {
    // The list of signatures which are allowed by the application security policy
    // to have signed the application binary.
    // They are base64-encoded.
    val allowedSignatures = hardcodedBase64EncodedSignatures.base64EncodedSignatures

    return try {
        // The list of signatures the current running binary has been signed with (usually only one)
        val runtimeSignatureList = extractPackageSignatures(context)

        /**
         * We are guaranteed to have at least one signature, even if it is for debug
         * */
        for (signature in runtimeSignatureList) {
            val runtimeSignature = signature.encodeAsBase64()

            // If any of the given valid signatures matches the current one
            if (allowedSignatures.contains(runtimeSignature)) {
                return true
            }
        }

        false
    } catch (_: RuntimeException) {
        false
    }
}

internal fun obtainBase64EncodedSignatures(
    context: Context
): List<String> = extractPackageSignatures(context).map { it.encodeAsBase64() }
