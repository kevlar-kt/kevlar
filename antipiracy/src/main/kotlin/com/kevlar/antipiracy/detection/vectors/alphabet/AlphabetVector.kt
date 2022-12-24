package com.kevlar.antipiracy.detection.vectors.alphabet

import android.content.pm.ApplicationInfo
import com.kevlar.antipiracy.detection.vectors.AntipiracyVector
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector
import com.kevlar.antipiracy.detection.vectors.alphabet.str.AlphabetUnitProducer
import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit
import com.kevlar.antipiracy.detection.vectors.alphabet.units.isContainedIn

internal class AlphabetVector(inputVector: InputVector) : AntipiracyVector(inputVector) {
    override suspend fun probe(applicationInfo: ApplicationInfo): OutputVector {
        val label: CharSequence? = applicationInfo.nonLocalizedLabel

        AlphabetDataset.nonIdentifiable.forEach {
            // Android labels can be null
            if (label != null) {
                val reduced: List<AlphabetUnit> = AlphabetUnitProducer.convertString(label)
                val targetLabel: List<AlphabetUnit> = it.labelAlphabetUnits

                if (reduced.isContainedIn(targetLabel)) {
                    return OutputVector(matchingDataset = it.datasetEntry)
                }
            }
        }

        return OutputVector(matchingDataset = null)
    }
}