# Reference

The complete rooting configuration is as follows.

```kotlin title="Complete Rooting settings"
private val rooting = KevlarRooting {
    targets {
        root()
        magisk()
        busybox()
        xposed()
    }

    status {
        testKeys()
        emulator()
        selinux {
            flagPermissive()
        }
    }

    allowExplicitRootCheck()
}
```


!!! warning
	Bear in mind, this kind of configuration is exhaustive and should be used just in a few cases where you *really* need to detect all of those conditions.
	Only including what your application's security environment requires is a key step in properly configuring the library.


Unlike other Kevlar modules, here you can actually require two different types of attestation: you have `attestateTargets` and `attestateRooting`.
Once you require the attestation through any of those two methods, any discrepancies between your expected configuration (w.r.t. the invoked attestation type) and the actual device status will be reported back to you.

```kotlin
withContext(externalDispatcher) {
    val targetAttestation = rooting.attestateTargets(context)
    val statusAttestation = rooting.attestateStatus()
}
```


