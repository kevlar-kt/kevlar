package com.kevlar.antipiracy.detection.vectors.alphabet

import com.kevlar.antipiracy.detection.dataset.DatasetEntry
import com.kevlar.antipiracy.detection.dataset.MatchableDatasetEntry
import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit

internal data class MatchableAlphabetDatasetEntry (
    override val datasetEntry: DatasetEntry,
    val labelAlphabetUnits: List<AlphabetUnit>
) : MatchableDatasetEntry()