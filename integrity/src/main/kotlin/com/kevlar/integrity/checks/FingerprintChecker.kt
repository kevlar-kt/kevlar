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
import android.util.Base64
import java.security.MessageDigest

/**
 * Checks the application signature(s) and matches them against the hardcoded one.
 *
 * @return whether the hardcoded signature matches the running application's one(s).
 * */
@SuppressLint("PackageManagerGetSignatures")
internal fun matchesHardcodedFingerprint(
    hardcodedFingerprints: List<String>,
    context: Context
): Boolean = TODO()

@SuppressLint("PackageManagerGetSignatures")
internal fun obtainBase64EncodedFingerprints(
    context: Context
): List<String> = TODO()