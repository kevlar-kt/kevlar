# Implementation

Implementing `integrity` requires, on top of a `KevlarAntipiracy` and the attestation
infrastructure, a bit of information about your application's metadata, which will be hardcoded
inside it.

Hardcoded data is necessary to provide kevlar a **truth value** (essentially, that should be the
description of all the scenarios that the application is allowed to run in) to match the runtime
values of your binary (which may have been tampered or altered by an attacker) against.

That's because, if done well, we can detect almost every kind of attack/tampering attempt through
checking various APK metadata, such as the package name, the signature, the installer and debug
flags.

The obfuscation is necessary because we need to conceal the truth values, since they will be looked
for by the attacker (software or human), and make it as hard as possible to automatically find and
patch them.

A working example for the integrity module can be found in the github repository under
the `:showcase` module.

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

### Understanding which metadata you need

The first step is choosing which checks to run, and thus which data to provide kevlar.

The good news is that they all are stable metadata. This means that once you make the effort of
searching the right strings and implementing everything, you are ideally good to go forever.

Kevlar has 4 kinds of checks: a package name check, a signature check, an installer check and a
debug check.

| Check Type                  | Required parameters | Parameter type            | 
|-----------------------------|---------------------|---------------------------|
| Package Name                | package name        | `String`                  |
| Signature Check             | app signature       | `String` (base64-encoded) |
| Installer Check             | N/A                 | N/A                       |
| Debug Check                 | N/A                 | N/A                       |

The hardcoded metadata you need to find is the following:

- Your application **package name**: Will check that the running binary's package name matches the
  hardcoded package name;
- Your application **signature**: Will check that the running binary's signature is the same as the
  hardcoded signature.

Additionally, you should consider enabling the other two kinds of checks:

- **Debug checks**: Is enabled, kevlar autonomously checks for debug flags and bits in your binary;
- **Installer checks**: If enabled, kevlar will report any application which has not been installed
  through an allowed installer (by default it only supports the Play Store, but you can add custom
  stores if you distribute your software elsewhere).

Once you choose which kind of checks you want to run, you get the required metadata

```kotlin title="hardcoded_metadata.kt"
// Holds the package name
private val packageNameData = HardcodedPackageName(
    packageName = "com.kevlar.showcase"
)

// Holds the signature
private val signatureData = HardcodedBase64EncodedSignature(
    base64EncodedSignature = "J+nqXLfuIO8B2AmhkMYHGE4jDyw="
)

// Combines all the metadata and configuration
private val integrity = KevlarIntegrity {
    checks {
        packageName {
            hardcodedPackageName(packageNameData)
        }
        
        signature {
            hardcodedSignatures(signatureData)
        }

        installer()
        debug()
    }
}

// Runs the checks on the current executing app
integrity.attestate(context)
```


