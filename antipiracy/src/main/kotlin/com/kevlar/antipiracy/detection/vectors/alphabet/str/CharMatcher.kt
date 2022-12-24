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

package com.kevlar.antipiracy.detection.vectors.alphabet.str

import com.kevlar.antipiracy.detection.vectors.alphabet.ascii.AsciiVariations
import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit

/**
 * Horrible class doing character matching.
 *
 * We should create a map which has keys as the characters, and value as the [AlphabetUnit] matching
 * to the key, to improve performance of character lookup.
 * */
internal object CharMatcher {

    fun identify(char: Char): AlphabetUnit = when {
        AsciiVariations.a.contains(char) -> AlphabetUnit.A
        AsciiVariations.e.contains(char) -> AlphabetUnit.E
        AsciiVariations.i.contains(char) -> AlphabetUnit.I
        AsciiVariations.o.contains(char) -> AlphabetUnit.O

        AsciiVariations.t.contains(char) -> AlphabetUnit.T
        AsciiVariations.n.contains(char) -> AlphabetUnit.N
        AsciiVariations.r.contains(char) -> AlphabetUnit.R
        AsciiVariations.s.contains(char) -> AlphabetUnit.S

        AsciiVariations.b.contains(char) -> AlphabetUnit.B
        AsciiVariations.c.contains(char) -> AlphabetUnit.C
        AsciiVariations.d.contains(char) -> AlphabetUnit.D
        AsciiVariations.f.contains(char) -> AlphabetUnit.F
        AsciiVariations.g.contains(char) -> AlphabetUnit.G
        AsciiVariations.h.contains(char) -> AlphabetUnit.H
        AsciiVariations.j.contains(char) -> AlphabetUnit.J
        AsciiVariations.k.contains(char) -> AlphabetUnit.K
        AsciiVariations.l.contains(char) -> AlphabetUnit.L
        AsciiVariations.m.contains(char) -> AlphabetUnit.M
        AsciiVariations.p.contains(char) -> AlphabetUnit.P
        AsciiVariations.q.contains(char) -> AlphabetUnit.Q
        AsciiVariations.u.contains(char) -> AlphabetUnit.U
        AsciiVariations.v.contains(char) -> AlphabetUnit.V
        AsciiVariations.w.contains(char) -> AlphabetUnit.W
        AsciiVariations.x.contains(char) -> AlphabetUnit.X
        AsciiVariations.y.contains(char) -> AlphabetUnit.Y
        AsciiVariations.z.contains(char) -> AlphabetUnit.Z

        // Space or anything that may be used as a separator
        AsciiVariations.sepatator.contains(char) -> AlphabetUnit.SEPARATOR

        // Unclear character
        else -> AlphabetUnit.NOT_EXHAUSTIVE
    }
}