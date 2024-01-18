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

package com.kevlar.showcase.ui.activities.integrity

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
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

        CoroutineScope(Dispatchers.Main).launch {
            vm.requestAttestation()
        }
    }
}