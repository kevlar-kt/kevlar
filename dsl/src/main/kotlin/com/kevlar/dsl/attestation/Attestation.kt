package com.kevlar.dsl.attestation

import com.kevlar.dsl.settings.ScanResult

/**
 * Attestation
 * */
public sealed class Attestation {

    /**
     * BLANK
     * */
    public data class Blank(
        override val index: Int
    ) : Attestation()


    /**
     * CLEAR
     * */
    public data class Clear(
        override val index: Int
    ) : Attestation()

    /**
     * FAILED
     * */
    public data class Failed(
        override val index: Int,
        public val scanResult: ScanResult
    ) : Attestation()

    public abstract val index: Int
}