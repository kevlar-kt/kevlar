package com.kevlar.antipiracy.detection.vectors.alphabet.str

import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit

internal object StringReducer {
    fun reduceChar(char: Char): AlphabetUnit = CharMatcher.identify(char)
    fun reduceString(str: CharSequence): List<AlphabetUnit> = str.map { reduceChar(it) }.toList()
}