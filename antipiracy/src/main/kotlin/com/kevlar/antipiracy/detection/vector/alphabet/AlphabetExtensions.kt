package com.kevlar.antipiracy.detection.vector.alphabet

internal fun List<AlphabetUnit>.stringifyLower(): String = this.map { it.asciiLower }.toString()
internal fun List<AlphabetUnit>.stringifyUpper(): String = this.map { it.asciiUpper }.toString()