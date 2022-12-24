/*
 * Designed and developed by Kevlar Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevlar.rooting.dataset

/**
 * This enumeration contains all the possible issues that kevlar may report in an attestation result.
 *
 * They are used to pinpoint checks that kevlar can run, and they are included in the attestation to
 * signify that a specific test has produced a negative result.
 *
 * Keep in mind that kevlar will run **only** the checks you explicitly requested in its configuration.
 *
 * ```kotlin
 * targets {
 *     root()
 *     busybox()
 * }
 * ```
 *
 * This means that you have to enable a specific option (see documentation) in order to tell kevlar to
 * actually run the check for it, which will eventually be reported here (if it is found to be present
 * on the host system).
 * */
public enum class DetectableSystemTarget {
    /**
     * Device emulation status.
     *
     * This item will be included in your attestation if the system your application is running in
     * is thought to be rooted.
     * */
    ROOT,

    /**
     * Device emulation status.
     *
     * This item will be included in your attestation if the system your application is running in
     * is thought to have busybox binaries installed.
     * */
    BUSYBOX,

    /**
     * Device emulation status.
     *
     * This item will be included in your attestation if the system your application is running in
     * is thought to have magisk installed.
     * */
    MAGISK,

    /**
     * Device emulation status.
     *
     * This item will be included in your attestation if the system your application is running in
     * is thought to have xposed installed.
     * */
    XPOSED,

    /**
     * Device emulation status.
     *
     * This item will be included in your attestation if the system your application is running in
     * is thought to have toybox binaries installed.
     * */
    TOYBOX;
}