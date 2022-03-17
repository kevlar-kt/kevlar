package com.kevlar.antipiracy

public object Attestator {

    public suspend fun attestate(): AntipiracyAttestation {
        return AntipiracyAttestation.Clear()
    }
}