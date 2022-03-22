package com.kevlar.antipiracy.detection.vectors.specter

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.alphabet.AlphabetVector
import com.kevlar.antipiracy.detection.vectors.heuristic.HeuristicVector
import com.kevlar.antipiracy.parallel.mapParallel

/**
 * Full specter of runnable checks
 * */
internal class VectorSpecter(
    inputVector: InputVector
) {
    /**
     * The space containing our [AntipiracyVector]s.
     * */
    private val space = listOf(
        AlphabetVector(inputVector),
        HeuristicVector(inputVector)
    )

    suspend fun probeSpace(applicationInfo: ApplicationInfo): OutputSpecter = OutputSpecter(
        applicationInfo,
        space.mapParallel {
            it.probe(applicationInfo)
        }
    )
}