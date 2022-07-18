package com.kevlar.showcase.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private const val algorithm = "AES"
    private const val transformation = "AES/ECB/PKCS5Padding"

    fun generateKey(key: String): SecretKey = SecretKeySpec(key.toByteArray(), algorithm)

    fun encrypt(text: ByteArray, secret: SecretKey): String {
        val cipher: Cipher = Cipher.getInstance(transformation).apply {
            init(Cipher.ENCRYPT_MODE, secret)
        }

        return Base64.encodeToString(cipher.doFinal(text), Base64.NO_WRAP) ?: ""
    }

    fun decrypt(ciphertext: ByteArray, secret: SecretKey): String {
        val cipher: Cipher = Cipher.getInstance(transformation).apply {
            init(Cipher.DECRYPT_MODE, secret)
        }

        return String(cipher.doFinal(Base64.decode(ciphertext, Base64.NO_WRAP)), Charsets.UTF_8)
    }
}