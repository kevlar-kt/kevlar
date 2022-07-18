# Integrity

``` mermaid
graph LR
  I[Inizialization] -.Settings..-> K{KevlarIntegrity};
  DB([Hardcoded Metadata]) === K
  AR1[Attestation Requests] --> K
  K --> |Clear| P[Passed];
  K --> |Failed| NP[Not Passed];
  P --> A[IntegrityAssestation]
  NP --> A
```

The integrity module contains tools for the detection of tampering attempts against your app.

It takes a little bit of time to set up, but the 

Once configured with settings, the package is able to quickly run different batteries of tests on the operative system (through shell commands) to check if the target modifications are present on the device.

It produces two different kinds of attestations: one searching for system modification and one for system conditions.

It is capable of detecting the following system modifications (through the `targets` attestation)

Depending on what you need to check, you may choose one or run both.


!!! question "Purpose of the `integrity` module"
	You may want to use this package if you care about the system-wide security status, or if you consider that your application running on rooted/modified devices is a security risk.

!!! summary "Notation"

	In the rooting module, the words "target" and "status" are used loosely, but they actually have special meaning.

	`Targets` means system modification. Something that may be installed (and detectable) over the operating system. It is a kind of add-on, a custom software component.

	`Status` means a system condition. Something that is itself part of the operating system out of the box, and whose status we want to check.


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