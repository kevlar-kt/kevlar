@file:Suppress("SpellCheckingInspection")

package com.kevlar.antipiracy.detection.vectors.heuristic

import com.kevlar.antipiracy.detection.dataset.DetectionPolicy
import com.kevlar.antipiracy.detection.dataset.DatasetUnit
import com.kevlar.antipiracy.detection.dataset.DatasetEntry

/**
 * Partitioned
 * */
internal object HeuristicDataset {

    val identifiableHeuristicsPirateApps = arrayOf(
        DatasetUnit(
            DatasetEntry.URET_PATCHER,
            DetectionPolicy.packageName(
                "uret.jasi2169.patcher",
                "zone.jasi2169.uretpatcher",
            )
        ),
        DatasetUnit(
            DatasetEntry.CREEPLAYS_PATCHER,
            DetectionPolicy.packageName(
                "org.creeplays.hack"
            )
        ),
        DatasetUnit(
            DatasetEntry.CREE_HACK,
            DetectionPolicy.packageName(
                "apps.zhasik007.hack",
                "org.creeplays.hack"
            )
        ),
        DatasetUnit(
            DatasetEntry.LEO_PLAYCARDS,
            DetectionPolicy.packageName(
                "com.leo.playcard",
                /* com.hakiansatu.leohhf */
            )
        ),
        DatasetUnit(
            DatasetEntry.APP_SARA,
            DetectionPolicy.packageName(
                "com.appsara.app",
            )
        ),
        DatasetUnit(
            DatasetEntry.XMG,
            DetectionPolicy.packageName(
                "com.xmodgame",
            )
        ),
        DatasetUnit(
            DatasetEntry.GAME_HACKER,
            DetectionPolicy.packageName(
                "org.sbtools.gamehack",
            )
        ),
        DatasetUnit(
            DatasetEntry.GAME_KILLER,
            DetectionPolicy.packageName(
                "com.zune.gamekiller",
                "com.killerapp.gamekiller",
                "cn.lm.sq",
            )
        ),
        DatasetUnit(
            DatasetEntry.AGK,
            DetectionPolicy.packageName(
                "com.aag.killer",
            )
        ),
        DatasetUnit(
            DatasetEntry.CONTENT_GUARD_DISABLER,
            DetectionPolicy.packageName(
                "com.github.oneminusone.disablecontentguard",
                "com.oneminusone.disablecontentguard",
            )
        ),
        DatasetUnit(
            DatasetEntry.FREEDOM,
            DetectionPolicy.packageName(
                "madkite.freedom",
                "jase.freedom",
                "cc.jase.freedom",
                "cc.madkite.freedom",
                "cc.cz.madkite.freedom",
            )
        ),
    )


    val identifiableHeuristicStores = arrayOf(
        DatasetUnit(
            DatasetEntry.APTOIDE,
            DetectionPolicy.packageName(
                "cm.aptoide.pt",
            )
        ),

        DatasetUnit(
            DatasetEntry.HAPPYMOD,
            DetectionPolicy.packageName(
                "com.happymod.apk",
            )
        ),

        DatasetUnit(
            DatasetEntry.HAPPYMOD,
            DetectionPolicy.packageName(

                "org.blackmart.market",
                "com.blackmartalpha",
            )
        ),
        DatasetUnit(
            DatasetEntry.MOB_GENIE,
            DetectionPolicy.packageName(
                "com.mobogenie",
            )
        ),
        DatasetUnit(
            DatasetEntry.ONE_MOBILE,
            DetectionPolicy.packageName(
                "me.onemobile.android",
            )
        ),
        DatasetUnit(
            DatasetEntry.GET_APK,
            DetectionPolicy.packageName(
                "com.repodroid.app",
            )
        ),
        DatasetUnit(
            DatasetEntry.GET_JAR,
            DetectionPolicy.packageName(
                "com.getjar.reward",
            )
        ),

        DatasetUnit(
            DatasetEntry.SLIDE_ME,
            DetectionPolicy.packageName(
                "com.slideme.sam.manager",
            )
        ),
        DatasetUnit(
            DatasetEntry.AC_MARKET,
            DetectionPolicy.packageName(
                "net.appcake",
                "ac.market.store",
            )
        ),
        DatasetUnit(
            DatasetEntry.APP_CAKE,
            DetectionPolicy.packageName(
                "com.appcake",
            )
        ),

        DatasetUnit(
            DatasetEntry.Z_MARKET,
            DetectionPolicy.packageName(
                "com.zmapp",
            )
        ),
        DatasetUnit(
            DatasetEntry.MOBILISM,
            DetectionPolicy.packageName(
                "org.mobilism.android",
            )
        ),
        DatasetUnit(
            DatasetEntry.AIOD,
            DetectionPolicy.packageName(
                "com.allinone.free",
            )
        ),
    )


    val nonIdentifiableHeuristicApps = arrayOf(
        DatasetUnit(
            DatasetEntry.LUCKY_PATCHER,
            listOf(
                DetectionPolicy.PackageNameDetection(
                    listOf(
                        """com.chelpus.lackypatch""",
                        """com.dimonvideo.luckypatcher""",
                        """com.forpda.lp""",
                        """com.android.vendinc""",
                        """com.android.vending.licensing.ILicensingService""",
                        """com.android.vending.billing.InAppBillingService""",
                    )
                ),
                DetectionPolicy.PackageNameRegex(
                    """com.android.vending.billing.InAppBillingService.*"""
                ),
            )
        )
    )

}