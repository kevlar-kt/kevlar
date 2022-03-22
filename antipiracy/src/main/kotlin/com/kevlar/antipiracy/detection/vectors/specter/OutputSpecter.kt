package com.kevlar.antipiracy.detection.vectors.specter

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.detection.vectors.OutputVector

/**
 * Set of vector results
 * */
internal class OutputSpecter(
    val applicationInfo: ApplicationInfo,
    val matchingVectors: List<OutputVector>
)