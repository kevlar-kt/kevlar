package com.kevlar.integrity.checks

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

/**
 * Checks
 *
 * @return whether the given signature matches the running application's
 * */
internal fun matchesAllowedInstallerPackageNames(
    allowedInstallerPackageNames: List<String>,
    context: Context
) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    matchesAllowedInstallerPackageNamesAboveOrEqualR(allowedInstallerPackageNames, context)
} else {
    matchesAllowedInstallerPackageNamesBelowR(allowedInstallerPackageNames, context)
}


/**
 * If the package manager isn't able to figure out the installer, we treat the check
 * as passed, because we have no information.
 * */
private const val defaultReturnBehaviourOnUnavailablePackage = true

private fun matchesAllowedInstallerPackageNamesBelowR(
    allowedInstallerPackageNames: List<String>,
    context: Context
): Boolean {
    val installerPackageName: String? =
        context.packageManager.getInstallerPackageName(context.packageName)

    return if (installerPackageName == null) {
        defaultReturnBehaviourOnUnavailablePackage
    } else {
        allowedInstallerPackageNames.any {
            installerPackageName.startsWith(
                it
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.R)
private fun matchesAllowedInstallerPackageNamesAboveOrEqualR(
    allowedInstallerPackageNames: List<String>,
    context: Context
): Boolean {
    val installerPackageName = context.packageManager.getInstallSourceInfo(context.packageName).installingPackageName

    return if (installerPackageName == null) {
        // we fall back to the pre-R check
        matchesAllowedInstallerPackageNamesBelowR(allowedInstallerPackageNames, context)
    } else {
        allowedInstallerPackageNames.any {
            installerPackageName.startsWith(
                it
            )
        }
    }
}