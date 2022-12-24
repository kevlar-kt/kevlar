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

package com.kevlar.rooting.dsl.settings.status

import com.kevlar.rooting.dataset.DetectableSystemStatus

/**
 * Data holder returned as an attestation result.
 *
 * @param detectedStatuses  A [Set] containing all the [DetectableSystemStatus]s which have been found on the
 *                          system through kevlar's checks.
 * */
public data class StatusResult(
    val detectedStatuses: Set<DetectableSystemStatus>
) {
    public constructor(list: List<DetectableSystemStatus>) : this(list.toSet())

    public fun isClear(): Boolean = detectedStatuses.isEmpty()

    public companion object {
        public fun empty(): StatusResult = StatusResult(setOf())
    }
}