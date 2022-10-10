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

@file:Suppress("SpellCheckingInspection")

package com.kevlar.antipiracy.detection.vectors.heuristic

import com.kevlar.antipiracy.dataset.DatasetEntry

/**
 * Dataset for heuristic piracy detection.
 * The complete dataset is partitioned in different lists, each one
 * containing a specific type of pirate software.
 * */
internal object HeuristicDataset {

    /**
     * Packages which are straightforward to pick up
     * since they don't actively try to disguise themselves
     * */
    val identifiableHeuristicsPirateApps: Array<MatchableHeuristicDatasetEntry> = arrayOf(
        MatchableHeuristicDatasetEntry(
            DatasetEntry.URET_PATCHER,
            DetectionPolicy.packageNames(
                "uret.jasi2169.patcher",
                "zone.jasi2169.uretpatcher",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.CREEPLAYS_PATCHER,
            DetectionPolicy.packageNames(
                "org.creeplays.hack"
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.CREE_HACK,
            DetectionPolicy.packageNames(
                "apps.zhasik007.hack",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.LEO_PLAYCARDS,
            DetectionPolicy.packageNames(
                "com.leo.playcard",
                /* com.hakiansatu.leohhf */
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.APP_SARA,
            DetectionPolicy.packageNames(
                "com.appsara.app",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.XMG,
            DetectionPolicy.packageNames(
                "com.xmodgame",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.GAME_HACKER,
            DetectionPolicy.packageNames(
                "org.sbtools.gamehack",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.GAME_KILLER,
            DetectionPolicy.packageNames(
                "com.zune.gamekiller",
                "com.killerapp.gamekiller",
                "cn.lm.sq",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.AGK,
            DetectionPolicy.packageNames(
                "com.aag.killer",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.CGD,
            DetectionPolicy.packageNames(
                "com.github.oneminusone.disablecontentguard",
                "com.oneminusone.disablecontentguard",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.FREEDOM,
            DetectionPolicy.packageNames(
                "madkite.freedom",
                "jase.freedom",
                "cc.jase.freedom",
                "cc.madkite.freedom",
                "cc.cz.madkite.freedom",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.ROOT_CLOAK,
            DetectionPolicy.packageNames(
                "com.devadvance.rootcloak",
                "com.devadvance.rootcloakplus"
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.HIDE_ROOT,
            DetectionPolicy.packageNames(
                "com.formyhm.hideroot",
                "com.formyhm.hiderootpremium",
                "com.amphoras.hidemyroot",
                "com.amphoras.hidemyrootadfree",
                "com.zachspong.temprootremovejb",
            )
        ),
    )


    /**
     * Basically no store has active disguising methods
     * */
    val identifiableHeuristicStores: Array<MatchableHeuristicDatasetEntry> = arrayOf(
        MatchableHeuristicDatasetEntry(
            DatasetEntry.APTOIDE,
            DetectionPolicy.packageNames(
                "cm.aptoide.pt",
            )
        ),

        MatchableHeuristicDatasetEntry(
            DatasetEntry.HAPPYMOD,
            DetectionPolicy.packageNames(
                "com.happymod.apk",
                "happygames.io",
            )
        ),

        MatchableHeuristicDatasetEntry(
            DatasetEntry.BLACK_MART,
            DetectionPolicy.packageNames(
                "org.blackmart.market",
                "com.blackmartalpha",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.MOB_GENIE,
            DetectionPolicy.packageNames(
                "com.mobogenie",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.ONE_MOBILE,
            DetectionPolicy.packageNames(
                "me.onemobile.android",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.GET_APK,
            DetectionPolicy.packageNames(
                "com.repodroid.app",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.GET_JAR,
            DetectionPolicy.packageNames(
                "com.getjar.reward",
            )
        ),

        MatchableHeuristicDatasetEntry(
            DatasetEntry.SLIDE_ME,
            DetectionPolicy.packageNames(
                "com.slideme.sam.manager",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.AC_MARKET,
            DetectionPolicy.packageNames(
                "ac.market.store",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.APP_CAKE,
            DetectionPolicy.packageNames(
                "net.appcake",
                "com.appcake",
            )
        ),

        MatchableHeuristicDatasetEntry(
            DatasetEntry.Z_MARKET,
            DetectionPolicy.packageNames(
                "com.zmapp",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.MOBILISM,
            DetectionPolicy.packageNames(
                "org.mobilism.android",
            )
        ),
        MatchableHeuristicDatasetEntry(
            DatasetEntry.AIOD,
            DetectionPolicy.packageNames(
                "com.allinone.free",
            )
        ),
    )


    /**
     * [MatchableHeuristicDatasetEntry]s which are harder to detect with heuristic methods, but
     * still do a good job at catching old versions / tertiary components
     * associated with one [DatasetEntry]
     * */
    val nonIdentifiableHeuristicPirateApps: Array<MatchableHeuristicDatasetEntry> = arrayOf(
        MatchableHeuristicDatasetEntry(
            DatasetEntry.LUCKY_PATCHER,
            listOf(
                DetectionPolicy.PackageNameDetection(
                    listOf(
                        // Lucky patcher app
                        """com.chelpus.lackypatch""",
                        """com.dimonvideo.luckypatcher""",
                        """com.forpda.lp""",
                        """com.android.vendinc""",

                        // Lucky patcher proxy server for license verification
                        """com.android.vending.licensing.ILicensingService""",
                    )
                ),
                DetectionPolicy.PackageNameRegex(
                    // Lucky patcher proxy server for inppp purchases
                    """com.android.vending.billing.InAppBillingService.*"""
                ),
                DetectionPolicy.PackageNameRegex(
                    // Lukcy Patcher Installer
                    """ru\..?.?[a]{3,}.?.?.?.?.?\.installer"""
                ),
                DetectionPolicy.ClassNameNameRegex(
                    // Lucky patcher app
                    """com.lp"""
                )
            )
        )
    )


    val collateralPirateApps: Array<MatchableHeuristicDatasetEntry> = arrayOf(
        MatchableHeuristicDatasetEntry(
            DatasetEntry.LUCKY_PATCHER,
            listOf(
                DetectionPolicy.PackageNameRegex(
                    // ru.********.*********
                    """ru\.........\.........."""
                )
            )
        ),
    )
}