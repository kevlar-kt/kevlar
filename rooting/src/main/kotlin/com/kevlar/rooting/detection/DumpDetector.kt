package com.kevlar.rooting.detection

import com.kevlar.rooting.shell.dump.BinaryDump
import com.kevlar.rooting.shell.dump.CombinedBinaryDump

internal object DumpDetector {
    fun detect(combinedDump: CombinedBinaryDump): Boolean {
        if (processSingleDump(combinedDump.applicationDump)) {
            return true
        }

        if (processSingleDump(combinedDump.shellDump)) {
            return true
        }

        if (processSingleDump(combinedDump.suDump)) {
            return true
        }

        return false
    }

    private fun processSingleDump(dump: BinaryDump): Boolean {
        return dump.exists()
    }
}