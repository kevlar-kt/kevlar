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

package com.kevlar.antipiracy.detection.vectors.alphabet

import com.kevlar.antipiracy.dataset.Threat
import com.kevlar.antipiracy.dataset.ThreatDetectionSuite
import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit

/**
 * Single abstract alphabet string.
 *
 * It is composed of a list of [AlphabetUnit]s, which
 * model single alphabet letters, and a [Threat]
 * associated with the given label.
 * */
internal data class AlphabetThreatDetectionSuite (
    override val threat: Threat,
    val associatedAlphabetizedLabel: List<AlphabetUnit>
) : ThreatDetectionSuite()