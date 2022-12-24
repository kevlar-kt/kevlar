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

package com.kevlar.antipiracy.detection.vectors.alphabet.units

/**
 * Models an abstract alphabet element (unit).
 * */
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