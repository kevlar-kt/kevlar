package com.kevlar.antipiracy

import com.kevlar.antipiracy.dsl.builders.ScanResult

/**
 * Attestation released by [KevlarAntipiracy] to describe system's state at a given time
 * */
public sealed class AntipiracyAttestation {

    /**
     * Counts the number of attestation released
     * */
    public abstract val index: Int

    /**
     * Attestation has not been verified yet
     * */
    public class Blank(
        override val index: Int
    ) : AntipiracyAttestation()

    /**
     * Attestation result is green light.
     * */
    public class Clear(
        override val index: Int
    ) : AntipiracyAttestation()

    /**
     * Attestation detected pirate software installed.
     * */
    public class Failed(
        override val index: Int,
        public val scanResult: ScanResult
    ) : AntipiracyAttestation()
}