package com.kevlar.antipiracy.detection.vectors.alphabet

import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit

internal fun List<AlphabetUnit>.stringifyLower(): String = this.map { it.asciiLower }.toString()
internal fun List<AlphabetUnit>.stringifyUpper(): String = this.map { it.asciiUpper }.toString()