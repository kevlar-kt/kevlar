package com.kevlar.rooting.detection

import com.kevlar.rooting.dataset.SystemTarget
import com.kevlar.rooting.shell.dump.CombinedBinaryDump

internal sealed class OutputSpecter {

}

/**
 * Automated binary's output dump and analysis
 * */
internal data class TargetOutputDump(
    val target: SystemTarget,
    val associatedDumps: CombinedBinaryDump
) : OutputSpecter()

/**
 * Custom logic for specific targets
 * */
internal data class TargetCustomAnalysis(
    val target: SystemTarget,
    val detection: Boolean
) : OutputSpecter()

/**
 * Conveys no information about the status of the associated binary.
 * Usually because its detection has not been requested.
 * */
internal object BlankSpecter : OutputSpecter()

