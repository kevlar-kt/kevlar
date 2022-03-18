package com.kevlar.antipiracy

import android.content.Context
import com.kevlar.antipiracy.dsl.builders.AntipiracyArmament
import com.kevlar.antipiracy.dsl.builders.AntipiracyArmamentBuilder

public class KevlarAntipiracy(
    block: AntipiracyArmamentBuilder.() -> Unit
) {
    private val armament: AntipiracyArmament = AntipiracyArmamentBuilder().apply(block).build()

    public suspend fun attestate(context: Context): AntipiracyAttestation = Attestator.attestate(armament, context, index++)

    public companion object {
        private var index = 0

        public fun defaultAttestation(): AntipiracyAttestation = AntipiracyAttestation.Unknown(0)
    }
}
