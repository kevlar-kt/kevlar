package com.kevlar.antipiracy.dsl.builders

import android.content.pm.ApplicationInfo
import android.content.pm.PackageItemInfo
import com.kevlar.antipiracy.dsl.AntipiracyDslMarker
import com.kevlar.antipiracy.dsl.DslBuilder

public abstract class Scan {
    public abstract val enabled: Boolean
}



public data class PirateSoftwareScan(
    override val enabled: Boolean
) : Scan()

public data class PirateStoreScan(
    override val enabled: Boolean
): Scan()

public data class CustomScan(
    override val enabled: Boolean
): Scan()




public data class ScanResult(
    val packages: List<ApplicationInfo>
) {
    public companion object {
        public fun empty(): ScanResult = ScanResult(listOf())
    }

    public fun isClear(): Boolean = packages.isEmpty()

    public operator fun plus(other: ScanResult): ScanResult = ScanResult(packages + other.packages)
}



@AntipiracyDslMarker
public class PirateSoftwareScanBuilder() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public fun build(): PirateSoftwareScan = PirateSoftwareScan(enabled)
}


@AntipiracyDslMarker
public class PirateStoreScanBuilder() : DslBuilder<PirateStoreScan>() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public override fun build(): PirateStoreScan = PirateStoreScan(enabled)
}


@AntipiracyDslMarker
public class CustomScanBuilder() : DslBuilder<CustomScan>() {
    private var enabled: Boolean = false

    internal fun enable() {
        enabled = true
    }

    internal fun disable() {
        enabled = false
    }

    public override fun build(): CustomScan = CustomScan(enabled)
}