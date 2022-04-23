package com.kevlar.antipiracy.detection.vectors.alphabet.units

import androidx.annotation.IntRange

/**
 * Overrides `in` operator for [List<AlphabetUnit>]
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
 * Returns whether [outer] contains [this] (the inner string)
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