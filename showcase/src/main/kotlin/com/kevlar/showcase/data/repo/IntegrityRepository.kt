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

package com.kevlar.showcase.data.repo

import android.content.Context
import android.util.Base64
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.integrity.KevlarIntegrity
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedSignatures
import com.kevlar.integrity.hardcoded.HardcodedPackageName
import com.kevlar.showcase.concurrency.IoDispatcher
import com.kevlar.showcase.util.EncryptionUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class designed to hold [KevlarAntipiracy]
 * */
class IntegrityRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
    /**
     * Plaintext
     * */
    private val plaintextHardcodedPackageName = HardcodedPackageName(
        packageName = "com.kevlar.showcase"
    )

    private val plaintextHardcodedSignatures = HardcodedBase64EncodedSignatures(
        base64EncodedSignatures = listOf("J+nqXLfuIO8B2AmhkMYHGE4jDyw=")
    )



    /**
     * Base64 obfuscated
     * */
    private val base64PackageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray(Charsets.UTF_8)
    private val base64Signature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray(Charsets.UTF_8)


    private val base64ObfuscatedHardcodedPackageName = HardcodedPackageName(
        packageName = Base64.decode(base64PackageName, Base64.DEFAULT).toString(Charsets.UTF_8)
    )

    private val base64ObfuscatedHardcodedSignatures = HardcodedBase64EncodedSignatures(
        base64EncodedSignatures = listOf(
            Base64.decode(base64Signature, Base64.DEFAULT).toString(Charsets.UTF_8)
        )
    )


    /**
     * AES+Base64 encrypted
     * */
    private val key256 = """4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?Ds"""

    private val encryptedPackageName = """s3wf/AOYtr9BEMVFrweeLnkmerryUykMA8O77S5tMlI=""".toByteArray(Charsets.UTF_8)
    private val encryptedSignature = """tqMJquO3D+EKx1rx4R7/qzmsuEgpp1bKwxXe9AeB/WU=""".toByteArray(Charsets.UTF_8)

    private val aes256EncryptedHardcodedPackageName = HardcodedPackageName(
        packageName = EncryptionUtil.decrypt(encryptedPackageName, EncryptionUtil.generateKey(key256))
    )

    private val aes256EncryptedHardcodedSignatures = HardcodedBase64EncodedSignatures(
        base64EncodedSignatures = listOf(
            EncryptionUtil.decrypt(encryptedSignature, EncryptionUtil.generateKey(key256))
        )
    )


    /**
     * Integrity package
     * */
    private val integrity = KevlarIntegrity {
        checks {
            signature()
            packageName()
            installer()
            debug()
        }
    }

    suspend fun attestate(): IntegrityAttestation = withContext(externalDispatcher) {
        integrity.attestate(context)
    }
}