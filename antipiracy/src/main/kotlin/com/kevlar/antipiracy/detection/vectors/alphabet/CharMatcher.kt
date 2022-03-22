package com.kevlar.antipiracy.detection.vectors.alphabet

internal object CharMatcher {
    fun identify(char: Char): AlphabetUnit = when {
        AsciiVariations.a.contains(char) -> AlphabetUnit.A
        AsciiVariations.b.contains(char) -> AlphabetUnit.B
        AsciiVariations.c.contains(char) -> AlphabetUnit.C
        AsciiVariations.d.contains(char) -> AlphabetUnit.D
        AsciiVariations.e.contains(char) -> AlphabetUnit.E
        AsciiVariations.f.contains(char) -> AlphabetUnit.F
        AsciiVariations.g.contains(char) -> AlphabetUnit.G
        AsciiVariations.h.contains(char) -> AlphabetUnit.H
        AsciiVariations.i.contains(char) -> AlphabetUnit.I
        AsciiVariations.j.contains(char) -> AlphabetUnit.J
        AsciiVariations.k.contains(char) -> AlphabetUnit.K
        AsciiVariations.l.contains(char) -> AlphabetUnit.L
        AsciiVariations.m.contains(char) -> AlphabetUnit.M
        AsciiVariations.n.contains(char) -> AlphabetUnit.N
        AsciiVariations.o.contains(char) -> AlphabetUnit.O
        AsciiVariations.p.contains(char) -> AlphabetUnit.P
        AsciiVariations.q.contains(char) -> AlphabetUnit.Q
        AsciiVariations.r.contains(char) -> AlphabetUnit.R
        AsciiVariations.s.contains(char) -> AlphabetUnit.S
        AsciiVariations.t.contains(char) -> AlphabetUnit.T
        AsciiVariations.u.contains(char) -> AlphabetUnit.U
        AsciiVariations.v.contains(char) -> AlphabetUnit.V
        AsciiVariations.w.contains(char) -> AlphabetUnit.W
        AsciiVariations.x.contains(char) -> AlphabetUnit.X
        AsciiVariations.y.contains(char) -> AlphabetUnit.Y
        AsciiVariations.z.contains(char) -> AlphabetUnit.Z

        AsciiVariations.sepatator.contains(char) -> AlphabetUnit.SEPARATOR
        else -> AlphabetUnit.NOT_EXHAUSTIVE
    }
}