package com.kevlar.rooting.shell.dump

import com.kevlar.rooting.shell.ExecutionLevel
import com.topjohnwu.superuser.Shell

/**
 * This is a very ugly, rough and approximate representation of a binary's output and context.
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

