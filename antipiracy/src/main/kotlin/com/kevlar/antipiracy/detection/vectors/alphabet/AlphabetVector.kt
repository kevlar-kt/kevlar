package com.kevlar.antipiracy.detection.vectors.alphabet

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.detection.vectors.AntipiracyVector
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector

internal class AlphabetVector(inputVector: InputVector) : AntipiracyVector(inputVector) {
    override suspend fun probe(applicationInfo: ApplicationInfo): OutputVector {

    }
}