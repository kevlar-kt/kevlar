package com.kevlar.showcase.ui.activities.antipiracy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kevlar.antipiracy.AntipiracyAttestation
import com.kevlar.antipiracy.detection.dataset.DatasetEntry
import com.kevlar.showcase.R
import com.kevlar.showcase.databinding.AntipiracyActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
                        }
                        is AntipiracyAttestation.Clear -> {
                            binding.debugText.text = "Clear attestation"
                        }
                        is AntipiracyAttestation.Failed -> {
                            binding.debugText.text = buildString {
                                appendLine("Failed attestation")
                                appendLine()

                                it.scanResult.detectedEntries.forEachIndexed { i, it: DatasetEntry ->
                                    appendLine("[$i] $it")
                                }
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