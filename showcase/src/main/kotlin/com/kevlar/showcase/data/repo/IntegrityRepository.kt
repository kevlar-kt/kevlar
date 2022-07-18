package com.kevlar.showcase.data.repo

import android.content.Context
import android.util.Base64
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.integrity.KevlarIntegrity
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.integrity.model.HardcodedMetadata
import com.kevlar.showcase.concurrency.IoDispatcher
import com.kevlar.showcase.util.EncryptionUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository class designed to hold [KevlarAntipiracy]
 * */
class IntegrityRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
    /**
     * Plaintext
     * */
    private val plaintextHardcodedMetadata = HardcodedMetadata(
        packageName = "com.kevlar.showcase",
        signature = "J+nqXLfuIO8B2AmhkMYHGE4jDyw="
    )

    /**
     * Base64 obfuscated
     * */
    private val base64PackageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray(Charsets.UTF_8)
    private val base64Signature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray(Charsets.UTF_8)

    private val base64ObfuscatedHardcodedMetadata = HardcodedMetadata(
        Base64.decode(base64PackageName, Base64.DEFAULT).toString(Charsets.UTF_8),
        Base64.decode(base64Signature, Base64.DEFAULT).toString(Charsets.UTF_8)
    )

    /**
     * AES+Base64 encrypted
     * */
    private val key256 = """4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?Ds"""

    private val encryptedPackageName = """s3wf/AOYtr9BEMVFrweeLnkmerryUykMA8O77S5tMlI=""".toByteArray(Charsets.UTF_8)
    private val encryptedSignature = """tqMJquO3D+EKx1rx4R7/qzmsuEgpp1bKwxXe9AeB/WU=""".toByteArray(Charsets.UTF_8)

    private val aes256EncryptedHardcodedMetadata = HardcodedMetadata(
        EncryptionUtil.decrypt(encryptedPackageName, EncryptionUtil.generateKey(key256)),
        EncryptionUtil.decrypt(encryptedSignature, EncryptionUtil.generateKey(key256))
    )

    /**
     * Integrity package
     * */
    private val integrity = KevlarIntegrity(plaintextHardcodedMetadata) {
        checks {
            signature()
            packageName()
            installer()
            debug()
        }
    }

    suspend fun attestate(): IntegrityAttestation = withContext(externalDispatcher) {
        integrity.attestate(context)
    }
}