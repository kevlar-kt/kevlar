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

package com.kevlar.rooting.detection.status

import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal enum class SelinuxGetenforceStatus {
    DISABLED, PERMISSIVE, ENFORCING;
}

private const val SX_DISABLED = """disabled"""
private const val SX_PERMISSIVE = """permissive"""

/**
 * This command may fail under different circumstances.
 * We want to have no false positives. Thus, in case of unknown output
 * we assume that the status is set to enforcing.
 * Only if we find evidence of selinux being either disabled or permissive
 * we can infer some information.
 * */
internal suspend fun detectSelinux(): SelinuxGetenforceStatus = withContext(Dispatchers.IO) {
    when (Shell.cmd("getenforce").exec().out.joinToString().lowercase().trim()) {
        SX_DISABLED -> SelinuxGetenforceStatus.DISABLED
        SX_PERMISSIVE -> SelinuxGetenforceStatus.PERMISSIVE
        else -> SelinuxGetenforceStatus.ENFORCING
    }
}