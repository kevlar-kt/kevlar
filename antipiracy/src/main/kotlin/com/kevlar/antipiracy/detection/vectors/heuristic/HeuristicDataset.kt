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

import com.kevlar.antipiracy.dataset.Threat

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
    val identifiableHeuristicsPirateApps: Array<HeuristicThreatDetectionSuite> = arrayOf(
        HeuristicThreatDetectionSuite(
            Threat.URET_PATCHER,
            DetectionStrategy.packageNames(
                """uret.jasi2169.patcher""",
                """zone.jasi2169.uretpatcher""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.CREEPLAYS_PATCHER,
            DetectionStrategy.packageNames(
                """org.creeplays.hack"""
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.CREE_HACK,
            DetectionStrategy.packageNames(
                """apps.zhasik007.hack""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.LEO_PLAYCARDS,
            DetectionStrategy.packageNames(
                """com.leo.playcard""",
                /* com.hakiansatu.leohhf */
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.APP_SARA,
            DetectionStrategy.packageNames(
                """com.appsara.app""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.XMG,
            DetectionStrategy.packageNames(
                """com.xmodgame""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.GAME_HACKER,
            DetectionStrategy.packageNames(
                """org.sbtools.gamehack""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.GAME_KILLER,
            DetectionStrategy.packageNames(
                """com.zune.gamekiller""",
                """com.killerapp.gamekiller""",
                """cn.lm.sq""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.AGK,
            DetectionStrategy.packageNames(
                """com.aag.killer""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.CGD,
            DetectionStrategy.packageNames(
                """com.github.oneminusone.disablecontentguard""",
                """com.oneminusone.disablecontentguard""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.FREEDOM,
            DetectionStrategy.packageNames(
                """madkite.freedom""",
                """jase.freedom""",
                """cc.jase.freedom""",
                """cc.madkite.freedom""",
                """cc.cz.madkite.freedom""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.ROOT_CLOAK,
            DetectionStrategy.packageNames(
                """com.devadvance.rootcloak""",
                """com.devadvance.rootcloakplus"""
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.HIDE_ROOT,
            DetectionStrategy.packageNames(
                """com.formyhm.hideroot""",
                """com.formyhm.hiderootpremium""",
                """com.amphoras.hidemyroot""",
                """com.amphoras.hidemyrootadfree""",
                """com.zachspong.temprootremovejb""",
            )
        ),
    )


    /**
     * Basically no store has active disguising methods
     * */
    val identifiableHeuristicStores: Array<HeuristicThreatDetectionSuite> = arrayOf(
        HeuristicThreatDetectionSuite(
            Threat.APTOIDE,
            DetectionStrategy.packageNames(
                """cm.aptoide.pt""",
            )
        ),

        HeuristicThreatDetectionSuite(
            Threat.HAPPYMOD,
            DetectionStrategy.packageNames(
                """com.happymod.apk""",
                """happygames.io""",
            )
        ),

        HeuristicThreatDetectionSuite(
            Threat.BLACK_MART,
            DetectionStrategy.packageNames(
                """org.blackmart.market""",
                """com.blackmartalpha""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.MOB_GENIE,
            DetectionStrategy.packageNames(
                """com.mobogenie""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.ONE_MOBILE,
            DetectionStrategy.packageNames(
                """me.onemobile.android""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.GET_APK,
            DetectionStrategy.packageNames(
                """com.repodroid.app""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.GET_JAR,
            DetectionStrategy.packageNames(
                """com.getjar.reward""",
            )
        ),

        HeuristicThreatDetectionSuite(
            Threat.SLIDE_ME,
            DetectionStrategy.packageNames(
                """com.slideme.sam.manager""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.AC_MARKET,
            DetectionStrategy.packageNames(
                """ac.market.store""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.APP_CAKE,
            DetectionStrategy.packageNames(
                """net.appcake""",
                """com.appcake""",
            )
        ),

        HeuristicThreatDetectionSuite(
            Threat.Z_MARKET,
            DetectionStrategy.packageNames(
                """com.zmapp""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.MOBILISM,
            DetectionStrategy.packageNames(
                """org.mobilism.android""",
            )
        ),
        HeuristicThreatDetectionSuite(
            Threat.AIOD,
            DetectionStrategy.packageNames(
                """com.allinone.free""",
            )
        ),
    )


    /**
     * [HeuristicThreatDetectionSuite]s which are harder to detect with heuristic methods, but
     * still do a good job at catching old versions / tertiary components
     * associated with one [Threat]
     * */
    val nonIdentifiableHeuristicPirateApps: Array<HeuristicThreatDetectionSuite> = arrayOf(
        HeuristicThreatDetectionSuite(
            Threat.LUCKY_PATCHER,
            listOf(
                DetectionStrategy.PackageNameDetection(
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
                DetectionStrategy.PackageNameRegex(
                    // Lucky patcher proxy server for inppp purchases
                    """com.android.vending.billing.InAppBillingService.*"""
                ),
                DetectionStrategy.PackageNameRegex(
                    // Lukcy Patcher Installer
                    """ru\..?.?[a]{3,}.?.?.?.?.?\.installer"""
                ),
                DetectionStrategy.ClassNameNameRegex(
                    // Lucky patcher app
                    """com.lp"""
                )
            )
        )
    )


    val collateralPirateApps: Array<HeuristicThreatDetectionSuite> = arrayOf(
        HeuristicThreatDetectionSuite(
            Threat.LUCKY_PATCHER,
            listOf(
                DetectionStrategy.PackageNameRegex(
                    // ru.********.*********
                    """ru\.........\.........."""
                )
            )
        ),
    )
}