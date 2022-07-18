package com.kevlar.integrity.checks

import android.content.Context

/**
 * Checks
 *
 * @return whether the given signature matches the running application's
 * */
internal fun matchesHardcodedPackageName(
    hardcodedPackageName: String,
    context: Context
): Boolean = context.packageName == hardcodedPackageName