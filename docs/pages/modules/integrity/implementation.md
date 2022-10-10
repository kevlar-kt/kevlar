# Implementation

Implementing `integrity` requires, on top of a `KevlarAntipiracy` and the attestation infrastructure, a bit of information about your application's metadata, which will be hardcoded inside it.

This is necessary to provide kevlar a (hardcoded) truth value to match the runtime values (which may have been tampered or altered by an attacker) against.
The obfuscation is necessary because we need to conceal the truth values, since they will be looked for by the attacker (software or human), and make it as hard as possible to automatically find and patch them.

A working example for the integrity module can be found in the github repository under the `:showcase` module.

## Dependency

???+ gradle "Gradle"

	``` java
	dependencies {
    	implementation "io.github.kevlar-kt:integrity:1.0.0"
	}
	```

??? gradle "Kotlin DSL"

	``` kotlin
	dependencies {
	    implementation("io.github.kevlar-kt:integrity:1.0.0")
	}
	```

??? gradle "Maven"

	``` xml
	<dependency>
	    <groupId>io.github.kevlar-kt</groupId>
	    <artifactId>integrity</artifactId>
	    <version>1.0.0</version>
	    <type>pom</type>
	</dependency>
	```

## Hardcoded metadata
The first step is choosing which 

- Your application **package name**;
- Your application **signature**.

They go right in a class which is required in order to initialize `KevlarIntegrity`. 

```kotlin title="hardcoded_metadata.kt"
private val hardcodedMetadata = HardcodedMetadata(
    packageName = "com.kevlar.showcase",           // showcase package name
    signature = "K+wrXLluOI8B5LkisWQPE4jLew="      // debug signature
)
```

### Finding metadata
You already know the package name, and the signature can be extracted directly from kevlar with:

```kotlin 
KevlarIntegrity.obtainCurrentAppSignature(context)
```

Grab that string value and save it along with your package name.

