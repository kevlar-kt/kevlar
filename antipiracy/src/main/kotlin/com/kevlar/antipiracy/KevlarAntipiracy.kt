package com.kevlar.antipiracy

import com.kevlar.antipiracy.dsl.builders.AntipiracyArmament
import com.kevlar.antipiracy.dsl.builders.AntipiracyArmamentBuilder

public class KevlarAntipiracy(
    block: AntipiracyArmamentBuilder.() -> Unit
) {
    private val armament: AntipiracyArmament = AntipiracyArmamentBuilder().apply(block).build()

    public suspend fun attestate(): AntipiracyAttestation = Attestator.attestate()
}
