@file:Suppress("SpellCheckingInspection")

package com.kevlar.antipiracy.detection.dataset

/**
 * Synthetizes a target software, with its essential metadata.
 * */
public enum class DatasetEntry(
    public val type: DatasetType,
    public val softwareName: String
) {
    URET_PATCHER(DatasetType.PIRATE_APP, "Uret Patcher"),
    CREEPLAYS_PATCHER(DatasetType.PIRATE_APP, "Creeplays Patcher"),
    ACTION_LAUNCHER_PATCHER(DatasetType.PIRATE_APP, "Action Launcher Patcher"),
    CREE_HACK(DatasetType.PIRATE_APP, "Cree Hack"),
    LEO_PLAYCARDS(DatasetType.PIRATE_APP, "Leo Playcards"),
    APP_SARA(DatasetType.PIRATE_APP, "App Sara"),
    XMG(DatasetType.PIRATE_APP, "XModGames"),
    GAME_HACKER(DatasetType.PIRATE_APP, "Game Hacker"),
    GAME_KILLER(DatasetType.PIRATE_APP, "Game Killer Cheats"),
    AGK(DatasetType.PIRATE_APP, "AGK App Killer"),
    CONTENT_GUARD_DISABLER(DatasetType.PIRATE_APP, "Content Guard Disabler"),
    FREEDOM(DatasetType.PIRATE_APP, "Freedom"),
    LUCKY_PATCHER(DatasetType.PIRATE_APP, "Lucky Patcher"),

    APTOIDE(DatasetType.PIRATE_STORE, "Aptoide"),
    HAPPYMOD(DatasetType.PIRATE_STORE, "Happymod"),
    BLACK_MART(DatasetType.PIRATE_STORE, "Black Mart"),
    MOB_GENIE(DatasetType.PIRATE_STORE, "Mobogenie"),
    ONE_MOBILE(DatasetType.PIRATE_STORE, "1Mobile"),
    GET_APK(DatasetType.PIRATE_STORE, "Get Apk"),
    GET_JAR(DatasetType.PIRATE_STORE, "Get Jar"),
    SLIDE_ME(DatasetType.PIRATE_STORE, "Slide Me"),
    AC_MARKET(DatasetType.PIRATE_STORE, "AC Market"),
    APP_CAKE(DatasetType.PIRATE_STORE, "App Cake"),
    Z_MARKET(DatasetType.PIRATE_STORE, "Z Market"),
    MOBILISM(DatasetType.PIRATE_STORE, "Mobilism"),
    AIOD(DatasetType.PIRATE_STORE, "All In One Downloader"),
}