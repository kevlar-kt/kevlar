package com.kevlar.antipiracy

public sealed class AntipiracyAttestation {
    public class Idle() : AntipiracyAttestation()
    public class Clear() : AntipiracyAttestation()
    public class Failed() : AntipiracyAttestation()
}