!!! fail "Do not"
	Maybe, just maybe, you may be tempted to so something like this:

	```kotlin title="VERY_BAD_hardcoded_metadata.kt"
	private val notAtAllHardcodedMetadata = HardcodedMetadata(
	    packageName = context.getPackageName(),
	    signature = getRuntimeSignature()
	)
	```

	Refrain from coding this monstruosity. The sole purpose of `HardcodedMetadata` is **[hardcoding](https://en.wikipedia.org/wiki/Hard_coding)** truth values inside your app. This little snippet single handedly kills the whole library (because obviously the runtime package name will match the runtime package name) and is like shooting yourself in the foot with a cannon. Don't. 


### Obfuscating metadata
The second step (optional but recommended) is obfuscating the metadata you just read, so that it is **saved** in an obfuscated form (in your bytecode, so that automatic tools can't find it), but passed to kevlar deobfuscated (so that we have the original truth values).

There are a few ways to do it:

#### No obfuscation (not recommended)
You just save the values as they are and pass them in HardcodedMetadata

```kotlin title="unobfuscated_hardcoded_metadata.kt"
private const val packageName = """com.kevlar.showcase"""
private const val signature = """J+nqXLfuIO8B2AmhkMYHGE4jDyw="""

private val hardcodedMetadata = HardcodedMetadata(packageName, signature)
```

??? bug "Bytecode"

	The produced kotlin bytecode clearly exposes the raw values:
	
	``` linenums="1" hl_lines="6 8"
	L1
	 ALOAD 0
	 NEW com/kevlar/integrity/model/HardcodedMetadata
	 DUP
	L2
	 LDC "com.kevlar.showcase"
	L3
	 LDC "J+nqXLfuIO8B2AmhkMYHGE4jDyw="
	L4
	 INVOKESPECIAL com/kevlar/integrity/model/HardcodedMetadata.<init> (Ljava/lang/String;Ljava/lang/String;)V
	 PUTFIELD com/kevlar/showcase/data/repo/IntegrityRepository.hardcodedMetadata : Lcom/kevlar/integrity/model/HardcodedMetadata;
	```

#### Base64 obfuscation
You store the package name and signature values as Base64-encoded byte arrays, and they go through the `Base64.decode()` function when creating `HardcodedMetadata`.

```kotlin title="base64_obfuscated_hardcoded_metadata.kt"
private val packageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray(Charsets.UTF_8)
private val signature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray(Charsets.UTF_8)

private val base64ObfuscatedHardcodedMetadata = HardcodedMetadata(
    Base64.decode(packageName, Base64.DEFAULT).toString(Charsets.UTF_8),
    Base64.decode(signature, Base64.DEFAULT).toString(Charsets.UTF_8)
)
```

Where `Y29tLmtldmxhci5zaG93Y2FzZQ==` is the base64 encoding of `com.kevlar.showcase`, and `SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==` of `J+nqXLfuIO8B2AmhkMYHGE4jDyw=`. 

You can look them up [online](https://www.base64encode.org), grab them from your app or use `openssl base64` in the terminal.

!!! info "Base64 flags & charset"
	The flag field and charset don't necessarily need to be `Base64.DEFAULT` and `UTF_8`. Even though they are the most popular, you may choose something else if you prefer, as long as you preserve consistency.

??? bug "Bytecode"
	Here the metadata is hidden and not targetable with basic find-and-replace techniques

	``` linenums="1" hl_lines="3 19"
	L6
     ALOAD 0
     LDC "Y29tLmtldmxhci5zaG93Y2FzZQ=="
     ASTORE 3
     GETSTATIC kotlin/text/Charsets.UTF_8 : Ljava/nio/charset/Charset;
     ASTORE 4
	L7
	 ALOAD 3
	L8
	 ALOAD 4
	 INVOKEVIRTUAL java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
	 DUP
	 LDC "(this as java.lang.String).getBytes(charset)"
	 INVOKESTATIC kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
	L9
	 PUTFIELD com/kevlar/showcase/data/repo/IntegrityRepository.packageName : [B
	L10
	 ALOAD 0
	 LDC "SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ=="
	 ASTORE 3
	 GETSTATIC kotlin/text/Charsets.UTF_8 : Ljava/nio/charset/Charset;
	 ASTORE 4
	L11
	 ALOAD 3
	L12
	 ALOAD 4
	 INVOKEVIRTUAL java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
	 DUP
	 LDC "(this as java.lang.String).getBytes(charset)"
	 INVOKESTATIC kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
	L13
	 PUTFIELD com/kevlar/showcase/data/repo/IntegrityRepository.signature : [B
	L14
	 ALOAD 0
	 NEW com/kevlar/integrity/model/HardcodedMetadata
	 DUP
	L15
	 ALOAD 0
	 GETFIELD com/kevlar/showcase/data/repo/IntegrityRepository.packageName : [B
	 ICONST_0
	 INVOKESTATIC android/util/Base64.decode ([BI)[B
	 DUP
	 LDC "Base64.decode(packageName, Base64.DEFAULT)"
	 INVOKESTATIC kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
	 ASTORE 3
	 GETSTATIC kotlin/text/Charsets.UTF_8 : Ljava/nio/charset/Charset;
	 ASTORE 4
	L16
	 NEW java/lang/String
	 DUP
	 ALOAD 3
	 ALOAD 4
	 INVOKESPECIAL java/lang/String.<init> ([BLjava/nio/charset/Charset;)V
	L17
	L18
	 ALOAD 0
	 GETFIELD com/kevlar/showcase/data/repo/IntegrityRepository.signature : [B
	 ICONST_0
	 INVOKESTATIC android/util/Base64.decode ([BI)[B
	 DUP
	 LDC "Base64.decode(signature, Base64.DEFAULT)"
	 INVOKESTATIC kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
	 ASTORE 3
	 GETSTATIC kotlin/text/Charsets.UTF_8 : Ljava/nio/charset/Charset;
	 ASTORE 4
	L19
	 NEW java/lang/String
	 DUP
	 ALOAD 3
	 ALOAD 4
	 INVOKESPECIAL java/lang/String.<init> ([BLjava/nio/charset/Charset;)V
	L20
	L21
	 INVOKESPECIAL com/kevlar/integrity/model/HardcodedMetadata.<init> (Ljava/lang/String;Ljava/lang/String;)V
	 PUTFIELD com/kevlar/showcase/data/repo/IntegrityRepository.base64ObfuscatedHardcodedMetadata : Lcom/kevlar/integrity/model/HardcodedMetadata;
	```


#### Encryption (+base64)
A better alternative is to encrypt the hardcoded metadata, store them in an encrypted form, and send them through a decryption function when creating `HardcodedMetadata`.

```kotlin title="encrypted_hardcoded_metadata.kt"
private val key256 = """4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?D"""

private val packageName = """s3wf/AOYtr9BEMVFrweeLnkmerryUykMA8O77S5tMlI=""".toByteArray(Charsets.UTF_8)
private val signature = """tqMJquO3D+EKx1rx4R7/qzmsuEgpp1bKwxXe9AeB/WU=""".toByteArray(Charsets.UTF_8)

private val encryptedHardcodedMetadata = HardcodedMetadata(
	EncryptionUtil.decrypt(packageName, key256), 
	EncryptionUtil.decrypt(signature, key256)
)
```

Where `7KAa2CFkhPQOUouDu32KZJLqOzGFbTTnJA3rGxMlAg4=` is the encrypted value of `com.kevlar.showcase`, and `+ylMx63kwFRmXKHQU0cbzyb8MJ1iiGW1g8+MjDRcS/o=` of `J+nqXLfuIO8B2AmhkMYHGE4jDyw=`.

This ensures that there is no possibility that an automatic attack picks up the string as a package name or signature, and trivial string substitutions or encodings like base64 won't give any information away (the ciphertext is encoded in base64).

AES128 or AES256 is recommended as the encryption algorithm (it's a little overkill but it does the job).

The ciphertext may also be stored as a byte array.

??? tldr "Encryption utility"

	[This](https://github.com/kevlar-kt/kevlar/blob/master/showcase/src/main/kotlin/com/kevlar/showcase/util/EncryptionUtil.kt) tiny class (from the showcase module in the repository) is a basic AES encryption/decryption utility.

	```kotlin title="EncryptionUtil.kt"
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
	```

??? bug "Bytecode"

	Here detecting and reconstructing the original package name automatically is basically impossible

	``` linenums="1" hl_lines="7 23"
	L22
	 ALOAD 0
	 LDC "4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?Ds"
	 PUTFIELD com/kevlar/showcase/data/repo/IntegrityRepository.key256 : Ljava/lang/String;
	L23
	 ALOAD 0
	 LDC "s3wf/AOYtr9BEMVFrweeLnkmerryUykMA8O77S5tMlI="
	 ASTORE 3
	 GETSTATIC kotlin/text/Charsets.UTF_8 : Ljava/nio/charset/Charset;
	 ASTORE 4
	L24
	 ALOAD 3
	L25
	 ALOAD 4
	 INVOKEVIRTUAL java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
	 DUP
	 LDC "(this as java.lang.String).getBytes(charset)"
	 INVOKESTATIC kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
	L26
	 PUTFIELD com/kevlar/showcase/data/repo/IntegrityRepository.encryptedPackageName : [B
	L27
	 ALOAD 0
	 LDC "tqMJquO3D+EKx1rx4R7/qzmsuEgpp1bKwxXe9AeB/WU="
	 ASTORE 3
	 GETSTATIC kotlin/text/Charsets.UTF_8 : Ljava/nio/charset/Charset;
	 ASTORE 4
	L28
	 ALOAD 3
	L29
	 ALOAD 4
	 INVOKEVIRTUAL java/lang/String.getBytes (Ljava/nio/charset/Charset;)[B
	 DUP
	 LDC "(this as java.lang.String).getBytes(charset)"
	 INVOKESTATIC kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue (Ljava/lang/Object;Ljava/lang/String;)V
	L30
	 PUTFIELD com/kevlar/showcase/data/repo/IntegrityRepository.encryptedSignature : [B
	L31
	 ALOAD 0
	 NEW com/kevlar/integrity/model/HardcodedMetadata
	 DUP
	L32
	 GETSTATIC com/kevlar/showcase/util/EncryptionUtil.INSTANCE : Lcom/kevlar/showcase/util/EncryptionUtil;
	 ALOAD 0
	 GETFIELD com/kevlar/showcase/data/repo/IntegrityRepository.encryptedPackageName : [B
	 GETSTATIC com/kevlar/showcase/util/EncryptionUtil.INSTANCE : Lcom/kevlar/showcase/util/EncryptionUtil;
	 ALOAD 0
	 GETFIELD com/kevlar/showcase/data/repo/IntegrityRepository.key256 : Ljava/lang/String;
	 INVOKEVIRTUAL com/kevlar/showcase/util/EncryptionUtil.generateKey (Ljava/lang/String;)Ljavax/crypto/SecretKey;
	 INVOKEVIRTUAL com/kevlar/showcase/util/EncryptionUtil.decrypt ([BLjavax/crypto/SecretKey;)Ljava/lang/String;
	L33
	 GETSTATIC com/kevlar/showcase/util/EncryptionUtil.INSTANCE : Lcom/kevlar/showcase/util/EncryptionUtil;
	 ALOAD 0
	 GETFIELD com/kevlar/showcase/data/repo/IntegrityRepository.encryptedSignature : [B
	 GETSTATIC com/kevlar/showcase/util/EncryptionUtil.INSTANCE : Lcom/kevlar/showcase/util/EncryptionUtil;
	 ALOAD 0
	 GETFIELD com/kevlar/showcase/data/repo/IntegrityRepository.key256 : Ljava/lang/String;
	 INVOKEVIRTUAL com/kevlar/showcase/util/EncryptionUtil.generateKey (Ljava/lang/String;)Ljavax/crypto/SecretKey;
	 INVOKEVIRTUAL com/kevlar/showcase/util/EncryptionUtil.decrypt ([BLjavax/crypto/SecretKey;)Ljava/lang/String;
	L34
     INVOKESPECIAL com/kevlar/integrity/model/HardcodedMetadata.<init> (Ljava/lang/String;Ljava/lang/String;)V
     PUTFIELD com/kevlar/showcase/data/repo/IntegrityRepository.aes256EncryptedHardcodedMetadata : Lcom/kevlar/integrity/model/HardcodedMetadata;
	```

#### Hashing
This hasn't been developed yet, but it may be possible to let kevlar know only the hash of your hardcoded data, and let it match directly on the runtime signatures and package names hashes. 
This would require multiple options of composite hash functions to be secure enough. It is not implemented.

## Initialization & Attestations

You need to create a `KevlarIntegrity` instance (which is the way you will be requesting attestations), along with your desired parameters (either global, local or in your repository layer, if you are using MVVM/MVC).

Once you have that, you just go ahead and call `integrity.attestate()` in a coroutine and your application running metadata will be checked, according to the provided parameters.

`IntegrityAttestation` will be returned from the call (it's a sealed class), containing the checks which failed, if any.

Note that we will be initializing `KevlarIntegrity` with custom scan settings, but you could leave it as default.

## In-Place
This is the most concise (and complete) way to implement this package.

```kotlin title="InPlace.kt"
// com.kevlar.showcase
val base64PackageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray(Charsets.UTF_8)

// J+nqXLfuIO8B2AmhkMYHGE4jDyw=
val base64Signature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray(Charsets.UTF_8)	


val base64ObfuscatedHardcodedMetadata = HardcodedMetadata(
    Base64.decode(base64PackageName, Base64.DEFAULT).toString(Charsets.UTF_8),
    Base64.decode(base64Signature, Base64.DEFAULT).toString(Charsets.UTF_8)
)

val integrity = KevlarIntegrity(base64ObfuscatedHardcodedMetadata) {
    checks {
        signature()
        packageName()
        installer()
        debug()
    }
}

CoroutineScope(Dispatchers.Default).launch {
	// Attestation request
    when (val attestation = integrity.attestate(context)) {
        is IntegrityAttestation.Blank -> {
            // Pending attestation, no information yet. 
        	// Don't do anything.
        }
        is IntegrityAttestation.Clear -> {
            // Good to go.
        }
        is IntegrityAttestation.Failed -> {
            // One or more checks have failed.
        }
    }
}
```

This packs everything in one file. It is not excellent when writing a modern applications but it does its job.

## ViewModel + Repository + SharedFlow + DI with Hilt

#### Activity:
```kotlin title="IntegrityActivity.kt"
@AndroidEntryPoint
class IntegrityActivity : AppCompatActivity() {

    private val vm: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
	    
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.attestation.collectLatest {
                    when (it) {
                        is IntegrityAttestation.Blank -> {
                            // Pending attestation, no information yet.
                            // Don't do anything.
                        }
                        is IntegrityAttestation.Clear -> {
                            // Good to go.
                        }
                        is IntegrityAttestation.Failed -> {
                            // Pirate software detected.
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
```

#### View model:
```kotlin title="ActivityViewModel.kt"
@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val integrityRepository: IntegrityRepository
) : ViewModel() {
    private val _attestation = MutableStateFlow(KevlarIntegrity.blankAttestation())

    internal val attestation: SharedFlow<IntegrityAttestation> = _attestation.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = KevlarIntegrity.blankAttestation()
    )

    fun requestAttestation() {
        viewModelScope.launch {
            _attestation.value = integrityRepository.attestate()
        }
    }
}

```

#### Repository
```kotlin title="IntegrityRepository.kt"
class IntegrityRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
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
```
