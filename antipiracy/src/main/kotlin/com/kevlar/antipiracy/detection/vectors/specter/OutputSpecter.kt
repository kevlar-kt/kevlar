package com.kevlar.antipiracy.detection.vectors.specter

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.detection.vectors.OutputVector

/**
 * Full scan specter containing probing results
 * for each vector in the [VectorSpecter] space.
 * */
internal class OutputSpecter(
    val applicationInfo: ApplicationInfo,
    val matchingVectors: List<OutputVector>
)