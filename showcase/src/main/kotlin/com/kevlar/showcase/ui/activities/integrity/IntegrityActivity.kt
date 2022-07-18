package com.kevlar.showcase.ui.activities.integrity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kevlar.integrity.KevlarIntegrity
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.rooting.dataset.DetectableSystemStatus
import com.kevlar.rooting.dataset.DetectableSystemTarget
import com.kevlar.rooting.dsl.attestation.status.StatusRootingAttestation
import com.kevlar.showcase.R
import com.kevlar.showcase.databinding.IntegrityActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntegrityActivity : AppCompatActivity() {

    private val vm: IntegrityActivityViewModel by viewModels()

    private lateinit var binding: IntegrityActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.integrity_activity)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.attestation.collectLatest {
                    when (it) {
                        is IntegrityAttestation.Blank -> {
                            binding.debugText.text = "Blank attestation"
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is IntegrityAttestation.Clear -> {
                            binding.debugText.text = "Clear attestation"
                            binding.progressBar.visibility = View.GONE
                        }
                        is IntegrityAttestation.Failed -> {
                            binding.debugText.text = buildString {
                                appendLine("Failed attestation")
                                appendLine()

                                it.checkResult.detectedElements.forEachIndexed { i, it ->
                                    appendLine("[$i] $it")
                                }

                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }

        Log.d("SIGNATURE", KevlarIntegrity.obtainCurrentAppSignatures(this).toString())

        CoroutineScope(Dispatchers.Main).launch {
            vm.requestAttestation()
        }
    }
}