package com.kevlar.antipiracy.detection.vectors.alphabet

import com.kevlar.antipiracy.dataset.DatasetEntry
import com.kevlar.antipiracy.dataset.MatchableDatasetEntry
import com.kevlar.antipiracy.detection.vectors.alphabet.units.AlphabetUnit

/**
 * Single abstract alphabet string.
 *
 * It is composed of a list of [AlphabetUnit]s, which
 * model single alphabet letters, and a [DatasetEntry]
 * associated with the given label
 * */
internal data class MatchableAlphabetDatasetEntry (
    override val datasetEntry: DatasetEntry,
    val labelAlphabetUnits: List<AlphabetUnit>
) : MatchableDatasetEntry()