package com.kevlar.rooting.util;

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
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Utility methods for checking if the Xposed framework is installed.
 */
public class XposedUtil {

    /**
     * Get the current Xposed version installed on the device.
     *
     * @param context The application context
     * @return The Xposed version or {@code null} if Xposed isn't installed.
     */
    public static Integer getXposedVersion(Context context) {
        try {
            File xposedBridge = new File("/system/framework/XposedBridge.jar");
            if (xposedBridge.exists()) {
                File optimizedDir = context.getDir("dex", Context.MODE_PRIVATE);
                DexClassLoader dexClassLoader = new DexClassLoader(xposedBridge.getPath(),
                        optimizedDir.getPath(), null, ClassLoader.getSystemClassLoader());
                Class<?> XposedBridge = dexClassLoader.loadClass("de.robv.android.xposed.XposedBridge");
                Method getXposedVersion = XposedBridge.getDeclaredMethod("getXposedVersion");
                if (!getXposedVersion.isAccessible()) getXposedVersion.setAccessible(true);
                return (Integer) getXposedVersion.invoke(null);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Check if the Xposed installer is installed and enabled on the device.
     *
     * @param context The application context
     * @return {@code true} if the package "de.robv.android.xposed.installer" is installed and enabled.
     */
    public static boolean isXposedInstallerAvailable(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo("de.robv.android.xposed.installer", 0);
            if (appInfo != null) {
                return appInfo.enabled;
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return false;
    }

    /**
     * Check if the Xposed framework is installed and active.
     *
     * @return {@code true} if Xposed is active on the device.
     */
    public static boolean isXposedActive() {
        StackTraceElement[] stackTraces = new Throwable().getStackTrace();
        for (StackTraceElement stackTrace : stackTraces) {
            final String clazzName = stackTrace.getClassName();
            if (clazzName != null && clazzName.contains("de.robv.android.xposed.XposedBridge")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all currently installed Xposed modules.
     *
     * @param context The application context
     * @return A list of installed Xposed modules.
     */
    public static ArrayList<PackageInfo> getInstalledXposedPackages(Context context) {
        ArrayList<PackageInfo> packages = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_META_DATA);
        for (PackageInfo installedPackage : installedPackages) {
            Bundle metaData = installedPackage.applicationInfo.metaData;
            if (metaData != null && metaData.containsKey("xposedmodule")) {
                packages.add(installedPackage);
            }
        }
        return packages;
    }

}