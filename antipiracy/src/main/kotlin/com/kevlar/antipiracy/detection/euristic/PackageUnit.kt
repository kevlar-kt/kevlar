package com.kevlar.antipiracy.detection.euristic

/**
 * Synthesizes and uniquely identifies an application.
 * */
public data class PackageUnit(
    val appLabel: String,
    val packageNames: List<String>
)

public data class AntipiracyUnit(
    val packageUnit: PackageUnit,
    val flag: AntipiracyFlags
)

public enum class AntipiracyFlags {
    PIRATE_SOFTWARE, PIRATE_STORE, CUSTOM_DETECTION
}
