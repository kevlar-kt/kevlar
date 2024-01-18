# Rooting

``` mermaid
graph LR
  I[Inizialization] -.Settings..-> K{KevlarRooting};
  DB([Android OS]) === K
  AR1[Target Attestation Requests] --> K
  AR2[Status Attestation Requests] --> K
  K --> |Clear| P[Passed];
  K --> |Failed| NP[Not Passed];
  P --> A1[TargetAssestation]
  NP --> A1
  P --> A2[StatusAttestation]
  NP --> A2
```

The rooting package contains tools for the detection of system modifications that may be active on the device running your app.

Once configured with settings, the package can quickly run different batteries of tests on the operative system (through shell commands) to check if the target modifications are present on the device.

It produces two different kinds of attestations: one searching for system modification and one for system conditions.

It is capable of detecting the following system modifications (through the `targets` attestation)

- Root access;
- Magisk installations (not hidden);
- Busybox binaries;
- Toybox binaries;
- Xposed framework.

And the following system conditions (through the `status` attestation)

- Emulator execution;
- Test keys;
- SELinux status.


Depending on what you need to check, you may choose one or run both.

!!! question "Purpose of the `rooting` package"
    You may want to use this package if you care about the system-wide security status, or if you consider that your application running on rooted/modified devices is a security risk.

!!! summary "Notation"

    In the rooting package, the words "target" and "status" are used loosely, but they actually have a special meaning.
    `Targets` means system modification. Something that may be installed (and detectable) over the operating system. It is a kind of add-on, a custom software component.
    `Status` means a system condition. Something that is itself part of the operating system out of the box, and whose status we want to check.

To [implement](implementation.md) this, you initialize `KevlarRooting` and provide your desired settings (which influence what is to be checked and what not). Then you can submit attestation requests of whichever kind you prefer (which will be executed according to your settings).








## Attestation process overview
This package can produce two different kinds of attestations: a `TargetAssestation` and a `StatusAttestation`.

When you require an attestation, kevlar performs the following operations:

- for the **targets attestation** (through `rooting.attestateTargets(context)`):
    
    1. Depending on what system modification you selected, the appropriate battery of tests for that system modification is initialized and run;
    2. The results are collected, processed, filtered, and returned.

- for the **status attestation** (through `rooting.attestateStatus()`):
    
    1. Depending on what system condition you selected, the appropriate check for that system status flag is initialized and ran;
    2. The results are collected, processed, filtered, and returned.


The attestation is returned either in `TargetRootingAttestation` or `StatusRootingAttestation` (both are sealed classes), which depending on the detection status can be of three types (with different fields):

- `Blank`: This is a non-processed status. It should not be interpreted, as it does not carry any meaning about the attestation result. It is not to be interpreted as `Clear`;
- `Clear`: The attestation has passed. There is nothing to report. This means that no system modification/status has triggered the detection from the battery of tests that have been executed, in compliance with the given scan parameters;
- `Failed`: The attestation has not passed. A target/status has been detected. You can read which one has tripped the detection in the attestation result.


!!! warning
    `Blank` is completely different from `Clear` (or `Failed`). It means that the software is initialized but that nothing has been done yet. Do not mix them up.



## Configuring `KevlarRooting`
Configuring the rooting module requires a bit of android technical knowledge, since you have to be aware of which items you are enabling.

You may choose to configure the rooting module manually (using the dedicated DSL, more flexibility), or just grabbing one of the default configurations (which are the most commonly used and don't need DSL configuration)

```kotlin title="Manual DSL Configuration"
private val rooting = KevlarRooting {
    targets {
        root()
        magisk()
        busybox()
    }

    status {
        emulator()
        selinux {
            flagPermissive()
        }
    }

    allowExplicitRootCheck()
}
```


```kotlin title="Default Configuration"
private val rooting = KevlarRooting.Defaults.Standard()
```

You can find more information about each individual item in the [reference](reference.md) page.


The settings are additive. If you leave a blank DSL, nothing will be detected, because no checks will be run, because the settings are empty.

```kotlin title="Empty"
private val rooting = KevlarRooting {
    targets {}
    status {}
}
```

```kotlin title="Default"
private val rooting = KevlarRooting()
```










## Use cases
This is a pretty typical scenario for any banking/financial application, game, or software managing sensitive resources (files, records, data)