package com.kevlar.rooting.util

import dalvik.system.DexClassLoader
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.os.Bundle
import java.io.File
import java.lang.Exception
import java.util.ArrayList

/*
 * Copyright (C) 2016 Jared Rummler <jared.rummler@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ /**
 * Utility methods for checking if the Xposed framework is installed.
 */
internal object XposedUtil {
    /**
     * Get the current Xposed version installed on the device.
     *
     * @param context The application context
     * @return The Xposed version or `null` if Xposed isn't installed.
     */
    fun getXposedVersion(context: Context): Int? {
        try {
            val xposedBridge = File("/system/framework/XposedBridge.jar")
            if (xposedBridge.exists()) {
                val optimizedDir = context.getDir("dex", Context.MODE_PRIVATE)
                val dexClassLoader = DexClassLoader(
                    xposedBridge.path,
                    optimizedDir.path, null, ClassLoader.getSystemClassLoader()
                )
                val XposedBridge = dexClassLoader.loadClass("de.robv.android.xposed.XposedBridge")
                val getXposedVersion = XposedBridge.getDeclaredMethod("getXposedVersion")
                if (!getXposedVersion.isAccessible) getXposedVersion.isAccessible = true
                return getXposedVersion.invoke(null) as Int
            }
        } catch (ignored: Exception) {
        }
        return null
    }

    /**
     * Check if the Xposed installer is installed and enabled on the device.
     *
     * @param context The application context
     * @return `true` if the package "de.robv.android.xposed.installer" is installed and enabled.
     */
    fun isXposedInstallerAvailable(context: Context): Boolean {
        try {
            val appInfo =
                context.packageManager.getApplicationInfo("de.robv.android.xposed.installer", 0)
            if (appInfo != null) {
                return appInfo.enabled
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return false
    }

    /**
     * Check if the Xposed framework is installed and active.
     *
     * @return `true` if Xposed is active on the device.
     */
    val isXposedActive: Boolean
        get() {
            val stackTraces = Throwable().stackTrace
            for (stackTrace in stackTraces) {
                val clazzName = stackTrace.className
                if (clazzName != null && clazzName.contains("de.robv.android.xposed.XposedBridge")) {
                    return true
                }
            }
            return false
        }

    /**
     * Get all currently installed Xposed modules.
     *
     * @param context The application context
     * @return A list of installed Xposed modules.
     */
    fun getInstalledXposedPackages(context: Context): ArrayList<PackageInfo> {
        val packages = ArrayList<PackageInfo>()
        val pm = context.packageManager
        @SuppressLint("QueryPermissionsNeeded") val installedPackages =
            pm.getInstalledPackages(PackageManager.GET_META_DATA)
        for (installedPackage in installedPackages) {
            val metaData = installedPackage.applicationInfo.metaData
            if (metaData != null && metaData.containsKey("xposedmodule")) {
                packages.add(installedPackage)
            }
        }
        return packages
    }
}