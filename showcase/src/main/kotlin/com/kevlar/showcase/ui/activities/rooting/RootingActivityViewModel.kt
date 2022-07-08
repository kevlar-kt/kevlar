package com.kevlar.showcase.ui.activities.rooting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.rooting.KevlarRooting
import com.kevlar.rooting.dsl.attestation.RootingAttestation
import com.kevlar.showcase.data.repo.AntipiracyRepository
import com.kevlar.showcase.data.repo.RootingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootingActivityViewModel @Inject constructor(
    private val rootingRepository: RootingRepository
) : ViewModel() {

    private val _attestationState = MutableStateFlow(KevlarRooting.blankAttestation())

    val attestation: SharedFlow<RootingAttestation> = _attestationState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = KevlarRooting.blankAttestation()
    )

    fun requestAttestation() {
        viewModelScope.launch {
            _attestationState.value = rootingRepository.attestate()
        }
    }
}
