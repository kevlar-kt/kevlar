package com.kevlar.showcase.data.repo

import android.content.Context
import android.util.Base64
import com.kevlar.antipiracy.dsl.attestation.AntipiracyAttestation
import com.kevlar.antipiracy.KevlarAntipiracy
import com.kevlar.integrity.KevlarIntegrity
import com.kevlar.integrity.dsl.attestation.IntegrityAttestation
import com.kevlar.integrity.model.HardcodedMetadata
import com.kevlar.showcase.concurrency.IoDispatcher
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
    private val hardcodedMetadata = HardcodedMetadata(
        packageName = "com.kevlar.showcase",
        signature = "J+nqXLfuIO8B2AmhkMYHGE4jDyw="
    )

    private val packageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray(Charsets.UTF_8)
    private val signature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray(Charsets.UTF_8)

    private val base64ObfuscatedHardcodedMetadata = HardcodedMetadata(
        Base64.decode(packageName, Base64.DEFAULT).toString(Charsets.UTF_8),
        Base64.decode(signature, Base64.DEFAULT).toString(Charsets.UTF_8)
    )



    private val packageName = """7KAa2CFkhPQOUouDu32KZJLqOzGFbTTnJA3rGxMlAg4="""
    private val signature = """+ylMx63kwFRmXKHQU0cbzyb8MJ1iiGW1g8+MjDRcS/o="""

    private val encryptedHardcodedMetadata = HardcodedMetadata(
        decrypt(packageName)
        decrypt(signature)
    )


    private val integrity = KevlarIntegrity(base64ObfuscatedHardcodedMetadata) {
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