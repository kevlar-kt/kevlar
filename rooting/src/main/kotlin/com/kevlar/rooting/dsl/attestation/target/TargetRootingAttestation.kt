package com.kevlar.rooting.dsl.attestation.target

import com.kevlar.rooting.dsl.settings.target.TargetResult

/**
 * Attestation
 * */
public sealed class TargetRootingAttestation {

    /**
     * BLANK
     * */
    public data class Blank(
        override val index: Int
    ) : TargetRootingAttestation()


    /**
     * CLEAR
     * */
    public data class Clear(
        override val index: Int
    ) : TargetRootingAttestation()

    /**
     * FAILED
     * */
    public data class Failed(
        override val index: Int,
        public val detectedTargets: TargetResult
    ) : TargetRootingAttestation()

    public abstract val index: Int
}