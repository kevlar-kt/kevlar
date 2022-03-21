@file:Suppress("SpellCheckingInspection")

package com.kevlar.antipiracy.detection.vector.euristic

import com.kevlar.antipiracy.detection.dataset.DatasetEntry

/**
 * Partitioned
 * */
internal object HeuristicDataset {

    val identifiableHeuristicsApps = arrayOf(
        HeuristicUnit(
            DatasetEntry.URET_PATCHER,
            HeuristicDetectionPolicy.packageName(
                "uret.jasi2169.patcher",
                "zone.jasi2169.uretpatcher",
            )
        ),
        HeuristicUnit(
            DatasetEntry.CREEPLAYS_PATCHER,
            HeuristicDetectionPolicy.packageName(
                "org.creeplays.hack"
            )
        ),
        HeuristicUnit(
            DatasetEntry.CREE_HACK,
            HeuristicDetectionPolicy.packageName(
                "apps.zhasik007.hack",
                "org.creeplays.hack"
            )
        ),
        HeuristicUnit(
            DatasetEntry.LEO_PLAYCARDS,
            HeuristicDetectionPolicy.packageName(
                "com.leo.playcard",
                /* com.hakiansatu.leohhf */
            )
        ),
        HeuristicUnit(
            DatasetEntry.APP_SARA,
            HeuristicDetectionPolicy.packageName(
                "com.appsara.app",
            )
        ),
        HeuristicUnit(
            DatasetEntry.XMG,
            HeuristicDetectionPolicy.packageName(
                "com.xmodgame",
            )
        ),
        HeuristicUnit(
            DatasetEntry.GAME_HACKER,
            HeuristicDetectionPolicy.packageName(
                "org.sbtools.gamehack",
            )
        ),
        HeuristicUnit(
            DatasetEntry.GAME_KILLER,
            HeuristicDetectionPolicy.packageName(
                "com.zune.gamekiller",
                "com.killerapp.gamekiller",
                "cn.lm.sq",
            )
        ),
        HeuristicUnit(
            DatasetEntry.AGK,
            HeuristicDetectionPolicy.packageName(
                "com.aag.killer",
            )
        ),
        HeuristicUnit(
            DatasetEntry.CONTENT_GUARD_DISABLER,
            HeuristicDetectionPolicy.packageName(
                "com.github.oneminusone.disablecontentguard",
                "com.oneminusone.disablecontentguard",
            )
        ),
        HeuristicUnit(
            DatasetEntry.FREEDOM,
            HeuristicDetectionPolicy.packageName(
                "madkite.freedom",
                "jase.freedom",
                "cc.jase.freedom",
                "cc.madkite.freedom",
                "cc.cz.madkite.freedom",
            )
        ),
    )


    val identifiableHeuristicStores = arrayOf(
        HeuristicUnit(
            DatasetEntry.APTOIDE,
            HeuristicDetectionPolicy.packageName(
                "cm.aptoide.pt",
            )
        ),

        HeuristicUnit(
            DatasetEntry.HAPPYMOD,
            HeuristicDetectionPolicy.packageName(
                "com.happymod.apk",
            )
        ),

        HeuristicUnit(
            DatasetEntry.HAPPYMOD,
            HeuristicDetectionPolicy.packageName(

                "org.blackmart.market",
                "com.blackmartalpha",
            )
        ),
        HeuristicUnit(
            DatasetEntry.MOB_GENIE,
            HeuristicDetectionPolicy.packageName(
                "com.mobogenie",
            )
        ),
        HeuristicUnit(
            DatasetEntry.ONE_MOBILE,
            HeuristicDetectionPolicy.packageName(
                "me.onemobile.android",
            )
        ),
        HeuristicUnit(
            DatasetEntry.GET_APK,
            HeuristicDetectionPolicy.packageName(
                "com.repodroid.app",
            )
        ),
        HeuristicUnit(
            DatasetEntry.GET_JAR,
            HeuristicDetectionPolicy.packageName(
                "com.getjar.reward",
            )
        ),

        HeuristicUnit(
            DatasetEntry.SLIDE_ME,
            HeuristicDetectionPolicy.packageName(
                "com.slideme.sam.manager",
            )
        ),
        HeuristicUnit(
            DatasetEntry.AC_MARKET,
            HeuristicDetectionPolicy.packageName(
                "net.appcake",
                "ac.market.store",
            )
        ),
        HeuristicUnit(
            DatasetEntry.APP_CAKE,
            HeuristicDetectionPolicy.packageName(
                "com.appcake",
            )
        ),

        HeuristicUnit(
            DatasetEntry.Z_MARKET,
            HeuristicDetectionPolicy.packageName(
                "com.zmapp",
            )
        ),
        HeuristicUnit(
            DatasetEntry.MOBILISM,
            HeuristicDetectionPolicy.packageName(
                "org.mobilism.android",
            )
        ),
        HeuristicUnit(
            DatasetEntry.AIOD,
            HeuristicDetectionPolicy.packageName(
                "com.allinone.free",
            )
        ),
    )


    val nonIdentifiableHeuristicApps = arrayOf(
        HeuristicUnit(
            DatasetEntry.LUCKY_PATCHER,
            listOf(
                HeuristicDetectionPolicy.PackageNameDetection(
                    listOf(
                        """com.chelpus.lackypatch""",
                        """com.dimonvideo.luckypatcher""",
                        """com.forpda.lp""",
                        """com.android.vendinc""",
                        """com.android.vending.licensing.ILicensingService""",
                        """com.android.vending.billing.InAppBillingService""",
                    )
                ),
                HeuristicDetectionPolicy.PackageNameRegex(
                    """com.android.vending.billing.InAppBillingService.*"""
                ),
            )
        )
    )

}