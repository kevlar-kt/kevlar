package com.kevlar.antipiracy.detection.vectors

import android.content.pm.ApplicationInfo

/**
 * Formalization of scan rules through different detection vectors.
 * */
internal abstract class AntipiracyVector(private val inputVector: InputVector) {
    /**
     * Called upon scanning of a single package unit
     * */
    abstract suspend fun probe(applicationInfo: ApplicationInfo): OutputVector
}