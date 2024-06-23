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
public enum class Threat(public val type: ThreatType, public val softwareName: String) {
    // Pirate apps
    ACTION_LAUNCHER_PATCHER(ThreatType.PIRATE_APP, "Action Launcher Patcher"),
    AGK(ThreatType.PIRATE_APP, "AGK App Killer"),
    APP_SARA(ThreatType.PIRATE_APP, "App Sara"),
    CGD(ThreatType.PIRATE_APP, "Content Guard Disabler"),
    CREEPLAYS_PATCHER(ThreatType.PIRATE_APP, "Creeplays Patcher"),
    CREE_HACK(ThreatType.PIRATE_APP, "Cree Hack"),
    FREEDOM(ThreatType.PIRATE_APP, "Freedom"),
    HIDE_ROOT(ThreatType.PIRATE_APP, "Hide Root"),
    GAME_HACKER(ThreatType.PIRATE_APP, "Game Hacker"),
    GAME_KILLER(ThreatType.PIRATE_APP, "Game Killer Cheats"),
    LEO_PLAYCARDS(ThreatType.PIRATE_APP, "Leo Playcards"),
    LUCKY_PATCHER(ThreatType.PIRATE_APP, "Lucky Patcher"),
    ROOT_CLOAK(ThreatType.PIRATE_APP, "Root Cloak"),
    URET_PATCHER(ThreatType.PIRATE_APP, "Uret Patcher"),
    XMG(ThreatType.PIRATE_APP, "XModGames"),

    // Pirate stores
    AC_MARKET(ThreatType.PIRATE_STORE, "AC Market"),
    AIOD(ThreatType.PIRATE_STORE, "All In One Downloader"),
    APP_CAKE(ThreatType.PIRATE_STORE, "App Cake"),
    APTOIDE(ThreatType.PIRATE_STORE, "Aptoide"),
    BLACK_MART(ThreatType.PIRATE_STORE, "Black Mart"),
    GET_APK(ThreatType.PIRATE_STORE, "Get Apk"),
    GET_JAR(ThreatType.PIRATE_STORE, "Get Jar"),
    HAPPYMOD(ThreatType.PIRATE_STORE, "Happymod"),
    MOBILISM(ThreatType.PIRATE_STORE, "Mobilism"),
    MOB_GENIE(ThreatType.PIRATE_STORE, "Mobogenie"),
    ONE_MOBILE(ThreatType.PIRATE_STORE, "1Mobile"),
    SLIDE_ME(ThreatType.PIRATE_STORE, "Slide Me"),
    Z_MARKET(ThreatType.PIRATE_STORE, "Z Market"),

    // Whitelisting / Blacklisting
    BLACKLIST(ThreatType.PIRATE_APP, "Manual Blacklist")
}