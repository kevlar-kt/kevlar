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

package com.kevlar.rooting.shell

import com.kevlar.rooting.shell.dump.BinaryDump
import com.kevlar.rooting.shell.dump.CombinedBinaryDump
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext


/**
 * We make 3 asynchronous passes with different permission levels, and for each one
 * we test commands and infer whether root access is installed on the device
 * */
internal suspend fun CombinedBinaryDump(
    binaryName: String,
    packageName: String,
    allowExplicitRootCheck: Boolean
): CombinedBinaryDump = withContext(Dispatchers.IO) {
    val appDump = async {
        BinaryDump(binaryName, packageName, ExecutionLevel.APP, allowExplicitRootCheck)
    }

    val binDump = async {
        BinaryDump(binaryName, packageName, ExecutionLevel.SH, allowExplicitRootCheck)
    }

    val suDump = async {
        BinaryDump(binaryName, packageName, ExecutionLevel.SU, allowExplicitRootCheck)
    }

    awaitAll(appDump, binDump, suDump)

    CombinedBinaryDump(appDump.await(), binDump.await(), suDump.await())
}

internal suspend fun BinaryDump(
    binaryName: String,
    packageName: String,
    level: ExecutionLevel,
    allowExplicitRootCheck: Boolean
): BinaryDump = withContext(Dispatchers.IO) {
    when (level) {
        // App-level check
        ExecutionLevel.APP -> {
            val shellId = async {
                // We execute the `id` command as the running app
                Shell.sh("run-as $packageName id").exec()
            }

            val binaryPathExtraction = async {
                // We execute the `which` command [it should give us the command path, if found]
                Shell.sh("run-as $packageName which $binaryName").exec()
            }

            val binaryTest = async {
                // At last, we execute the command itself
                Shell.sh("run-as $packageName $binaryName").exec()
            }

            awaitAll(shellId, binaryPathExtraction, binaryTest)

            BinaryDump(
                binaryName = binaryName,
                executionLevel = ExecutionLevel.APP,
                userResult = shellId.await(),
                invocationResult = binaryTest.await(),
                pathExtractionResult = binaryPathExtraction.await()
            )
        }

        // Shell-level check
        ExecutionLevel.SH -> {
            val shellId = async {
                // We execute the `id` command as the shell
                Shell.sh("id").exec()
            }

            val binaryPathExtraction = async {
                // We execute the `which` command [it should give us the command path, if found]
                Shell.sh("which $binaryName").exec()
            }

            val binaryTest = async {
                // At last, we execute the command itself
                Shell.sh(binaryName).exec()
            }

            awaitAll(shellId, binaryPathExtraction, binaryTest)

            BinaryDump(
                binaryName = binaryName,
                executionLevel = ExecutionLevel.SH,
                userResult = shellId.await(),
                invocationResult = binaryTest.await(),
                pathExtractionResult = binaryPathExtraction.await()
            )
        }
        ExecutionLevel.SU -> {
            if (allowExplicitRootCheck) {
                val shellId = async {
                    // We execute the `id` command as root
                    Shell.su("id").exec()
                }

                val binaryPathExtraction = async {
                    // We execute the `which` command [it should give us the command path, if found]
                    Shell.su("which $binaryName").exec()
                }

                val binaryTest = async {
                    // At last, we execute the command itself
                    Shell.su(binaryName).exec()
                }

                awaitAll(shellId, binaryPathExtraction, binaryTest)

                BinaryDump(
                    binaryName = binaryName,
                    executionLevel = ExecutionLevel.SU,
                    userResult = shellId.await(),
                    invocationResult = binaryTest.await(),
                    pathExtractionResult = binaryPathExtraction.await()
                )
            } else {
                BinaryDump.emptyBinaryDump(binaryName, ExecutionLevel.SU)
            }
        }
    }
}