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
import com.kevlar.integrity.hardcoded.HardcodedBase64EncodedSignature
import com.kevlar.integrity.hardcoded.HardcodedPackageName
import com.kevlar.showcase.concurrency.IoDispatcher
import com.kevlar.showcase.util.EncryptionUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class designed to hold [KevlarAntipiracy]
 *
 * This class shows not only how to properly implement [KevlarIntegrity],
 * but does so in three (alternative and mutually exclusive) different ways.
 *
 * This is to showcase the obfuscation capabilities of kevlar.
 * The implementations are completely equivalent. The only thing changing is
 * how the data gets stored in the bytecode of the final software, but at runtime
 * the data is decoded using the proper tools in order to give kevlar the correct
 * and hardcoded truth value.
 *
 * We do this in three different flavours:
 * - Plaintext:     **No obfuscation**, the data is kept perfectly visible in the application binary;
 * - Base64:        **Transposition obfuscation**, the data is encoded in the application binary.
 *                  Specifically, the data is stored as s base64-encoded string, and at runtime the data is
 *                  decoded and passed to the data holder, which in turns gets fed in kevlar;
 * - AES+Base64:    **Encryption obfuscation**, the data is stored as an encrypted, base64 encoded string.
 *                  To encrypt the data, you take your plaintext and encrypt it with AES, ECB mode
 *                  using a 256-bit key. You take out the base64-encoded string and store it here.
 *                  Then upon creating the data holder to pass kevlar, you decrypt it on the fly (in
 *                  this case we use an utility class, [EncryptionUtil], which does all the work).
 *
 * */
class IntegrityRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {

    /**
     * Plaintext
     * */
    // region plaintext
    private val plaintextHardcodedPackageName = HardcodedPackageName(
        packageName = "com.kevlar.showcase"
    )

    private val plaintextHardcodedSignature = HardcodedBase64EncodedSignature(
        base64EncodedSignature = "J+nqXLfuIO8B2AmhkMYHGE4jDyw="
    )


    private val integrity_plaintext_obfuscated = KevlarIntegrity {
        checks {
            packageName {
                hardcodedPackageName(plaintextHardcodedPackageName)
            }

            signature {
                hardcodedSignatures(plaintextHardcodedSignature)
            }

            installer {
                allowInstaller("com.sec.android.app.samsungapps")
            }

            debug()
        }
    }
    // endregion plaintext



    /**
     * Base64 obfuscated package name and signature
     * */
    // region base64
    private val base64PackageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray()
    private val base64Signature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray()


    private val base64ObfuscatedHardcodedPackageName = HardcodedPackageName(
        packageName = Base64.decode(base64PackageName, Base64.DEFAULT).toString(Charsets.UTF_8)
    )

    private val base64ObfuscatedHardcodedSignature = HardcodedBase64EncodedSignature(
        Base64.decode(base64Signature, Base64.DEFAULT).toString(Charsets.UTF_8)
    )


    private val integrity_base64_obfuscated = KevlarIntegrity {
        checks {
            packageName {
                hardcodedPackageName(base64ObfuscatedHardcodedPackageName)
            }

            signature {
                hardcodedSignatures(base64ObfuscatedHardcodedSignature)
            }

            installer()
            debug()
        }
    }
    // endregion base64




    /**
     * AES ECB + Base64 encrypted data
     * */
    // region aes

    // Our arbitrary 256-bit encryption key
    private val aesKey256 = """4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?Ds"""

    // This is "com.kevlar.showcase", encrypted using AES256 with the previous key
    private val encryptedPackageName = """s3wf/AOYtr9BEMVFrweeLnkmerryUykMA8O77S5tMlI=""".toByteArray()

    // This is "J+nqXLfuIO8B2AmhkMYHGE4jDyw=", encrypted using AES256 with the previous key
    private val encryptedSignature = """tqMJquO3D+EKx1rx4R7/qzmsuEgpp1bKwxXe9AeB/WU=""".toByteArray()


    private val aes256EncryptedHardcodedPackageName = HardcodedPackageName(
        packageName = EncryptionUtil.decrypt(
            encryptedPackageName,
            EncryptionUtil.generateKey(aesKey256)
        )
    )

    private val aes256EncryptedHardcodedSignatures = HardcodedBase64EncodedSignature(
        EncryptionUtil.decrypt(encryptedSignature, EncryptionUtil.generateKey(aesKey256))
    )


    private val integrity_aes256_encrypted = KevlarIntegrity {
        checks {
            packageName {
                hardcodedPackageName(aes256EncryptedHardcodedPackageName)
            }

            signature {
                hardcodedSignatures(aes256EncryptedHardcodedSignatures)
            }

            installer()
            debug()
        }
    }
    // endregion aes


    /**
     * Integrity package
     * */
    suspend fun attestate(): IntegrityAttestation = withContext(externalDispatcher) {
        integrity_plaintext_obfuscated.attestate(context) // any other instance is equivalent
    }
}
