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
 * A single check which can be run from kevlar.
 * Only requested tests are ran.
 * */
public enum class IntegrityElement {
    /**
     * At least one between the signature or the fingerprint test.
     * */
    MATCH_HARDCODED_SIGNATURE_OR_FINGERPRINT,

    MATCH_HARDCODED_PACKAGE_NAME,
    DEBUG_BUILD,
    UNAUTHORIZED_INSTALLER;
}