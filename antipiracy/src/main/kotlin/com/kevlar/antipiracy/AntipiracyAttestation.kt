package com.kevlar.antipiracy

import com.kevlar.antipiracy.dsl.builders.ScanResult

/**
 * Attestation released by [KevlarAntipiracy] to describe system's state at a given time
 * */
public sealed class AntipiracyAttestation {

    /**
     * Attestation has *not* been verified yet
     * */
    public data class Blank(
        override val index: Int
    ) : AntipiracyAttestation()


    /**
     * Attestation result is green light.
     * */
    public data class Clear(
        override val index: Int
    ) : AntipiracyAttestation()

    /**
     * Attestation detected pirate software installed.
     * */
    public data class Failed(
        override val index: Int,
        public val scanResult: ScanResult
    ) : AntipiracyAttestation()

    /**
     * Counts the number of attestations released
     * */
    public abstract val index: Int
}