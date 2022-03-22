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
        space.mapParallel {
            it.probe(applicationInfo)
        }
    )
}