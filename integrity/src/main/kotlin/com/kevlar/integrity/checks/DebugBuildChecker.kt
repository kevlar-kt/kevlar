package com.kevlar.integrity.checks

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Base64
import java.security.MessageDigest

/**
 * Checks
 *
 * @return whether the given signature matches the running application's
 * */
internal fun isDebugBuild(
    context: Context
): Boolean = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0