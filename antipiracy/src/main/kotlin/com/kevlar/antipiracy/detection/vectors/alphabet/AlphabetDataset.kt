package com.kevlar.antipiracy.detection.vectors.alphabet

import com.kevlar.antipiracy.detection.dataset.DatasetEntry
import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit

internal object AlphabetDataset {

    val nonIdentifiable = arrayOf(
        MatchableAlphabetDatasetEntry(
            DatasetEntry.LUCKY_PATCHER,
            listOf(
                AlphabetUnit.L, AlphabetUnit.U, AlphabetUnit.C, AlphabetUnit.K, AlphabetUnit.Y,
                AlphabetUnit.SEPARATOR,
                AlphabetUnit.P, AlphabetUnit.A, AlphabetUnit.T, AlphabetUnit.C, AlphabetUnit.H, AlphabetUnit.E, AlphabetUnit.R
            )
        )
    )
}