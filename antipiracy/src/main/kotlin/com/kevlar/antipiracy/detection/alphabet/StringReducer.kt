package com.kevlar.antipiracy.detection.alphabet

public object StringReducer {
    public fun reduceChar(char: Char): AlphabetUnit = CharMatcher.identify(char)
    public fun reduceString(str: CharSequence): List<AlphabetUnit> = str.map { reduceChar(it) }.toList()
}