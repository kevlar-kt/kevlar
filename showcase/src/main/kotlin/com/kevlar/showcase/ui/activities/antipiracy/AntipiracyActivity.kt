/*
 * Designed and developed by Kevlar Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kevlar.showcase.ui.activities.antipiracy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kevlar.antipiracy.dataset.ScanEntry
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.antipiracy.dataset.Threat
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

    @SuppressLint("SetTextI18n")
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

                                it.scanResult.detectedEntries.forEachIndexed { i, it: ScanEntry ->
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