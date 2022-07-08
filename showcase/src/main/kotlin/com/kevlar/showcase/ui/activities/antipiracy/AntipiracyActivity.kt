package com.kevlar.showcase.ui.activities.antipiracy

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.antipiracy.dataset.DatasetEntry
import com.kevlar.showcase.R
import com.kevlar.showcase.databinding.AntipiracyActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AntipiracyActivity : AppCompatActivity() {

    private val vm: APActivityViewModel by viewModels()

    private lateinit var binding: AntipiracyActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.antipiracy_activity)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.attestation.collectLatest {
                    when (it) {
                        is AntipiracyAttestation.Blank -> {
                            binding.debugText.text = "Blank attestation"
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is AntipiracyAttestation.Clear -> {
                            binding.debugText.text = "Clear attestation"
                            binding.progressBar.visibility = View.GONE
                        }
                        is AntipiracyAttestation.Failed -> {
                            binding.debugText.text = buildString {
                                appendLine("Failed attestation")
                                appendLine()

                                it.scanResult.detectedEntries.forEachIndexed { i, it: DatasetEntry ->
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