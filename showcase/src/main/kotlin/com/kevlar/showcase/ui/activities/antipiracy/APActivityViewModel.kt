package com.kevlar.showcase.ui.activities.antipiracy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevlar.antipiracy.AntipiracyAttestation
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.showcase.data.repo.SecurityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class APActivityViewModel @Inject constructor(
    private val securityRepository: SecurityRepository
) : ViewModel() {

    private val _attestationState = MutableStateFlow(KevlarAntipiracy.blankAttestation())

    val attestation: SharedFlow<AntipiracyAttestation> = _attestationState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = KevlarAntipiracy.blankAttestation()
    )

    fun requestAttestation() {
        viewModelScope.launch {
            _attestationState.value = securityRepository.attestate()
        }
    }
}
