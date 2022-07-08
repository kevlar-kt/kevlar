package com.kevlar.rooting.dsl.attestation

import com.kevlar.rooting.dsl.settings.target.TargetResult

/**
 * Attestation
 * */
public sealed class RootingAttestation {

    /**
     * BLANK
     * */
    public data class Blank(
        override val index: Int
    ) : RootingAttestation()


    /**
     * CLEAR
     * */
    public data class Clear(
        override val index: Int
    ) : RootingAttestation()

    /**
     * FAILED
     * */
    public data class Failed(
        override val index: Int,
        public val scanResult: TargetResult
    ) : RootingAttestation()

    public abstract val index: Int
}