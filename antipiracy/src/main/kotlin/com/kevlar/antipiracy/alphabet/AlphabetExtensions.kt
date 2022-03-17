package com.kevlar.antipiracy.alphabet

public fun List<AlphabetUnit>.stringifyLower(): String = this.map { it.asciiLower }.toString()
public fun List<AlphabetUnit>.stringifyUpper(): String = this.map { it.asciiUpper }.toString()