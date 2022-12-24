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

package com.kevlar.integrity.dataset

/**
 * This enumeration contains all the possible issues that kevlar may report in an attestation result.
 *
 * They are used to pinpoint checks that kevlar can run, and they are included in the attestation to
 * signify that a specific test has produced a negative result
 *
 * Keep in mind that kevlar will run **only** the checks you explicitly requested in its configuration.
 *
 * ```kotlin
 * checks {
 *     packageName {
 *         hardcodedPackageName(packageNameData)
 *     }
 *
 *     signature {
 *         hardcodedSignatures(signatureData)
 *     }
 *
 *     installer()
 *     debug()
 * }
 * ```
 *
 * This means that you have to enable a specific option (see documentation) in order to tell kevlar to
 * actually run the check for it, which will eventually be reported here (if it is found to be present
 * on the host system).
 * */
public enum class IntegrityElement {
    /**
     * Signature check element.
     *
     * This item will be included in your attestation if your running binary's signature does not match
     * the hardcoded signature you passed inside kevlar's configuration.
     * */
    MATCH_HARDCODED_SIGNATURE,

    /**
     * Package name element.
     *
     * This item will be included in your attestation if your running binary's package name does not match
     * the hardcoded package name you passed inside kevlar's configuration.
     * */
    MATCH_HARDCODED_PACKAGE_NAME,

    /**
     * Debug element.
     *
     * This item will be included in your attestation if your running binary is found to have debug
     * configurations turned on.
     * */
    DEBUG_BUILD,

    /**
     * Original package installer element.
     *
     * This item will be included in your attestation if your running binary's installer is not
     * in the allowed installer list.
     * By default only the Google Play Store is set as an allowed installer, but you can pass
     * additional allowed installer packages whihc will be whitelisted while running this check.
     * */
    UNAUTHORIZED_INSTALLER;
}