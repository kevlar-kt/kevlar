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

package com.kevlar.rooting.dsl.attestation.status

import com.kevlar.rooting.dsl.settings.status.StatusResult
import com.kevlar.rooting.dsl.settings.target.TargetResult

/**
 * Attestation released by [com.kevlar.rooting.KevlarRooting].
 * */
public sealed class StatusRootingAttestation {

    /**
     * Attestation has *not* been verified yet.
     * This state does not carry any information.
     * */
    public data class Blank(
        override val index: Int
    ) : StatusRootingAttestation()


    /**
     * Attestation has passed.
     * You have green light according to your settings.
     * */
    public data class Clear(
        override val index: Int
    ) : StatusRootingAttestation()

    /**
     * Attestation has not passed.
     * Abnormal system status has been detected
     * according to your settings.
     * */
    public data class Failed(
        override val index: Int,
        public val status: StatusResult
    ) : StatusRootingAttestation()

    /**
     * Counts the number of attestations released.
     * */
    public abstract val index: Int
}