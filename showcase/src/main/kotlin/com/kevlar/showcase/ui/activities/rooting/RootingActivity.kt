package com.kevlar.showcase.ui.activities.rooting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kevlar.rooting.dataset.SystemTarget
import com.kevlar.rooting.dsl.attestation.RootingAttestation
import com.kevlar.showcase.R
import com.kevlar.showcase.databinding.RootingActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RootingActivity : AppCompatActivity() {

    private val vm: RootingActivityViewModel by viewModels()

    private lateinit var binding: RootingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.rooting_activity)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.attestation.collectLatest {
                    when (it) {
                        is RootingAttestation.Blank -> {
                            binding.debugText.text = "Blank attestation"
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is RootingAttestation.Clear -> {
                            binding.debugText.text = "Clear attestation"
                            binding.progressBar.visibility = View.GONE
                        }
                        is RootingAttestation.Failed -> {
                            binding.debugText.text = buildString {
                                appendLine("Failed attestation")
                                appendLine()

                                it.detectedTargets.detectedTargets.forEachIndexed { i, it: SystemTarget ->
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
            vm.requestAttestation()
        }
    }
}