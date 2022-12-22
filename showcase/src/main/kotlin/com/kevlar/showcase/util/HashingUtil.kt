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

package com.kevlar.showcase.util

import java.nio.charset.Charset
import java.security.MessageDigest

enum class HashingAlgorithm {
    MD5, SHA1, SHA256
}

object HashingUtil {
    fun hash(
        hashAlgorithm: HashingAlgorithm,
        string: String,
        charset: Charset = Charsets.UTF_8
    ): String {
        val bytes = string.toByteArray(charset)

        val output: ByteArray = MessageDigest.getInstance(hashAlgorithm.name).run {
            update(bytes)
            digest()
        }

        return output.toString()
    }
}