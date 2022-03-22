package com.kevlar.antipiracy.detection.vectors.alphabet.units

import androidx.annotation.IntRange

internal enum class AlphabetUnit(val asciiLower: Char, val asciiUpper: Char) {
    A('a', 'A'),
    B('b', 'B'),
    C('c', 'C'),
    D('d', 'D'),
    E('e', 'E'),
    F('f', 'F'),
    G('g', 'G'),
    H('h', 'H'),
    I('i', 'I'),
    J('j', 'J'),
    K('k', 'K'),
    L('l', 'L'),
    M('m', 'M'),
    N('n', 'N'),
    O('o', 'O'),
    P('p', 'P'),
    Q('q', 'Q'),
    R('r', 'R'),
    S('s', 'S'),
    T('t', 'T'),
    U('u', 'U'),
    V('v', 'V'),
    W('w', 'W'),
    X('x', 'X'),
    Y('y', 'Y'),
    Z('z', 'Z'),
    SEPARATOR(' ', ' '),
    NOT_EXHAUSTIVE('?', '?');
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