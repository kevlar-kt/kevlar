/**
 * Designed and developed by Andrea Cioccarelli (@cioccarellia)
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
package com.kevlar.showcase.ui.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kevlar.showcase.R
import com.kevlar.showcase.databinding.ActivityMainBinding
import com.kevlar.showcase.ui.activities.antipiracy.AntipiracyActivity
import com.kevlar.showcase.ui.activities.integrity.IntegrityActivity
import com.kevlar.showcase.ui.activities.rooting.RootingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.run {
            buttonAntipiracy.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, AntipiracyActivity::class.java)
                )
            }

            buttonRooting.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, RootingActivity::class.java)
                )
            }

            buttonIntegrity.setOnClickListener {
                startActivity(
                    Intent(this@MainActivity, IntegrityActivity::class.java)
                )
            }
        }
    }
}