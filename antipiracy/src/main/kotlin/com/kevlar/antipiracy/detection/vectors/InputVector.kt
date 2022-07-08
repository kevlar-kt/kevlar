package com.kevlar.antipiracy.detection.vectors

import com.kevlar.antipiracy.dsl.settings.scan.ScanConfiguration

/**
 * Input abstraction for vector operations
 * */
internal data class InputVector(
    val scanConfiguration: ScanConfiguration
)