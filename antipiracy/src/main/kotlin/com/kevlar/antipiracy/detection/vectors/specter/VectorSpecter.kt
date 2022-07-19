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

package com.kevlar.antipiracy.detection.vectors.specter

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.detection.vectors.AntipiracyVector
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.alphabet.AlphabetVector
import com.kevlar.antipiracy.detection.vectors.heuristic.HeuristicVector
import com.kevlar.antipiracy.parallel.mapParallel

/**
 * Full specter of runnable probes through different [AntipiracyVector]s.
 * Useful to coordinate vector creation and probing.
 * */
internal class VectorSpecter(
    inputVector: InputVector
) {
    /**
     * The space containing all available [AntipiracyVector]s.
     * */
    private val space = listOf(
        AlphabetVector(inputVector),
        HeuristicVector(inputVector)
    )

    /**
     * Returns a specter containing all the [space] probing results.
     * */
    suspend fun probeSpace(applicationInfo: ApplicationInfo): OutputSpecter = OutputSpecter(
        applicationInfo,
        space.map {
            it.probe(applicationInfo)
        }
    )
}