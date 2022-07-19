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

package com.kevlar.antipiracy.detection.vectors.alphabet.units

import androidx.annotation.IntRange

/**
 * Overrides `in` operator for [AlphabetUnit] lists.
 * */
internal operator fun List<AlphabetUnit>.contains(other: List<AlphabetUnit>): Boolean {
    return when {
        size <= other.size -> {
            // other is the outer or equal
            findMatch(other, this) >= 0
        }
        else -> {
            // this is the outer
            findMatch(this, other) >= 0
        }
    }
}



/**
 * Returns whether [outer] contains [this] (the inner string).
 * * */
internal fun List<AlphabetUnit>.isContainedIn(
    outer: List<AlphabetUnit>
): Boolean {
    return this in outer
}

/**
 * We assume that both packages have the same length and
 * we start from the leftmost character.
 *
 * This can easily be changed, tuned and adapter to find
 * more intricate patterns.
 * */
internal fun List<AlphabetUnit>.equalsWithProbabilityGreaterOrEqual(
    @IntRange(from = 1L, to = 100L) probability: Int,
    other: List<AlphabetUnit>
): Boolean {
    var matchCount = 0

    zip(other).forEach {
        if (it.first == it.second) {
            matchCount++
        }
    }

    // If our match rate is greater than the required probability
    return matchCount / size >= probability / 100
}

internal fun List<AlphabetUnit>.stringifyLower(): String = this.map { it.asciiLower }.toString()
internal fun List<AlphabetUnit>.stringifyUpper(): String = this.map { it.asciiUpper }.toString()