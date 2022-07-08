package com.kevlar.rooting.detection.status

import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal enum class SelinuxGetenforceStatus {
    DISABLED, PERMISSIVE, ENFORCING;
}

internal suspend fun detectSelinux(): SelinuxGetenforceStatus = withContext(Dispatchers.IO) {
    when (Shell.cmd("getenforce").exec().out.joinToString().lowercase()) {
        "disabled" -> SelinuxGetenforceStatus.DISABLED
        "permissive" -> SelinuxGetenforceStatus.PERMISSIVE
        else -> SelinuxGetenforceStatus.ENFORCING
    }
}