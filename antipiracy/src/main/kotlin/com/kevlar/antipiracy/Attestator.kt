@file:Suppress("NOTHING_TO_INLINE")

package com.kevlar.antipiracy

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.kevlar.antipiracy.dataset.DatasetEntry
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector
import com.kevlar.antipiracy.detection.vectors.specter.OutputSpecter
import com.kevlar.antipiracy.detection.vectors.specter.VectorSpecter
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.antipiracy.dsl.settings.scan.*
import com.kevlar.antipiracy.parallel.mapParallel
import kotlinx.coroutines.*

/**
 * Package queries, scan initializer, vector specter manager & attestation producer
 * */
internal object Attestator {

    /**
     * Method retrieving the installed package list.
     * */
    @SuppressLint("QueryPermissionsNeeded")
    private inline fun queryPackageList(context: Context) =
        context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    /**
     * Produces an [AntipiracyAttestation] for the given [AntipiracySettings]
     * */
    suspend fun attestate(
        settings: AntipiracySettings,
        context: Context,
        index: Int
    ): AntipiracyAttestation = withContext(Dispatchers.Default) {
        val installedApplicationList: List<ApplicationInfo> = queryPackageList(context)

        val input = InputVector(scanConfiguration = settings.scanConfiguration)
        val vectors = VectorSpecter(input)

        val outputSpecters: List<OutputSpecter> = installedApplicationList.mapParallel { appInfo ->
            vectors.probeSpace(appInfo)
        }

        return@withContext craftAttestation(outputSpecters, index)
    }

    /**
     * Converts the given vector output specters to an attestation
     * */
    private fun craftAttestation(
        outputSpecters: List<OutputSpecter>,
        index: Int
    ): AntipiracyAttestation {
        val detectedDatasetEntries: Set<DatasetEntry> = outputSpecters
            .asSequence()
            .filter { it.matchingVectors.any(OutputVector::isNotEmpty) }
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