package com.kevlar.rooting.shell

/**
 * Roughly speaking, we can execute a command in 4 different ways, which will give us different
 * permission levels.
 *
 * - Application-level
 * - Shell
 * - Root
 * - ADB
 *
 * We model this so than we can record with which level of execution permissions
 * a certain command (dump) has been executed.
 * */
internal enum class ExecutionLevel {
    APP, SH, SU;
}