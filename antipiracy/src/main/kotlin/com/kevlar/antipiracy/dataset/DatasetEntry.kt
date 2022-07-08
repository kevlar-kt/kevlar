@file:Suppress("SpellCheckingInspection")

package com.kevlar.antipiracy.dataset

/**
 * Synthetizes a target software, with its essential metadata.
 * */
public enum class DatasetEntry(
    public val type: DatasetType,
    public val softwareName: String
) {
    // Pirate software
    ACTION_LAUNCHER_PATCHER(DatasetType.PIRATE_APP, "Action Launcher Patcher"),
    AGK(DatasetType.PIRATE_APP, "AGK App Killer"),
    APP_SARA(DatasetType.PIRATE_APP, "App Sara"),
    CGD(DatasetType.PIRATE_APP, "Content Guard Disabler"),
    CREEPLAYS_PATCHER(DatasetType.PIRATE_APP, "Creeplays Patcher"),
    CREE_HACK(DatasetType.PIRATE_APP, "Cree Hack"),
    FREEDOM(DatasetType.PIRATE_APP, "Freedom"),
    GAME_HACKER(DatasetType.PIRATE_APP, "Game Hacker"),
    GAME_KILLER(DatasetType.PIRATE_APP, "Game Killer Cheats"),
    LEO_PLAYCARDS(DatasetType.PIRATE_APP, "Leo Playcards"),
    LUCKY_PATCHER(DatasetType.PIRATE_APP, "Lucky Patcher"),
    URET_PATCHER(DatasetType.PIRATE_APP, "Uret Patcher"),
    XMG(DatasetType.PIRATE_APP, "XModGames"),

    // Pirate stores
    AC_MARKET(DatasetType.PIRATE_STORE, "AC Market"),
    AIOD(DatasetType.PIRATE_STORE, "All In One Downloader"),
    APP_CAKE(DatasetType.PIRATE_STORE, "App Cake"),
    APTOIDE(DatasetType.PIRATE_STORE, "Aptoide"),
    BLACK_MART(DatasetType.PIRATE_STORE, "Black Mart"),
    GET_APK(DatasetType.PIRATE_STORE, "Get Apk"),
    GET_JAR(DatasetType.PIRATE_STORE, "Get Jar"),
    HAPPYMOD(DatasetType.PIRATE_STORE, "Happymod"),
    MOBILISM(DatasetType.PIRATE_STORE, "Mobilism"),
    MOB_GENIE(DatasetType.PIRATE_STORE, "Mobogenie"),
    ONE_MOBILE(DatasetType.PIRATE_STORE, "1Mobile"),
    SLIDE_ME(DatasetType.PIRATE_STORE, "Slide Me"),
    Z_MARKET(DatasetType.PIRATE_STORE, "Z Market"),
}