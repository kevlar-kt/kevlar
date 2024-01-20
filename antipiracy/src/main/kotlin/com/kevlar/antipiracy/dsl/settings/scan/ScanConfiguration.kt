/*
 * Designed and developed by Kevlar Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevlar.antipiracy.dsl.settings.scan

import com.kevlar.antipiracy.dsl.language.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.language.DslBuilder

/**
 * Contains global scan details.
 * */
public class ScanConfiguration(
    public val pirate: PirateSoftwareScan,
    public val stores: PirateStoreScan,
    public val collateral: CollateralScan,
    public val custom: CustomScan,
    public val whitelist: Set<String>,
    public val blacklist: Set<String>,
) {

    public companion object {
        public fun default(): ScanConfiguration = ScanConfiguration(
            pirate = PirateSoftwareScan(enabled = false),
            stores = PirateStoreScan(enabled = false),
            collateral = CollateralScan(enabled = false),
            custom = CustomScan(enabled = false),
            whitelist = setOf(),
            blacklist = setOf()
        )
    }
}


@AntipiracyDslMarker
public class ScanConfigurationBuilder : DslBuilder<ScanConfiguration>() {
    private var pirate = PirateSoftwareScan(enabled = false)
    private var store = PirateStoreScan(enabled = false)
    private var collateral = CollateralScan(enabled = false)
    private var custom = CustomScan(enabled = false)

    private var whitelist: MutableSet<String> = mutableSetOf()
    private var blacklist: MutableSet<String> = mutableSetOf()

    public fun pirate(block: PirateSoftwareScanBuilder.() -> Unit = {}) {
        pirate = PirateSoftwareScanBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun store(block: PirateStoreScanBuilder.() -> Unit = {}) {
        store = PirateStoreScanBuilder().apply {
            block()
            enable()
        }.build()
    }

    public fun collateral(block: CollateralScanBuilder.() -> Unit = {}) {
        collateral = CollateralScanBuilder().apply {
            block()
            enable()
        }.build()
    }

    // not implemented yet
    private fun custom(block: CustomScanBuilder.() -> Unit) {
        custom = CustomScanBuilder().apply {
            block()
            enable()
        }.build()
    }


    @Synchronized
    public fun whitelist(packageName: String) {
        assert(!blacklist.contains(packageName)) { "Can't add $packageName to whitelist=$whitelist: it is already contained in blacklist=$blacklist" }
        whitelist.add(packageName)
    }

    @Synchronized
    public fun whitelist(packageNameList: List<String>) {
        packageNameList.forEach(::whitelist)
    }



    @Synchronized
    public fun blacklist(packageName: String) {
        assert(!whitelist.contains(packageName)) { "Can't add $packageName to blacklist=$blacklist: it is already contained in whitelist=$whitelist" }
        blacklist.add(packageName)
    }

    @Synchronized
    public fun blacklist(packageNameList: List<String>) {
        packageNameList.forEach(::blacklist)
    }


    /**
     * Builds the scan configuration
     * */
    override fun build(): ScanConfiguration = ScanConfiguration(pirate, store, collateral, custom, whitelist, blacklist)
}
