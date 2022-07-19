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

package com.kevlar.showcase.ui.activities.integrity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevlar.integrity.KevlarIntegrity
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.rooting.KevlarRooting
import com.kevlar.showcase.data.repo.IntegrityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntegrityActivityViewModel @Inject constructor(
    private val integrityRepository: IntegrityRepository
) : ViewModel() {

    /**
     * System status attestation
     * */
    private val _attestation = MutableStateFlow(KevlarIntegrity.blankAttestation())

    internal val attestation: SharedFlow<IntegrityAttestation> = _attestation.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = KevlarIntegrity.blankAttestation()
    )

    fun requestAttestation() {
        viewModelScope.launch {
            _attestation.value = integrityRepository.attestate()
        }
    }
}
