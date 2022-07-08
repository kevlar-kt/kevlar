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
