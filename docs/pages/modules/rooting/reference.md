# Reference

## Configurations

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
	Only including what your application's security environment requires is a key step in properly configuring the library and having an efficient detection mechanism.


You can also use the pre-configured scan settings if your configuration is common:

```kotlin title="Automatic settings"
private val antipiracy = KevlarRooting.Defaults.Standard()
```

The defaults configurations are:

- `KevlarRooting.Defaults.Standard`: Complete scan, no explicit root access request:
	- Targets: `root()`, `magisk()`;
	- Status: `emulator()`, `testKeys()` and standard `selinux()`.
- `KevlarRooting.Defaults.JustRooting`: Only targets `root()` and `magisk()`, no explicit root access request;
- `KevlarRooting.Defaults.JustRootingExplicit`: Only targets `root()` and `magisk()`, with explicit root access request;
- `KevlarRooting.Defaults.JustEmulator`: Only `emulator()` and `testKeys()`;
- `KevlarRooting.Defaults.Empty`: No scan;


## Attestation types

Unlike other Kevlar modules, here you can actually request two different types of attestation: you have `attestateTargets` and `attestateRooting`.
Once you require the attestation through any of those two methods, any discrepancies between your expected configuration (w.r.t. the invoked attestation type) and the actual device status will be reported back to you.

```kotlin
withContext(externalDispatcher) {
    val targetAttestation: TargetRootingAttestation = rooting.attestateTargets(context)
    val statusAttestation: StatusRootingAttestation = rooting.attestateStatus()
}
```

This is done because the checks are completely independent, and so they can be run independently (basing on your needs), or together and in parallel.

They return their own attestation, each containing the security environment discrepancies it was instructed to scan for. (e.g. root access will be included in `TargetRootingAttestation`, while emulator will be in `StatusRootingAttestation`; granted they have been included in the kevlar configuration and they appear in the runtime systems)

??? example "Asynchronous scans"
	```kotlin
	withContext(externalDispatcher) {
	    val targetAttestation = rooting.attestateTargets(context)
	}

	withContext(externalDispatcher) {
		val statusAttestation = rooting.attestateStatus()
	}
	```


## Reference
Here is a list of all the flags and their specification:

### Targets

All of the following flags constitute the targets configuration, which can be requested through `rooting.attestateTargets(context)`, returning a `TargetRootingAttestation`, which can be either `Clear`, `Blank`, `Failed`. In the latter, you have access to the list of targets that you specified in your configuration and that have actually been detected on the host system.

#### Root Access

Root access checks are enabled through the following flag:

```kotlin title="Complete Rooting settings" hl_lines="3"
private val rooting = KevlarRooting {
    targets {
        root()
    }
}
```

This will run a series of checks to determine whether the `su` binary is present on the host system, without calling `su` directly (as that would imply that your application actively asks for root access as a form of detection, which is a very aggressive technique)

If you need to do so, you can enable this additional check by telling kevlar it is allowed to invoke the `su` binary directly, via `allowExplicitRootCheck()`.

```kotlin title="Complete Rooting settings" hl_lines="6"
private val rooting = KevlarRooting {
    targets {
        root()
    }
	
    allowExplicitRootCheck()
}
```


#### Magisk Installation

Magisk checks are enabled through the following flag:

```kotlin title="Complete Rooting settings" hl_lines="3"
private val rooting = KevlarRooting {
    targets {
        magisk()
    }
}
```

This will run a series of checks to determine whether [Magisk](https://github.com/topjohnwu/Magisk) is installed the host system.



#### Busybox

Busybox checks are enabled through the following flag:

```kotlin title="Complete Rooting settings" hl_lines="3"
private val rooting = KevlarRooting {
    targets {
        busybox()
    }
}
```

This will run a series of checks to determine whether any busybox custom binaries are installed in the host system.



#### Xposed

Xposed framework checks are enabled through the following flag:

```kotlin title="Complete Rooting settings" hl_lines="3"
private val rooting = KevlarRooting {
    targets {
        xposed()
    }
}
```


### Status

All of the following flags constitute the status configuration, which can be requested through `rooting.attestateStatus()`, returning a `StatusRootingAttestation`, which can be either `Clear`, `Blank`, `Failed`. In the latter, you have access to the list of statuses that you specified in your configuration and that have actually been detected on the host system.

#### Emulator

Emulation checks are enabled through the following flag:

```kotlin title="Complete Rooting settings" hl_lines="3"
private val rooting = KevlarRooting {
    status {
        emulator()
    }
}
```


#### Emulator

Test keys checks are enabled through the following flag:

```kotlin title="Complete Rooting settings"  hl_lines="3"
private val rooting = KevlarRooting {
    status {
        testKeys()
    }
}
```







#### SELinux

SELinux checks are enabled through the following flag:

```kotlin title="Complete Rooting settings"  hl_lines="3"
private val rooting = KevlarRooting {
    status {
        selinux()
    }
}
```

SELinux status can be in one of the following states:
- `disabled`
- `permissive`
- `enforcing`

The default state should be `enforcing`.

- If it is detected as `disabled`, it will be reported in the attestation.
- If it is detected as `permissive`, it will be reported in the attestation if the `flagPermissive()` modifier has been added:


```kotlin title="Complete Rooting settings"  hl_lines="4"
private val rooting = KevlarRooting {
    status {
        selinux {
            flagPermissive()
        }
    }
}
```




