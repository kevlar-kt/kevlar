package com.kevlar.showcase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevlar.antipiracy.AntipiracyAttestation
import com.kevlar.showcase.data.repo.SecurityRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val securityRepository: SecurityRepository
) : ViewModel() {

    private val _attestationState = MutableStateFlow<AntipiracyAttestation>(AntipiracyAttestation.Idle())
    val attestation: SharedFlow<AntipiracyAttestation> = _attestationState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = AntipiracyAttestation.Idle()
    )

    fun requestAttestation() {
        viewModelScope.launch {
            _attestationState.value = securityRepository.attestate()
        }
    }
}
