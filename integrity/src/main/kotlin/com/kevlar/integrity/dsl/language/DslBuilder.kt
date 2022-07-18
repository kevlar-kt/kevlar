package com.kevlar.integrity.dsl.language

public abstract class DslBuilder<out K> {
    public abstract fun build(): K
}