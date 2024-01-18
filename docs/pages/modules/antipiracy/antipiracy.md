# Antipiracy

``` mermaid
graph LR
  I[Inizialization] -.Settings..-> K{KevlarAntipiracy};
  DB[(Dataset)] === K
  AREQ[Attestation Requests] --> K
  K --> |Clear| P[Passed];
  K --> |Failed| NP[Not Passed];
  P --> ARES[AntipiracyAttestation]
  NP --> ARES
```

The antipiracy package contains tools for the detection of different categories of pirate software that may be installed and running on target devices. 

At its core, this package runs a battery of tests against all the installed applications to check for potential active pirate software, which depending on your policies may be a security environment issue. 
It then compiles the results into an attestation (it can either be uninitialized, clear, or failed) which is returned to your app, where you can check what has been found and act accordingly.

!!! question "Purpose of the `antipiracy` package"
	You may want to use this package if you consider that executing your app alongside pirate software which may be interfering/bypassing your code/protections is a security environment issue.

!!! warning "Tampering and System protection"
	The `antipiracy` package by itself does not do any kind of signature/tampering check (for that, refer to [integrity](../integrity/integrity.md) package) or system wide verification ([rooting](../rooting/rooting.md)).
	It just tells you whether software that may be trying/have already interfered/bypassed your protection mechanisms is installed or not.


To [implement](implementation.md) this, you initialize `KevlarAntipiracy` and provide your desired settings (which influence what is to be detected and what not). Then you can submit attestation requests (which will be executed according to your settings).

Each attestation request will cause Kevlar to grab the package list, run the appropriate checks and return an attestation.

The settings you provide influence what will be included in the attestation.









## Attestation process overview
When you require an attestation (through `antipiracy.attestate(context)`), kevlar executes the following operations:

1. The installed package list is queried from the `PackageManager`. Make sure to [have the right permissions](privacy.md) to do that, since from Android 11 you need to add queries in your app's manifest, see the [implement](implementation.md) page;
2. The test battery is initialized (to match your scan parameters) and ran on all packages, against the precompiled dataset onboard the library;
3. The results are collected, processed, filtered, and returned in an `AntipiracyAttestation`.

There is only one type of attestation that can be produced.

The attestation is returned in `AntipiracyAttestation` (sealed class), which depending on the detection status can be of three types:

- `Blank`: This is a non-processed status. It should not be interpreted, as it does not carry any meaning about the attestation result. It is not to be interpreted as `Clear`;
- `Clear`: The attestation has passed. There is nothing to report. This means that no installed software has triggered the detection from the battery of tests that have been executed, in compliance with the given scan parameters;
- `Failed`: The attestation has not passed. Pirated software has been detected. You can read which software has tripped the detection in the attestation result. The action you were about to do may be compromised, and you should not proceed.

!!! warning
	`Blank` is completely different from `Clear` (or `Failed`). It means that the software is initialized but that nothing has been done yet. Do not mix them up.

## Performance
Package detection and testing make use of coroutines to run scan operations in parallel for each package, to minimize the time taken to run through all packages.

Most of the checks consist of some type of string analysis (characters & regex matching and abstract alphabet inference, more details in [detection](detection.md)), which have pretty decent running times while being precise and reliable tools.

Scan settings are taken into account intelligently to analyze and run the battery of tests over all packages in one single pass, regardless of how many checks are to be performed, and to execute the single package battery test in parallel.

!!! tip "Timing"
	
	The full attestation process takes from start to finish ≈ 75-200ms for my devices and emulators (assuming the full app list is returned, which it won't for Android 11+ [which is actually good news for performance, since the time taken for computing the whole attestation is linearly proportional to the number of applications returned by `PackageManager`]). It is mainly influenced by the processing power of the device, the number of apps installed, and your scan configuration.

	It is decently fast, given that it is intended to be run quickly in the background before business-critical transactions (e.g. when the user clicks "purchase", we first check that the device is clean and then actually contact Google Play to initiate the high-value transaction).








# Configuring `KevlarAntipiracy`
The antipiracy module is easy to configure, since it works automatically: you just have to choose which search criteria is used.

You may choose to configure the antipiracy module manually (using the dedicated DSL, more flexibility), or just using one of the default configurations.


```kotlin title="Default Configurations"
private val antipiracy = KevlarAntipiracy.Defaults.Full()
```

```kotlin title="Manual DSL"
private val antipiracy = KevlarAntipiracy {
    scan {
        pirate()
        collateral()
    }
}
```

You can find more information about each individual item in the [reference](reference.md) page.


The settings are additive. If you leave a blank DSL, nothing will be detected, because no checks will be run, because the settings are empty.

```kotlin title="Empty"
private val antipiracy = KevlarAntipiracy {
    scan {}
}
```
