package com.kevlar.integrity.checks

import android.content.Context
import android.os.Build

/**
 * Checks
 *
 * @return whether the given signature matches the running application's
 * */
internal fun matchesAllowedInstallerPackageNames(
    allowedInstallerPackageNames: List<String>,
    context: Context
): Boolean {
    // Should be implemented with context.packageManager.getInstallSourceInfo(context.packageName)
    val installerPackageName: String? = context.packageManager.getInstallerPackageName(context.packageName)
    return installerPackageName != null && allowedInstallerPackageNames.any { installerPackageName.startsWith(it) }
}