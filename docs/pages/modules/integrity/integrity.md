# Integrity

``` mermaid
graph LR
  I[Inizialization] -.Settings..-> K{KevlarIntegrity};
  DB([Hardcoded & Obfuscated Metadata]) === K
  AR1[Attestation Requests] --> K
  K --> |Clear| P[Passed];
  K --> |Failed| NP[Not Passed];
  P --> A[IntegrityAssestation]
  NP --> A
```

The integrity package contains tools for the detection of tampering attempts against your app.

It requires a bit of effort to implement (you need to find, code and obfuscate your app metadata, all the instructions are in [implementation](implementation.md)), but once you have it runs quickly and works extremely well.

It is capable of detecting:

- Signature mismatches (the running app's signature and the hardcoded signature are different)
- Package name mismatches (the running app's package name and the hardcoded package name are different)
- Debuggable flag (the running app is debuggable, which should never happen for production apps)
- Disallowed Installers (the running app has been installed through a disallowed package installer)


!!! question "Purpose of the `integrity` package"
    You want to use this package if you need to give your app a layer of protection against tampering attacks (see [anatomy](../../overview/anatomy_of_attacks.md) for more details).
    Enabling just a few checks and implementing basic obfuscation will make your app harder to crack.


!!! warning Automatic vs Specific attack
    This package is the best defense against automatic and/or unskilled attacks. 
    If implemented well, it will kill off most of them


To [implement](implementation.md) this, you initialize `KevlarIntegrity` and provide your desired settings (which influence what is to be checked and what not). Then you can submit attestation requests (which will be executed according to your settings).

??? note "Empty & default settings"
    The settings on `integrity` are additive. If you leave a blank DSL, nothing will be detected, because no checks will be run, because the settings are empty.

    By default, only `debug` and `installer` checks are enbabled, since you need to provide additional arguments to enable `signature` and `packageName`

    If you do not pass a DSL at all, the default settings will be used (they scan for signature, package name, and debug).

    ```kotlin title="Custom (simplified)"
    private val integrity = KevlarIntegrity {
        checks {
            signature() {
                // Allowed package name
                hardcodedPackageName("com.kevlar.showcase")
            }
            packageName() {
                // Allowed signature
                hardcodedSignatures("J+nqXLfuIO8B2AmhkMYHGE4jDyw=")
            }
            debug()
            installer()
        }
    }
    ```

    ```kotlin title="Empty"
    private val integrity = KevlarIntegrity {
        checks {

        }
    }
    ```

    ```kotlin title="Default"
    private val integrity = KevlarIntegrity()
    ```


## Attestation process overview
When you require an attestation (through `integrity.attestate(context)`), kevlar executes the following operations:

1. Depending on what integrity checks you selected, the appropriate battery of tests for those targets is initialized and run;
2. The results are collected, processed, filtered, and returned.

There is only one type of attestation that can be produced.

The attestation is returned in `IntegrityAttestation` (it is a sealed class), which depending on the detection status can be of three types:

- `Blank`: This is a non-processed status. It should not be interpreted, as it does not carry any meaning about the attestation result. It is not to be interpreted as `Clear`;
- `Clear`: The attestation has passed. There is nothing to report. This means that no system modification/status has triggered the detection from the battery of tests that have been executed, in compliance with the given check parameters;
- `Failed`: The attestation has not passed. Integrity or tampering issues have been detected. You can check which check has failed (which inconsistency has been found between the hardcoded and runtime values) in the attestation result.

!!! warning
    `Blank` is completely different from `Clear` (or `Failed`). It means that the software is initialized but that nothing has been done yet. Do not mix them up.

## Use cases
This is a pretty typical scenario for any application where it is critical to preserve self-integrity and run unmodified code.