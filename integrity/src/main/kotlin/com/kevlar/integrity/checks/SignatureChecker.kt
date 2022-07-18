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
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)

        for (signature in packageInfo.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val currentSignature: String = Base64.encodeToString(md.digest(), Base64.DEFAULT)
            // Log.d("REMOVE_ME", "Include this string as a value for SIGNATURE:$currentSignature")

            if (hardcodedSignature == currentSignature) {
                return true
            }
        }

        false
    } catch (e: Exception) {
        false
    }
}

@SuppressLint("PackageManagerGetSignatures")
internal fun obtainBase64EncodedSignatures(context: Context): List<String> = try {
    context.packageManager.getPackageInfo(
        context.packageName,
        PackageManager.GET_SIGNATURES
    ).signatures.map {
        val md = MessageDigest.getInstance("SHA")
        md.update(it.toByteArray())

        Base64.encodeToString(md.digest(), Base64.DEFAULT)
    }
} catch (e: Exception) {
    listOf()
}