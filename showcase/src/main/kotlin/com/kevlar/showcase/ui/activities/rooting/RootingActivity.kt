package com.kevlar.showcase.ui.activities.rooting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kevlar.rooting.dataset.DetectableSystemStatus
import com.kevlar.rooting.dataset.DetectableSystemTarget
import com.kevlar.rooting.dsl.attestation.status.StatusRootingAttestation
import com.kevlar.rooting.dsl.attestation.target.TargetRootingAttestation
import com.kevlar.showcase.R
import com.kevlar.showcase.databinding.RootingActivityBinding
import com.kevlar.showcase.ui.activities.integrity.IntegrityActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RootingActivity : AppCompatActivity() {

    private val vm: IntegrityActivityViewModel by viewModels()

    private lateinit var binding: RootingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.rooting_activity)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.rootAttestation.collectLatest {
                    when (it) {
                        is TargetRootingAttestation.Blank -> {
                            binding.debugText1.text = "Blank attestation"
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is TargetRootingAttestation.Clear -> {
                            binding.debugText1.text = "Clear attestation"
                            binding.progressBar.visibility = View.GONE
                        }
                        is TargetRootingAttestation.Failed -> {
                            binding.debugText1.text = buildString {
                                appendLine("Failed attestation")
                                appendLine()

                                it.detectedTargets.detectedTargets.forEachIndexed { i, it: DetectableSystemTarget ->
                                    appendLine("[$i] $it")
                                }

                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                vm.statusAttestation.collectLatest {
                    when (it) {
                        is StatusRootingAttestation.Blank -> {
                            binding.debugText2.text = "Blank attestation"
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is StatusRootingAttestation.Clear -> {
                            binding.debugText2.text = "Clear attestation"
                            binding.progressBar.visibility = View.GONE
                        }
                        is StatusRootingAttestation.Failed -> {
                            binding.debugText2.text = buildString {
                                appendLine("Failed attestation")
                                appendLine()

                                it.status.detectedStatus.forEachIndexed { i, it: DetectableSystemStatus ->
                                    appendLine("[$i] $it")
                                }

                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            vm.requestAttestationRoot()
            vm.requestAttestationStatus()
        }
    }
}