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

package com.kevlar.showcase.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private const val algorithm = "AES"
    private const val transformation = "AES/ECB/PKCS5Padding"

    fun generateKey(key: String): SecretKey = SecretKeySpec(key.toByteArray(), algorithm)

    fun encrypt(text: ByteArray, secret: SecretKey): String {
        val cipher: Cipher = Cipher.getInstance(transformation).apply {
            init(Cipher.ENCRYPT_MODE, secret)
        }

        return Base64.encodeToString(cipher.doFinal(text), Base64.NO_WRAP) ?: ""
    }

    fun decrypt(ciphertext: ByteArray, secret: SecretKey): String {
        val cipher: Cipher = Cipher.getInstance(transformation).apply {
            init(Cipher.DECRYPT_MODE, secret)
        }

        return String(cipher.doFinal(Base64.decode(ciphertext, Base64.NO_WRAP)), Charsets.UTF_8)
    }
}