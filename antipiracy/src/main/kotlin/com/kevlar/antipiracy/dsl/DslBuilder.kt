package com.kevlar.antipiracy.dsl

public abstract class DslBuilder<out K> {
    public abstract fun build(): K
}