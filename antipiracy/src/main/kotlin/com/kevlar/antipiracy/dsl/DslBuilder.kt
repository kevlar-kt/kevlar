package com.kevlar.antipiracy.dsl

/**
 * Helper class to enforce
 * */
public abstract class DslBuilder<out K> {
    public abstract fun build(): K
}