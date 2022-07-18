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
