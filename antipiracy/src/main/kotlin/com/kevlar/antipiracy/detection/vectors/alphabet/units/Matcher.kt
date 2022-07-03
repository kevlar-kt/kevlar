package com.kevlar.antipiracy.detection.vectors.alphabet.units

/**
 * Function used to find matches of [inner] in [outer].
 * It returns the index of the beginning of the match (if found),
 * otherwise -1
 * */
internal fun <D> findMatch(outer: List<D>, inner: List<D>): Int {
    val range = outer.size - inner.size
    var j: Int

    for (i in 0..range) {
        j = 0

        while ((j < inner.size) && (outer[i + j] == inner[j])) {
            j++
        }

        if (j == inner.size) {
            return i
        }
    }

    return -1
}