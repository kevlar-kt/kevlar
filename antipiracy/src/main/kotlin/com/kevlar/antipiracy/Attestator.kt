package com.kevlar.antipiracy

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import com.kevlar.antipiracy.detection.dataset.DatasetEntry
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector
import com.kevlar.antipiracy.detection.vectors.specter.OutputSpecter
import com.kevlar.antipiracy.detection.vectors.specter.VectorSpecter
import com.kevlar.antipiracy.dsl.builders.*
import com.kevlar.antipiracy.parallel.mapParallel
import kotlinx.coroutines.*
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

public object Attestator {

    @SuppressLint("QueryPermissionsNeeded")
    private fun buildPackageList(context: Context) = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    @OptIn(ExperimentalTime::class)
    public suspend fun attestate(
        armament: AntipiracyArmament,
        context: Context,
        index: Int
    ): AntipiracyAttestation = withContext(Dispatchers.Default) {
        val installedApplicationList: List<ApplicationInfo> = buildPackageList(context)

        val input = InputVector(scanConfiguration = armament.scanConfiguration)
        val vectors = VectorSpecter(input)


        var outputSpecters: List<OutputSpecter> = listOf()
        val time = measureTime {
            outputSpecters = installedApplicationList.map {
                vectors.probeSpace(it)
            }
        }

        Log.d(TAG, "time: $time")

        return@withContext craftAttestation(outputSpecters, index)
    }

    private fun craftAttestation(
        outputSpecters: List<OutputSpecter>,
        index: Int
    ): AntipiracyAttestation {
        val detectedDatasetEntries: Set<DatasetEntry> = outputSpecters
            .asSequence()
            .filter {
                it.matchingVectors.any(OutputVector::isNotEmpty) }
            .map(OutputSpecter::matchingVectors)
            .flatten()
            .mapNotNull(OutputVector::matchingDataset)
            .toSet()

        return when {
            detectedDatasetEntries.isEmpty() -> {
                AntipiracyAttestation.Clear(index)
            }

            else -> {
                AntipiracyAttestation.Failed(index, ScanResult(detectedDatasetEntries))
            }
        }
    }

    private const val TAG = "Attestator"
}