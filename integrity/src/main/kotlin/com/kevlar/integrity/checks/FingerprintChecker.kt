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
import android.content.pm.Signature
import android.os.Message
import com.kevlar.integrity.hardcoded.FingerprintHashType
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedFingerprint
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Objects.hash

/**
 * Checks the application signature(s) and matches them against the hardcoded one.
 *
 * @param hardcodedBase64EncodedFingerprints        the class holding the list (usually only one)
 *                                                  of the allowed (and base64-encoded) fingerprints
 *
 * @return whether the hardcoded signature matches the running application's one(s).
 * */
@SuppressLint("PackageManagerGetSignatures")
internal fun matchesHardcodedFingerprint(
    hardcodedBase64EncodedFingerprints: HardcodedBase64EncodedFingerprint,
    context: Context
): Boolean {
    val (allowedFingerprints, hashType) = hardcodedBase64EncodedFingerprints
    val base64FingerprintHashes = obtainBase64EncodedFingerprints(context, hashType)

    return base64FingerprintHashes.any { it in allowedFingerprints }
}

/**
 * Returns the running application base64-encoded fingerprint (for debug purposes)
 * */
@SuppressLint("PackageManagerGetSignatures")
internal fun obtainBase64EncodedFingerprints(
    context: Context,
    hashType: FingerprintHashType
): List<String> = context
    .getPackageSignatures()
    .asSequence()
    .map { requireNotNull(it.hash(hashType)) { "Hash type not supported" } }
    .map { it.toByteArray().encodeAsBase64() }
    .toList()


internal fun Signature.hash(type: FingerprintHashType): String? = try {
    MessageDigest.getInstance(type.name).run {
        update(this@hash.toByteArray())
        digest().toHex()
    }
} catch (expected: NoSuchAlgorithmException) {
    null
}

internal fun ByteArray.toHex(): String =
    joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
