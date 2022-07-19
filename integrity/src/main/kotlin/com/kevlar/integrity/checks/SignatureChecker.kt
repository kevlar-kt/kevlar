package com.kevlar.integrity.checks

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import java.security.MessageDigest

/**
 * Checks
 *
 * @return whether the given signature matches the running application's
 * */
@SuppressLint("PackageManagerGetSignatures")
internal fun matchesHardcodedSignature(
    hardcodedSignature: String,
    context: Context
): Boolean {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_SIGNATURES
        )

        /**
         * We are guaranteed to have at least one signature, even if it is for debug
         * */
        for (signature in packageInfo.signatures) {
            val md = MessageDigest.getInstance("SHA").apply {
                update(signature.toByteArray())
            }

            val runtimeSignature = Base64.encodeToString(md.digest(), Base64.NO_WRAP)

            if (runtimeSignature == hardcodedSignature) {
                return true
            }
        }

        false
    } catch (e: Exception) {
        false
    }
}

@SuppressLint("PackageManagerGetSignatures")
internal fun obtainBase64EncodedSignatures(
    context: Context
): List<String> = context.packageManager
    .getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
    .signatures
    .map {
        val md = MessageDigest.getInstance("SHA").apply {
            update(it.toByteArray())
        }

        Base64.encodeToString(md.digest(), Base64.NO_WRAP)
    }