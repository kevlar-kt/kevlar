package com.kevlar.rooting.shell.dump

internal data class CombinedBinaryDump(
    val applicationDump: BinaryDump,
    val shellDump: BinaryDump,
    val suDump: BinaryDump,
)