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

The integrity module contains tools for the detection of tampering attempts against your app.

It requires a bit of effort to implement (you need to find, code and obfuscate your app metadata, all the instruction are in [implementation](implementation.md)), but once you have it runs quickly and works extremely well.

It is capable of detecting:

- Signature mismatches (the running app's signature and the hardcoded signature are different)
- Package name mismatches (the running app's package name and the hardcoded package name are different)
- Debuggable flag (the running app is debuggable, which should never happen for production apps)
- Disallowed Installers (the running app has been installed through a disallowed package installer)


!!! question "Purpose of the `integrity` module"
	You want to use this module if you need to give your app a layer of protection against tampering attacks (see [anatomy](../../overview/anatomy_of_attacks.md) for more details).
	Enabling just a few checks and implementing basic obfuscation will make your app harder to crack.


!!! warning Automatic vs Specific attack
	This module is the best defence against automatic and/or unskilled attacks


## Attestation process overview
This package can produce two attestations: a `TargetAssestation` and a `StatusAttestation`.

When you require an attestation, the rooting module performs the following operations:

- for the targets attestation (through `rooting.attestateTargets(context)`):

	1. Depending on what system modification you selected, the appropriate battery of tests for that system modification is initialized and ran;
	2. The results are collected, processed, filtered and returned.

- for the status attestation (through `rooting.attestateStatus()`):

	1.  Depending on what system condition you selected, the appropriate check for that system status flag is initialized and ran;
	2. The results are collected, processed, filtered and returned.


!!! warning
`Blank` is completely different from `Clear` (or `Failed`). It means that the software is initialized but that nothing has been done yet.

The attestation is returned either in `TargetRootingAttestation` or `StatusRootingAttestation` (both are sealed class), which depending on the detection status can be of three types (with different fields):

- `Blank`: This is a non-processed status. It should not be interpreted, as it does not carry any meaning about the attestation result. It is not to be interpret `Clear`.
- `Clear`: The attestation has passed. There is nothing to report. This means that no system modification/status has triggered the detection from the battery of tests which has been executed, in compliance with the given scan parameters;
- `Failed`: The attestation has not passed. Pirate software has been detected. You can read which component has tripped the detection in the attestation result.

## Use cases
This is a pretty typical scenario for any banking application, or even for games.