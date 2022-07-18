package com.kevlar.integrity.dsl.attestation

import com.kevlar.integrity.dsl.settings.scan.CheckResult

/**
 * Attestation released by [KevlarIntegrity]
 * */
public sealed class IntegrityAttestation {

    /**
     * Attestation has *not* been verified yet
     * */
    public data class Blank(
        override val index: Int
    ) : IntegrityAttestation()


    /**
     * Attestation result is green light.
     * */
    public data class Clear(
        override val index: Int
    ) : IntegrityAttestation()

    /**
     * Attestation detected integrity issues.
     * */
    public data class Failed(
        override val index: Int,
        public val checkResult: CheckResult
    ) : IntegrityAttestation()

    /**
     * Counts the number of attestations released
     * */
    public abstract val index: Int
}