!!! fail "Do not"
    Maybe, just maybe, you may be tempted to so something like this:

	```kotlin title="VERY_BAD_hardcoded_metadata.kt"
	private val notAtAllHardcodedMetadata = HardcodedMetadata(
	    packageName = context.getPackageName(),
	    signature = getRuntimeSignature()
	)
	```

	The sole purpose of `HardcodedMetadata` is **[hardcoding](https://en.wikipedia.org/wiki/Hard_coding)** truth values inside your app, which don't depend on the context or application, **which may have been tampered with**. This snippet single handedly kills the whole library (because kevlar will check that the (supposedly) hardcoded package name, in this case `context.getPackageName()`, matches the runtime package name, which is always true since it is gathered via `context.getPackageName()` too) and is like shooting yourself in the foot with a cannon. Don't. 



### Finding metadata
Finding the package name is easy, since you are the one choosing it for your application.

For the signature, it's not so straightforward to extract because it depends on your keystore.
You have two different ways to get your keystore signature. In the examples we will find the debug signature, but you need to find the signature of the keystore you use to sign your application when publishing on google play.

#### Direct application extraction
The most practical way to read your keystore it is to put the following line of code in your app, to then sign the application with the key you are interested in acquiring the signature string of, and run it.

```kotlin
// This returns the signature of the current running application.
val signature: String = KevlarIntegrity.obtainCurrentAppSignature(context)
```

This will output the current app signature. 
That's the reference string you need to give to kevlar (which will extract the runtime signature of your app and match it against that string).

!!! example "Android debug signature"
    Every android application is signed with some key. 
    When an application is signed as "debug", it simply means that it is signed with a special key, which is known to be the debug key.
    
    That key has always signature `J+nqXLfuIO8B2AmhkMYHGE4jDyw=`


!!! warning "Signature extraction & Google Play App Signing API"
    If you are using Google Play App Signing, the key you sign your application with is not the one your app is distributed with (See the [official docs](https://developer.android.com/studio/publish/app-signing) regarding the matter, and a relevant [issue](https://github.com/kevlar-kt/kevlar/issues/1) in kevlar).
    
    In this case the easiest way to get your actual signature would be to upload a dummy version of your app (which logs the runtime signature) through google play store, let the backend process and sign it, download it (through the archive manager on the play console), install & run it locally on an emulator/device, and save the runtime signature. Once you have done this (quite tedious) procedure, you have your signature and can pass it to kevlar.

#### Android studio extraction
Running `./gradlew signingReport` will spit out all the details for all the different keystores in your project.

The signature we are interested in is the SHA-1 entry. In this case, `27:E9:EA:5C:B7:EE:20:EF:01:D8:09:A1:90:C6:07:18:4E:23:0F:2C`.

``` hl_lines="7"
> Task :showcase:signingReport
Variant: debug
Config: debug
Store: /Users/cioccarellia/.android/debug.keystore
Alias: AndroidDebugKey
MD5: 1B:AF:39:46:4E:13:83:F3:45:E9:0A:5A:53:64:9C:CB
SHA1: 27:E9:EA:5C:B7:EE:20:EF:01:D8:09:A1:90:C6:07:18:4E:23:0F:2C
SHA-256: 36:C8:C0:A1:8A:DD:6D:0E:34:F9:6E:7E:98:DC:1F:89:08:BC:CD:2E:EF:88:ED:45:DF:79:85:D2:39:BD:E1:54
Valid until: Tuesday, June 25, 2052
----------
Variant: release
Config: null
Store: null
Alias: null
----------
```

We then have to convert it in a string form (like that we have the raw hex bytes, we want a base64 encoding of the binary signature).
In this case the conversion (you can use online tools to do this) yields `J+nqXLfuIO8B2AmhkMYHGE4jDyw=`.

!!! fail "Play Signing"
    Since we don't have access to the keystore file if we use Play Signing, this method is not viable in that case, and you have to resort to uploading a dummy version of the app to have it signed and spit out the signature.


## Obfuscating metadata
The second step (optional but recommended) is obfuscating the metadata you just gathered, so that it
is **saved** in an obfuscated form (in your bytecode, so that automatic tools / unskilled attackers can't easily find it), but passed to kevlar deobfuscated (so that we have the original truth values at run time).

This means that we ship with out app the obfuscated data and the way to convert that obfuscated data back to plaintext to feed kevlar.

There are a few different ways to do it, all of them are fully implemented in the `:showcase` module:

### No obfuscation (not recommended)
In this case you just save the values as they are, and pass them in `HardcodedMetadata`

```kotlin title="unobfuscated_hardcoded_metadata.kt"
private const val packageName = HardcodedPackageName("com.kevlar.showcase")
private const val signature = HardcodedBase64EncodedSignature("J+nqXLfuIO8B2AmhkMYHGE4jDyw=")
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

### Base64 obfuscation

You store the package name and signature values as Base64-encoded byte arrays, and they go through
the `Base64.decode()` function when creating `HardcodedMetadata`.

```kotlin title="base64_obfuscated_hardcoded_metadata.kt"
private val packageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray()
private val signature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray()


private val base64ObfuscatedHardcodedPackageName = HardcodedPackageName(
    packageName = Base64.decode(base64PackageName, Base64.DEFAULT).toString(Charsets.UTF_8)
)

private val base64ObfuscatedHardcodedSignature = HardcodedBase64EncodedSignature(
    Base64.decode(base64Signature, Base64.DEFAULT).toString(Charsets.UTF_8)
)
```

Where `Y29tLmtldmxhci5zaG93Y2FzZQ==` is the base64 encoding of `com.kevlar.showcase`,
and `SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==` of `J+nqXLfuIO8B2AmhkMYHGE4jDyw=` (the signature).

You can look them up [online](https://www.base64encode.org), grab them from your app or
use `openssl base64` in the terminal.

!!! info "Base64 flags & charset"
    The flag field and charset don't necessarily need to be `Base64.DEFAULT` and `UTF_8`. Even though
    they are the most popular, you may choose something else if you prefer, as long as you preserve
    consistency.

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

### Encryption (+base64)

A better alternative is to encrypt the hardcoded metadata, store them in an encrypted form, and send
them through a decryption function when creating `HardcodedMetadata`.

```kotlin title="encrypted_hardcoded_metadata.kt"
// Our arbitrary 256-bit encryption key
private val aesKey256 = """4t7w!z%C*F-JaNcRfUjXn2r5u8x/A?Ds"""

// This is "com.kevlar.showcase", encrypted using AES256 with the previous key
private val encryptedPackageName = """s3wf/AOYtr9BEMVFrweeLnkmerryUykMA8O77S5tMlI=""".toByteArray()

// This is "J+nqXLfuIO8B2AmhkMYHGE4jDyw=", encrypted using AES256 with the previous key
private val encryptedSignature = """tqMJquO3D+EKx1rx4R7/qzmsuEgpp1bKwxXe9AeB/WU=""".toByteArray()


private val aes256EncryptedHardcodedPackageName = HardcodedPackageName(
    packageName = EncryptionUtil.decrypt(
        encryptedPackageName,
        EncryptionUtil.generateKey(aesKey256)
    )
)

private val aes256EncryptedHardcodedSignatures = HardcodedBase64EncodedSignature(
    EncryptionUtil.decrypt(encryptedSignature, EncryptionUtil.generateKey(aesKey256))
)
```

Where `7KAa2CFkhPQOUouDu32KZJLqOzGFbTTnJA3rGxMlAg4=` is the encrypted value of `com.kevlar.showcase`, and `+ylMx63kwFRmXKHQU0cbzyb8MJ1iiGW1g8+MjDRcS/o=` of `J+nqXLfuIO8B2AmhkMYHGE4jDyw=`.

This ensures that there is no possibility that an automatic attack picks up the string as a package
name or signature, and trivial string substitutions or encodings like base64 won't give any
information away (the ciphertext is encoded in base64).

AES128 or AES256 is recommended as the encryption algorithm (it's a little overkill but it does the
job).

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

### Hashing

This hasn't been developed yet, but it may be possible to let kevlar know only the hash of your
hardcoded data, and let it match directly on the runtime signatures and package names hashes. This
would require multiple options of composite hash functions to be secure enough. It is not
implemented.

## Initialization & Attestations

You need to create a `KevlarIntegrity` instance (which is the way you will be requesting
attestations), along with your desired parameters (either global, local or in your repository layer,
if you are using MVVM/MVC).

Once you have that, you just go ahead and call `integrity.attestate()` in a coroutine and your
application running metadata will be checked, according to the provided parameters.

`IntegrityAttestation` will be returned from the call (it's a sealed class), containing the checks
which failed, if any.

Note that we will be initializing `KevlarIntegrity` with custom scan settings, but you could leave
it as default.

## In-Place
This is the most concise (and complete) way to implement this package.

```kotlin title="InPlace.kt"
/**
 * Base64 obfuscated package name and signature
 * */
// Original value is "com.kevlar.showcase", the package name hardcoded value
private val base64EncodedPackageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray()

private val base64ObfuscatedHardcodedPackageName = HardcodedPackageName(
    packageName = Base64.decode(base64EncodedPackageName, Base64.DEFAULT).toString(Charsets.UTF_8)
)



// Original value is "J+nqXLfuIO8B2AmhkMYHGE4jDyw=", the signature hardcoded value
private val base64EncodedSignature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray()

private val base64ObfuscatedHardcodedSignatures = HardcodedBase64EncodedSignature(
    Base64.decode(base64EncodedSignature, Base64.DEFAULT).toString(Charsets.UTF_8)
)


/**
 * Integrity package
 * */
private val integrity = KevlarIntegrity {
    checks {
        packageName {
            hardcodedPackageName(base64ObfuscatedHardcodedPackageName)
        }

        signature {
            hardcodedSignatures(base64ObfuscatedHardcodedSignatures)
        }

        installer()
        debug()
    }
}




/**
 * Assestation request & callback
 * +/
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

This packs everything in one file. It is not excellent when writing a modern applications but it
does its job.

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
     * Base64 obfuscated package name
     * */
    private val base64PackageName = """Y29tLmtldmxhci5zaG93Y2FzZQ==""".toByteArray()
    private val base64Signature = """SitucVhMZnVJTzhCMkFtaGtNWUhHRTRqRHl3PQ==""".toByteArray()


    private val base64ObfuscatedHardcodedPackageName = HardcodedPackageName(
        packageName = Base64.decode(base64PackageName, Base64.DEFAULT).toString(Charsets.UTF_8)
    )

    private val base64ObfuscatedHardcodedSignatures = HardcodedBase64EncodedSignature(
        Base64.decode(base64Signature, Base64.DEFAULT).toString(Charsets.UTF_8)
    )


    /**
     * Integrity package
     * */
    private val integrity = KevlarIntegrity {
        checks {
            packageName {
                hardcodedPackageName(base64ObfuscatedHardcodedPackageName)
            }

            signature {
                hardcodedSignatures(base64ObfuscatedHardcodedSignatures)
            }

            installer()
            debug()
        }
    }

    suspend fun attestate(): IntegrityAttestation = withContext(externalDispatcher) {
        integrity.attestate(context)
    }
}
```
