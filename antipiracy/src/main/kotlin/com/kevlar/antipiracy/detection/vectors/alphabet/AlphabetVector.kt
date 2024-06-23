package com.kevlar.antipiracy.detection.vectors.alphabet

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.dataset.ScanEntry
import com.kevlar.antipiracy.detection.vectors.AntipiracyVector
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector
import com.kevlar.antipiracy.detection.vectors.alphabet.str.AlphabetUnitProducer
import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit
import com.kevlar.antipiracy.detection.vectors.alphabet.units.isContainedIn

internal class AlphabetVector(inputVector: InputVector) : AntipiracyVector(inputVector) {
    override suspend fun probe(applicationInfo: ApplicationInfo): OutputVector {
        val appLabel: CharSequence? = applicationInfo.nonLocalizedLabel

        AlphabetDataset.nonIdentifiable.forEach {
            // Android labels can be null
            if (appLabel != null) {
                val reduced: List<AlphabetUnit> = AlphabetUnitProducer.convertString(appLabel)
                val targetLabel: List<AlphabetUnit> = it.associatedAlphabetizedLabel

                if (reduced.isContainedIn(targetLabel)) {
                    return OutputVector(
                        scanEntry = ScanEntry(
                            threat = it.threat,
                            packageName = applicationInfo.packageName,
                            detectionLog = "Detected [appLabel=$appLabel] masked as [target=$reduced], app package is [packageName=${applicationInfo.packageName}]"
                        )
                    )
                }
            }
        }

        return OutputVector(scanEntry = null)
    }
}