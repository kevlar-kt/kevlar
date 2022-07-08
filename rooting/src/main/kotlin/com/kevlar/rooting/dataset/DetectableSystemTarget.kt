package com.kevlar.rooting.dataset

/**
 * Defines a trait which can be targeted, analyzed and detected in the running system.
 * */
public enum class DetectableSystemTarget {
    ROOT, BUSYBOX, TOYBOX, MAGISK, XPOSED;
}