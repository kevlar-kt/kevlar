package com.kevlar.rooting.dsl.language

public abstract class DslBuilder<out K> {
    public abstract fun build(): K
}