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

package com.kevlar.antipiracy.dataset

/**
 * Synthetizes a targetable software, along with its essential metadata.
 * */
public enum class DatasetEntry(public val type: DatasetType, public val softwareName: String) {
    // Pirate apps
    ACTION_LAUNCHER_PATCHER(DatasetType.PIRATE_APP, "Action Launcher Patcher"),
    AGK(DatasetType.PIRATE_APP, "AGK App Killer"),
    APP_SARA(DatasetType.PIRATE_APP, "App Sara"),
    CGD(DatasetType.PIRATE_APP, "Content Guard Disabler"),
    CREEPLAYS_PATCHER(DatasetType.PIRATE_APP, "Creeplays Patcher"),
    CREE_HACK(DatasetType.PIRATE_APP, "Cree Hack"),
    FREEDOM(DatasetType.PIRATE_APP, "Freedom"),
    HIDE_ROOT(DatasetType.PIRATE_APP, "Hide Root"),
    GAME_HACKER(DatasetType.PIRATE_APP, "Game Hacker"),
    GAME_KILLER(DatasetType.PIRATE_APP, "Game Killer Cheats"),
    LEO_PLAYCARDS(DatasetType.PIRATE_APP, "Leo Playcards"),
    LUCKY_PATCHER(DatasetType.PIRATE_APP, "Lucky Patcher"),
    ROOT_CLOAK(DatasetType.PIRATE_APP, "Root Cloak"),
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

    // Whitelisting / Blacklisting
    BLACKLIST(DatasetType.PIRATE_APP, "Manual Blacklist")
}