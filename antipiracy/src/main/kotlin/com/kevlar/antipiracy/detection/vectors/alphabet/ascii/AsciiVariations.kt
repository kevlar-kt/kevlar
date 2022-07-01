package com.kevlar.antipiracy.detection.vectors.alphabet.ascii

/**
 * Contains
 * */
@Suppress("SpellCheckingInspection")
internal class AsciiVariations {
    companion object {
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
}
