# Reference

The complete integrity configuration is as follows.

```kotlin title="Complete settings"
private val integrity = KevlarIntegrity {
    checks {
        packageName() {
            // Allowed package name
            hardcodedPackageName("com.kevlar.showcase")
        }
        signature() {
            // Allowed signature
            hardcodedSignatures("J+nqXLfuIO8B2AmhkMYHGE4jDyw=")
        }
        
        debug()
        installer()
    }
}
```

Once you require the attestation through `attestate(context)`, any integrity mismatch that you requested 
will be included in the attestation.

```kotlin
withContext(externalDispatcher) {
    val attestation = integrity.attestate(context)
}
```

## Package name check
The `packageName()` function tells kevlar to enable the integrity checks for the application package name.

This is a parametric setting, since kevlar needs to know what is the "right" application package name is.
Once kevlar has all the required data it is able to differentiate between genuine and tampered binaries.

```kotlin hl_lines="3-6"
private val integrity = KevlarIntegrity {
    checks {
        packageName() {
            // Allowed package name
            hardcodedPackageName("com.kevlar.showcase")
        }
        signature() {
            // Allowed signature
            hardcodedSignatures("J+nqXLfuIO8B2AmhkMYHGE4jDyw=")
        }
        
        debug()
        installer()
    }
}
```

You can find instruction on where to find the right parameters in [implementation](implementation.md).
In this case you simply have to pass in the package name of your app, so kevlar knows what is the right package.


## Signature check
The `signature()` function tells kevlar to enable the integrity checks for the application signature.

This is a parametric setting, since kevlar needs to know what is the "right" application signature is.
Once kevlar has all the required data it is able to differentiate between genuine and tampered binaries.

```kotlin hl_lines="7-10"
private val integrity = KevlarIntegrity {
    checks {
        packageName() {
            // Allowed package name
            hardcodedPackageName("com.kevlar.showcase")
        }
        signature() {
            // Allowed signature
            hardcodedSignatures("J+nqXLfuIO8B2AmhkMYHGE4jDyw=")
        }
        
        debug()
        installer()
    }
}
```

You can find instruction on where to find the right parameters in [implementation](implementation.md).



## Debug check
The `debug()` function tells kevlar to enable integrity debug checks.

```kotlin hl_lines="12"
private val integrity = KevlarIntegrity {
    checks {
        packageName() {
            // Allowed package name
            hardcodedPackageName("com.kevlar.showcase")
        }
        signature() {
            // Allowed signature
            hardcodedSignatures("J+nqXLfuIO8B2AmhkMYHGE4jDyw=")
        }
        
        debug()
        installer()
    }
}
```

If debug flags are found on your application it will be reported.


## Installer check
The `installer()` function tells kevlar to enable installer checks.

Since android R, google introduced APIs to check the original installer of a certain package.
With this check, you can instruct kevlar to analyze that installer (if available) and detect
whether it is allowed or not by your security policy.

In this case, the only allowed installer package is the Google Play Store, but you can always
add more through the `allowInstaller` function.

```kotlin hl_lines="13-15"
private val integrity = KevlarIntegrity {
    checks {
        packageName() {
            // Allowed package name
            hardcodedPackageName("com.kevlar.showcase")
        }
        signature() {
            // Allowed signature
            hardcodedSignatures("J+nqXLfuIO8B2AmhkMYHGE4jDyw=")
        }
        
        debug()
        installer() {
            allowInstaller("com.sec.android.app.samsungapps")
        }
    }
}
```

