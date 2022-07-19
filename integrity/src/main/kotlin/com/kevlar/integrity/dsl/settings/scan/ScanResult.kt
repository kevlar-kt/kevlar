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

package com.kevlar.integrity.dsl.settings.scan

import com.kevlar.integrity.dataset.IntegrityElement

public data class CheckResult(
    val detectedElements: Set<IntegrityElement>
) {
    public constructor(detectedEntries: List<IntegrityElement>) : this(detectedEntries.toSet())

    public companion object {
        public fun empty(): CheckResult = CheckResult(setOf())
    }

    public fun isClear(): Boolean = detectedElements.isEmpty()
}