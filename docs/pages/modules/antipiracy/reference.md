# Reference

The complete rooting configuration is as follows.

```kotlin title="Complete settings"
private val antipiracy = KevlarAntipiracy {
    scan {
        pirate()
        store()
        collateral()
    }
}
```

Once you require the attestation through `attestate(context)`, any software matching your settings
will be reported.

```kotlin
withContext(externalDispatcher) {
    val attestation = antipiracy.attestate(context)
}
```

You can find all the details about what software is regarded as pirate in the [database](database.md) page.


## Pirate Apps 
The `pirate()` function tells kevlar to scan the application list, and match it against the local
dataset for pirate applications.

If any package is deemed to be a pirate application, it is included in the attestation

```kotlin hl_lines="3"
private val antipiracy = KevlarAntipiracy {
    scan {
        pirate()
        store()
        collateral()
    }
}
```



## Pirate Stores
The `store()` function tells kevlar to scan the application list, and match it against the local
dataset for pirate stores.

```kotlin hl_lines="4"
private val antipiracy = KevlarAntipiracy {
    scan {
        pirate()
        store()
        collateral()
    }
}
```


## Collateral detection methods
The `collateral()` function enables kevlar to use more aggressive methods to perform the lookup of
specific pirate software.

```kotlin hl_lines="5"
private val antipiracy = KevlarAntipiracy {
    scan {
        pirate()
        store()
        collateral()
    }
}
```

This is made a separate flag because those special methods have a non-zero false-positive probability, 
and therefore are risky to use because you may (albeit extremely rarely) end up in situations where an unrelated
software (usually russian) may be detected as pirate software. Since we want you to have full control over 
your scan settings, we extrapolated this flag to make the switch explicit.

More details in the [detection](detection.md#collateral-tools) page.

