package com.kevlar.antipiracy

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageItemInfo
import android.content.pm.PackageManager
import com.kevlar.antipiracy.detection.alphabet.AlphabetTargetDefinitions
import com.kevlar.antipiracy.detection.alphabet.StringReducer
import com.kevlar.antipiracy.dsl.builders.*
import kotlinx.coroutines.*

public object Attestator {

    public suspend fun attestate(
        armament: AntipiracyArmament,
        context: Context,
        index: Int
    ): AntipiracyAttestation = withContext(Dispatchers.Default) {
        val packages: MutableList<ApplicationInfo> = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        val sc = armament.scanConfiguration

        val pirateScan: Deferred<ScanResult> = async {
            if (sc.pirate.enabled) {
                scanPirate(sc.pirate, packages)
            } else {
                ScanResult.empty()
            }
        }

        val storesScan: Deferred<ScanResult> = async {
            if (sc.stores.enabled) {
                scanStores(sc.stores, packages)
            } else {
                ScanResult.empty()
            }
        }

        val customScan: Deferred<ScanResult> = async {
            if (sc.custom.enabled) {
                scanCustom(sc.custom, packages)
            } else {
                ScanResult.empty()
            }
        }

        awaitAll(pirateScan, storesScan, customScan)

        val results = pirateScan.await() + storesScan.await() + customScan.await()

        return@withContext when {
            results.isClear() -> AntipiracyAttestation.Clear(index)
            else -> AntipiracyAttestation.Failed(index, results)
        }
    }

    private suspend fun scanPirate(
        scan: PirateSoftwareScan,
        installedApps: List<ApplicationInfo>
    ): ScanResult = withContext(Dispatchers.Default) {
        val detectedPackages = mutableListOf<ApplicationInfo>()

        for (installedApp in installedApps) {
            val label = installedApp.nonLocalizedLabel

            if (label.isNullOrBlank()) continue

            when (StringReducer.reduceString(label)) {
                AlphabetTargetDefinitions.luckyPatcherUnitString -> {
                    detectedPackages.add(installedApp)
                }
            }
        }

        return@withContext if (detectedPackages.isEmpty()) {
            ScanResult.empty()
        } else {
            ScanResult(detectedPackages)
        }
    }


    private suspend fun scanStores(
        scan: PirateStoreScan,
        installedApps: List<ApplicationInfo>
    ): ScanResult = withContext(Dispatchers.Default) {
        return@withContext ScanResult.empty()
    }


    private suspend fun scanCustom(
        scan: CustomScan,
        installedApps: List<ApplicationInfo>
    ): ScanResult = withContext(Dispatchers.Default) {
        return@withContext ScanResult.empty()
    }
}