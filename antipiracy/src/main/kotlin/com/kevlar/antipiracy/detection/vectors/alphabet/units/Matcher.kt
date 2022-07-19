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
 * Function used to find matches of [inner] in [outer].
 * It returns the index of the beginning of the match (if found),
 * otherwise -1.
 * */
internal fun <U> findMatch(outer: List<U>, inner: List<U>): Int {
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