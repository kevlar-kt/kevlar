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

package com.kevlar.rooting.detection.target

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