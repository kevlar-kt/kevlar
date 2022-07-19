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

package com.kevlar.showcase.ui.activities.rooting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevlar.rooting.KevlarRooting
import com.kevlar.rooting.dsl.attestation.status.StatusRootingAttestation
import com.kevlar.rooting.dsl.attestation.target.TargetRootingAttestation
import com.kevlar.showcase.data.repo.RootingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootingActivityViewModel @Inject constructor(
    private val rootingRepository: RootingRepository
) : ViewModel() {

    /**
     * System modification attestation
     * */
    private val _rootAttestationState = MutableStateFlow(KevlarRooting.blankTargetAttestation())

    val rootAttestation: SharedFlow<TargetRootingAttestation> = _rootAttestationState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = KevlarRooting.blankTargetAttestation()
    )

    fun requestAttestationRoot() {
        viewModelScope.launch {
            _rootAttestationState.value = rootingRepository.attestateRoot()
        }
    }



    /**
     * System status attestation
     * */
    private val _statusAttestationState = MutableStateFlow(KevlarRooting.blankStatusAttestation())

    val statusAttestation: SharedFlow<StatusRootingAttestation> = _statusAttestationState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = KevlarRooting.blankStatusAttestation()
    )

    fun requestAttestationStatus() {
        viewModelScope.launch {
            _statusAttestationState.value = rootingRepository.attestateStatus()
        }
    }
}
