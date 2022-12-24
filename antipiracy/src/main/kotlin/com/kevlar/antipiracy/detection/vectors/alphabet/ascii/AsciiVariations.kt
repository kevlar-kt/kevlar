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

package com.kevlar.antipiracy.detection.vectors.alphabet.ascii

/**
 * Maps a single character to a set of characters which may be used
 * to disguise a certain alphabet element.
 * */
@Suppress("SpellCheckingInspection")
internal object AsciiVariations {
    val a = charArrayOf(
        // Uppercase
        'A',
        'a',

        // Cyrillic
        'А',
        'а', 'ӑ', 'ӓ',

        // Fullwidth
        'ａ',
    )

    val b = charArrayOf(
        // Basic Latin
        'B', 'b',
    )


    val c = charArrayOf(
        // Basic Latin
        'с', 'c',

        // Cyrillic C
        'с',

        // Fullwidth
        'ｃ',

        // Cedilla
        'ç',
    )


    val d = charArrayOf(
        // Basic Latin
        'D', 'd',
    )

    val e = charArrayOf(
        // Basic latin
        'e', 'е',

        // Cyrillic
        'є', 'ё', 'е', 'ҽ', 'з', 'э', 'ҿ',

        // Fullwidth
        'ｅ',

        // Boh
        'µ'
    )

    val f = charArrayOf(
        // Basic Latin
        'F', 'f',
    )

    val g = charArrayOf(
        // Basic Latin
        'G', 'g',
    )

    val h = charArrayOf(
        // Basic Latin
        'H', 'h',

        // Cyrillic
        'н', 'һ', 'ӈ', 'Һ',

        // Fullwidth
        'ｈ',

        // Boh
        'Ð',
    )

    val i = charArrayOf(
        // Basic Latin
        'I', 'i',

        // Numbers
        '1',
    )

    val j = charArrayOf(
        // Basic Latin
        'J', 'j',
    )

    val k = charArrayOf(
        // Basic Latin
        'K', 'k',

        // Cyrillic
        'к', 'ќ', 'ӄ',

        // Fullwidth
        'ｋ',
    )

    val l = charArrayOf(
        // Basic Latin
        'L', 'l',

        // Fullwidth
        'Ｌ',

        // Cyrillic El
        'л', 'Л'
    )


    val m = charArrayOf(
        // Basic Latin
        'M', 'm',
    )

    val n = charArrayOf(
        // Basic Latin
        'N', 'n',
    )


    val o = charArrayOf(
        // Basic Latin
        'O', 'o',

        // Numbers
        '0',
    )

    val p = charArrayOf(
        // Basic Latin
        'P', 'p',

        // Cyrillic Er
        'Р', 'Ҏ', 'ҏ', 'р',

        // Fullwidth
        'Ｐ', 'ｐ'
    )

    val q = charArrayOf(
        // Basic Latin
        'Q', 'q',
    )

    val r = charArrayOf(
        // Basic Latin
        'r', 'r',

        // Cyrillic
        'я',

        // Fullwidth
        'ｒ',

        // Boh
        'µ'
    )

    val s = charArrayOf(
        // Basic Latin
        'S', 's',

        // Latin small
        'ꜱ',

        // Cyrillic
        'ѕ',
    )

    val t = charArrayOf(
        // Basic Latin
        'T', 't',

        // Cyrillic
        'т',

        // Fullwidth
        'ｔ',
    )


    val u = charArrayOf(
        // Basic Latin
        'U', 'u',

        // Cyrillic
        'ц', 'џ',

        // Fullwidth
        'ｕ',
    )


    val v = charArrayOf(
        // Basic Latin
        'V', 'v',
    )


    val w = charArrayOf(
        // Basic Latin
        'W', 'w',
    )


    val x = charArrayOf(
        // Basic Latin
        'X', 'x',
    )

    val y = charArrayOf(
        // Basic Latin
        'у', 'y',

        // Cyrillic
        'у', 'ӳ', 'ӱ', 'Ӱ', 'ӯ', 'Ӯ', 'У', 'ў',

        // Fullwidth
        'ｙ',
    )

    val z = charArrayOf(
        // Basic Latin
        'Z', 'z',
    )

    val sepatator = charArrayOf(
        // Quotes
        '"', '`', '´', '\'', '‘', '’', '“', '”',

        // Mashes
        '+', '*', '~', '#', '@', '$', '!', '?',

        // Dashes
        ' ', '_', '-', '–', '—', '─',

        // Slashes
        '/', '\\', '﹨', '＼', '／', '/',

        // Braces
        '(', ')', '[', ']',

        // Fking spaces
        ' ', ' ', '　',

        // Supplements
        '¨',

        // Characters thst should by all means be against Geneva Conventions
        ' ', ' ', ' ', '⁠', ' ', ' ',
    )
}
