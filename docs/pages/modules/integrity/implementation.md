# Implementation

Implementing `integrity` requires, on top of a `KevlarAntipiracy` and the attestation infrastructure, a tiny bit of information about your application's metadata, which will be hardcoded inside it.

This is necessary to provide kevlar a truth value to match the runtime values (which may have been tampered and altered) against.
The obfuscation is necessary because we need to conceal the truth values, since they will be looked for by the attacker (software or human), and make it as hard as possible to automatically find and patch them.

## Hardcoded metadata
The first step is getting the necessary metadata. You need the following:

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

	Refrain from coding this monstruosity. The sole purpose of `HardcodedMetadata` is **[hardcoding](https://en.wikipedia.org/wiki/Hard_coding)** truth values inside your app. This little code single handedly kills the whole library (because obviously the runtime package name will match the runtime package name) and is like shooting yourself in the foot with a cannon. Don't. 


### Obfuscating metadata
The second (optional but recommended) step is obfuscating the metadata you just read, so that it is **saved** in an obfuscated form (in your bytecode, so that automatic tools can't find it), but passed to kevlar deobfuscated (so that we have the original truth values).

There are a few ways to do it

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
	The flag field and charset don't necessarily need to be `Base64.DEFAULT` and `UTF_8`. Even though they are the most popular, you may choose something else if you prefer.

??? bug "Bytecode"

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

private val packageName = """7KAa2CFkhPQOUouDu32KZJLqOzGFbTTnJA3rGxMlAg4="""
private val signature = """+ylMx63kwFRmXKHQU0cbzyb8MJ1iiGW1g8+MjDRcS/o="""

private val encryptedHardcodedMetadata = HardcodedMetadata(
	decrypt(packageName, key), decrypt(signature, key)
)
```

Where `7KAa2CFkhPQOUouDu32KZJLqOzGFbTTnJA3rGxMlAg4=` is the encrypted value of `com.kevlar.showcase`, and `+ylMx63kwFRmXKHQU0cbzyb8MJ1iiGW1g8+MjDRcS/o=` of `J+nqXLfuIO8B2AmhkMYHGE4jDyw=`.

This ensures that there is no possibility that an automatic attack picks up the string as a package name or signature, and trivial string substitutions or encodings like base64 won't give any information away (the ciphertext is encoded in base64).

AES128 or AES256 is recommended as the encryption algorithm (it's a little overkill but it does the job).

The ciphertext may also be stored as a byte array.

#### Hashing
This hasn't been developed yet, but it may be possible to let kevlar know only the hash of your hardcoded data, and let it match directly on the runtime signatures and package names hashes. 
This would require multiple options of composite hash functions to be secure enough. It is not implemented.

## Initialization & Attestations

