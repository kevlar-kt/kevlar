# Reference

This document contains the complete reference for every scan parameter that can be enabled in the `antipiracy` module.

## Configurations

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

You can use the pre-configured scan settings if your configuration is trivial:

```kotlin title="Automatic settings"
private val antipiracy = KevlarAntipiracy.Defaults.JustPirateApps()
```

The possible configurations are:

- `KevlarAntipiracy.Defaults.Full`: Complete scan: `pirate()`, `store()` and `collateral()`;
- `KevlarAntipiracy.Defaults.JustPirateApps`: Only `pirate()`;
- `KevlarAntipiracy.Defaults.JustStores`: Only `store()`;
- `KevlarAntipiracy.Defaults.PirateAndStore`: Both `pirate()` and `store()`;
- `KevlarAntipiracy.Defaults.Empty`: No scan;

Once you require the attestation through `antipiracy.attestate(context)`, any installed application matching your settings will be reported.

```kotlin
withContext(externalDispatcher) {
    val attestation = antipiracy.attestate(context)
}
```

You can find all the details about what software is regarded as pirate in the [database](database.md) page.


### Pirate Apps 
The `pirate()` function tells kevlar to scan the application list, and match it against the local dataset for pirate applications.

A pirate application is software (installed on the device) that may try to maliciously interfere with the in-app purchase mechanism, or alter your application characteristics in order to induce it to perform a value-related transaction in an unsafe environment, where it can be injected/forged/cracked.

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



### Pirate Stores
The `store()` function tells kevlar to scan the application list, and match it against the local dataset for pirate stores.

A pirate store is a distribution network for android applications other that the Google Play Store.

```kotlin hl_lines="4"
private val antipiracy = KevlarAntipiracy {
    scan {
        pirate()
        store()
        collateral()
    }
}
```


### Collateral detection methods
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


### Manually configuring detection
If you need more advanced selection criteria / customizable scan settings with specific package names, you can use the whitelisting / blacklisting features.


!!! node
	The intersection between the whitelist and the blacklist has to remain empty

#### Whitelisting
The `whitelist()` function signals to kevlar explicit package names that you want to be ignored during the scan.
Kevlar will not run the battery of tests for such packages, and discard them immediately.

```kotlin hl_lines="7"
private val antipiracy = KevlarAntipiracy {
    scan {
        pirate()
        store()
        collateral()
	    
	    whitelist("com.cioccarellia.wordbucket")
    }
}
```


#### Blacklisting
The `blacklist()` function signals to kevlar explicit package names that you want to be detected during the scan.
Kevlar will not run the battery of tests for such packages, and report them immediately as blacklisted.

```kotlin hl_lines="7"
private val antipiracy = KevlarAntipiracy {
    scan {
        pirate()
        store()
        collateral()

        blacklist("com.kevlar.showcase")
    }
}
```

