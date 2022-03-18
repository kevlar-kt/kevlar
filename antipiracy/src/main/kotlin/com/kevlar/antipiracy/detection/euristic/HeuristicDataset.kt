@file:Suppress("SpellCheckingInspection")

package com.kevlar.antipiracy.detection.euristic

public object HeuristicDataset {

    /**
     * Contains heuristic entries for well-known and not disguised software.
     * */
    public object Identifiable {
        public val uret_patcher: AntipiracyUnit = AntipiracyUnit(
            PackageUnit(
                "Uret Patcher",
                listOf(
                    "uret.jasi2169.patcher",
                    "zone.jasi2169.uretpatcher",
                )
            ),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val creeplays_patcher: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Creeplays Patcher", listOf("org.creeplays.hack")),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val action_launcher_patcher: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("ActionLauncherPatcher", listOf("p.,jasi2169.al3")),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val creehack: AntipiracyUnit = AntipiracyUnit(
            PackageUnit(
                "Cree Hack",
                listOf(
                    "apps.zhasik007.hack",
                    "org.creeplays.hack"
                )
            ),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val leo_playcards: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Leo Playcards", listOf("com.leo.playcard")),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val appsara: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("AppSara", listOf("com.appsara.app")),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val xmod: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("XModGames", listOf("com.xmodgame")),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val game_hacker: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Game Hacker", listOf("org.sbtools.gamehack")),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val game_killer: AntipiracyUnit = AntipiracyUnit(
            PackageUnit(
                "Game Killer Cheats",
                listOf(
                    "com.zune.gamekiller",
                    "com.killerapp.gamekiller",
                    "cn.lm.sq",
                )
            ),
            AntipiracyFlags.PIRATE_SOFTWARE
        )

        public val agk: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("AGK App Killer", listOf("com.aag.killer")),
            AntipiracyFlags.PIRATE_SOFTWARE
        )


        public val content_guard_disabler: AntipiracyUnit = AntipiracyUnit(
            PackageUnit(
                "Content Guard Disabler",
                listOf(
                    "com.github.oneminusone.disablecontentguard",
                    "com.oneminusone.disablecontentguard",
                )
            ),
            AntipiracyFlags.PIRATE_SOFTWARE
        )


        public val freedom: AntipiracyUnit = AntipiracyUnit(
            PackageUnit(
                "Freedom",
                listOf(
                    "madkite.freedom",
                    "jase.freedom",
                    "cc.jase.freedom",
                    "cc.madkite.freedom",
                    "cc.cz.madkite.freedom",
                )
            ),
            AntipiracyFlags.PIRATE_SOFTWARE
        )


        public val aptoide: AntipiracyUnit = AntipiracyUnit(
            PackageUnit(
                "Aptoide",
                listOf(
                    "cm.aptoide.pt",
                )
            ),
            AntipiracyFlags.PIRATE_STORE
        )


        public val happy_mod: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Happy Mod", listOf("com.happymod.apk")),
            AntipiracyFlags.PIRATE_STORE
        )


        public val black_mart: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Black Mart",
                listOf(
                    "org.blackmart.market",
                    "com.blackmartalpha",
                )
            ),
            AntipiracyFlags.PIRATE_STORE
        )


        public val mobogenie: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Mobogenie", listOf("com.mobogenie")),
            AntipiracyFlags.PIRATE_STORE
        )

        public val one_mobile: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("1Mobile", listOf("me.onemobile.android")),
            AntipiracyFlags.PIRATE_STORE
        )

        public val getapk: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Get Apk", listOf("com.repodroid.app")),
            AntipiracyFlags.PIRATE_STORE
        )

        public val getjar: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Get Jar", listOf("com.getjar.reward")),
            AntipiracyFlags.PIRATE_STORE
        )

        public val slideme: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Slide Me", listOf("com.slideme.sam.manager")),
            AntipiracyFlags.PIRATE_STORE
        )

        public val as_market: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("ACMartke",
                listOf(
                    "net.appcake",
                    "ac.market.store",
                )
            ),
            AntipiracyFlags.PIRATE_STORE
        )

        public val app_cake: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("App Cake",
                listOf(
                    "com.appcake",
                )
            ),
            AntipiracyFlags.PIRATE_STORE
        )


        public val z_market: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Z Market",
                listOf(
                    "com.zmapp",
                )
            ),
            AntipiracyFlags.PIRATE_STORE
        )

        public val mobilism: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("Mobilism Market",
                listOf(
                    "org.mobilism.android",
                )
            ),
            AntipiracyFlags.PIRATE_STORE
        )


        public val aiod: AntipiracyUnit = AntipiracyUnit(
            PackageUnit("All In One Downloader",
                listOf(
                    "com.allinone.free",
                )
            ),
            AntipiracyFlags.PIRATE_STORE
        )



        public val allUnits: Array<AntipiracyUnit> =
            arrayOf(creeplays_patcher, leo_playcards, appsara, xmod)
        public val allMutliUnits: Array<AntipiracyUnit> = arrayOf(freedom)
    }


    /**
     * Contains heuristic entries for self-hiding and disguised software.
     * */
    public object NotIdentifiable {
        public val lucky_patcher: Array<AntipiracyUnit> = arrayOf(
            AntipiracyUnit(
                PackageUnit(
                    "Lucky Patcher",
                    listOf(
                        """com.chelpus.lackypatch""",
                        """com.dimonvideo.luckypatcher""",
                        """com.forpda.lp""",
                        """com.android.vendinc""",
                        """com.android.vending.licensing.ILicensingService""",
                        """com.android.vending.billing.InAppBillingService""",
                        """com.android.vending.billing.InAppBillingSorvice""",
                    )
                ),
                AntipiracyFlags.PIRATE_SOFTWARE
            )
        )
    }

}