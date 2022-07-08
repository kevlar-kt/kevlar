package com.kevlar.rooting.dsl.attestation.status

import com.kevlar.rooting.dsl.settings.status.StatusResult
import com.kevlar.rooting.dsl.settings.target.TargetResult

/**
 * Attestation
 * */
public sealed class StatusRootingAttestation {

    /**
     * BLANK
     * */
    public data class Blank(
        override val index: Int
    ) : StatusRootingAttestation()


    /**
     * CLEAR
     * */
    public data class Clear(
        override val index: Int
    ) : StatusRootingAttestation()

    /**
     * FAILED
     * */
    public data class Failed(
        override val index: Int,
        public val status: StatusResult
    ) : StatusRootingAttestation()

    public abstract val index: Int
}