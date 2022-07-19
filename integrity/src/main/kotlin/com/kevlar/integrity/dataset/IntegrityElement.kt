package com.kevlar.integrity.dataset

/**
 * A single check which can be run from kevlar.
 * */
public enum class IntegrityElement {
    MATCH_HARDCODED_SIGNATURE,
    MATCH_HARDCODED_PACKAGE_NAME,
    DEBUG_BUILD,
    UNAUTHORIZED_INSTALLER;
}