package com.kevlar.rooting.detection.status

import android.os.Build

internal fun detectTestKeys(): Boolean {
    val buildTags = Build.TAGS
    return buildTags != null && buildTags.contains("test-keys")
}