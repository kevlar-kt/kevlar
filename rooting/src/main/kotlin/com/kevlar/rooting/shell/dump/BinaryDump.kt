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

package com.kevlar.rooting.shell.dump

import com.kevlar.rooting.shell.ExecutionLevel
import com.topjohnwu.superuser.Shell

/**
 * This is a very rough and approximate representation of a binary's output and context.
 *
 * We can collect this data, analyze it and understand if said binary is present in the system of
 * the running device, or not.
 *
 * We go to the extent of logging all this information to have an accurate result.
 * */
internal data class BinaryDump(
    val binaryName: String,
    val executionLevel: ExecutionLevel,

    val userResult: Shell.Result?,
    val invocationResult: Shell.Result?,
    val pathExtractionResult: Shell.Result?
) {

    val exitCode: Int
        get() = invocationResult?.code ?: 0

    val user: String
        get() = userResult?.out?.firstOrNull()?.trim() ?: when (executionLevel) {
            ExecutionLevel.APP -> "app"
            ExecutionLevel.SH -> "shell"
            ExecutionLevel.SU -> "pseudo-root"
        }

    val path: String
        get() = pathExtractionResult?.out?.firstOrNull()?.trim() ?: ""

    val stdout: String
        get() = invocationResult?.out?.joinToString().toString()

    val stderr: String
        get() = invocationResult?.err?.joinToString().toString()

    fun exists() = exitCode != 127 && path.isNotEmpty()

    companion object {
        fun emptyBinaryDump(
            binaryName: String,
            executionLevel: ExecutionLevel,
        ) = BinaryDump(binaryName, executionLevel, null, null, null)
    }
}

