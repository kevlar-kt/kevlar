package com.kevlar.antipiracy.detection.vectors.alphabet

internal object StringReducer {
    fun reduceChar(char: Char): AlphabetUnit = CharMatcher.identify(char)
    fun reduceString(str: CharSequence): List<AlphabetUnit> = str.map { reduceChar(it) }.toList()
}