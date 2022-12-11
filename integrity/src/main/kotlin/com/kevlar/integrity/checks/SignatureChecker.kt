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

package com.kevlar.integrity.checks

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.util.Base64
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedSignatures
import java.security.MessageDigest

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
    val allowedSignatures = hardcodedBase64EncodedSignatures.base64EncodedSignatures

    return try {
        val signatures = context.getPackageSignatures()

        /**
         * We are guaranteed to have at least one signature, even if it is for debug
         * */
        signatures.forEach { signature ->
            val runtimeSignature = signature.toByteArray().encodeAsBase64()

            // If any of the given valid signatures matches the current one
            if (allowedSignatures.contains(runtimeSignature)) {
                return true
            }
        }

        false
    } catch (expected: Exception) {
        false
    }
}

internal fun obtainBase64EncodedSignatures(
    context: Context
): List<String> = context.getPackageSignatures().map { it.toByteArray().encodeAsBase64() }

internal fun ByteArray.encodeAsBase64(): String {
    val md = MessageDigest.getInstance("SHA").apply {
        update(this@encodeAsBase64)
    }

    return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
}

@Suppress("DEPRECATION", "PackageManagerGetSignatures")
internal fun Context.getPackageSignatures(): List<Signature> {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.PackageInfoFlags.of(PackageManager.GET_SIGNING_CERTIFICATES.toLong()),
            ).signingInfo.signingCertificateHistory
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            ).signingInfo.signingCertificateHistory
        }

        else -> {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            ).signatures
        }
    }.filterNotNull()
}
