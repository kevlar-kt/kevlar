package com.kevlar.antipiracy

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.kevlar.antipiracy.detection.vectors.InputVector
import com.kevlar.antipiracy.detection.vectors.OutputVector
import com.kevlar.antipiracy.detection.vectors.specter.OutputSpecter
import com.kevlar.antipiracy.detection.vectors.specter.VectorSpecter
import com.kevlar.antipiracy.dsl.builders.*
import com.kevlar.antipiracy.parallel.mapParallel
import kotlinx.coroutines.*

public object Attestator {

    @SuppressLint("QueryPermissionsNeeded")
    private fun buildPackageList(context: Context) = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    public suspend fun attestate(
        armament: AntipiracyArmament,
        context: Context,
        index: Int
    ): AntipiracyAttestation = withContext(Dispatchers.Default) {
        val installedApplicationList: List<ApplicationInfo> = buildPackageList(context)

        val input = InputVector(scanConfiguration = armament.scanConfiguration)
        val vectors = VectorSpecter(input)

        val outputSpecter: List<OutputSpecter> = installedApplicationList.mapParallel {
            vectors.probeSpace(it)
        }

        return@withContext craftAttestation(outputSpecter, index)
    }

    private fun craftAttestation(
        outputSpecter: List<OutputSpecter>,
        index: Int
    ): AntipiracyAttestation {
        TODO()
    }
}