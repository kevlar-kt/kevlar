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

package com.kevlar.integrity.checks

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

/**
 * Checks whether the current running software's installer's package name matches the hardcoded one.
 *
 * @return whether the two data match.
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