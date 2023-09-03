# Welcome to Kevlar

## Abstract
Kevlar is a security toolkit (library) for Android apps.
It is divided in 3 packages ([antipiracy], [rooting] and [integrity]), each containing specific tooling and components.

[antipiracy]: pages/modules/antipiracy/antipiracy.md
[rooting]:    pages/modules/rooting/rooting.md
[integrity]:  pages/modules/integrity/integrity.md

Its purpose is to be an auditing tool, used to inspect the security environment on Android devices.

A security environment is the security state of a device, which can be probed with the different packages kevlar provides.

Each package focuses on a specific security environment area:

- `antipiracy` detects the presence of pirate software installed on the device (user-wise security);
- `rooting` detects the presence of root access, custom binaries, and abnormal OS status (system-wise security);
- `integrity` detects certain types of tampering attempts your app may have been targeted with (app-wise security).

Kevlar is intended to be used any time it is deemed necessary to determine whether the device your app is running on can be regarded as secure, according to your policies and security requirements.


## Security Environment
The security environment is the status of the device.
This is subdivided into **system-wise** security (system modifications, rooting, custom binaries, custom ROMs, emulator, SELinux),
**user-wise** security (pirate stores and pirate apps),
and **app-wise** security (tampering, recompiling, changed signature & metadata)


## Flexibility
Kevlar does not automatically detect a "standard" unsafe environment and gives a 0/1 answer.
The kind of environment that is acceptable for your app to run in can be configured in each package individually.

You may be indifferent to some things (e.g. root detection) and very sensitive about others (e.g. app tampering & piracy detection).
You can customize the set of checks the library executes in each package.
Once you define your constraints, kevlar modules will operate accordingly.

If you don't explicitly instruct kevlar to check for a feature, then that feature will not be reported, regardless of its presence (or absence) on the device.


## Design
Each kevlar package contains custom implementations for what it has to scan for, but they all share the same overall structure, to make it easy to work with. Once you learn how to use a package, then you can transfer that knowledge to the other ones.

``` mermaid
graph LR
  I[Inizialization] -.Settings..-> K{Kevlar};
  AREQ[Attestation Requests] --> K
  K --> |Clear| P[Passed];
  K --> |Failed| NP[Not Passed];
  P --> ARES[Attestation Result]
  NP --> ARES
```

The founding idea is a flow of attestations. You initialize the package passing to it your settings (what you want to check for). Then you can go ahead and start requesting attestations. An attestation can either be Clear (passed) or Failed (non passed), according to your detection settings.

There may be one or more types of attestation you can request, and you can choose what you want by requesting different ones, to enforce granular control and run efficiently.

Under the hood, each package will call its implementations and run those checks against the operating system/current app, but you'll eventually get an `Attestation` back, so your only job is to check whether it is clear or not.

This makes security declarative: you express your constraints and requirements once while configuring kevlar, which will then take care of - when asked -  producing a report (attestation) for your specific configuration, telling you what was found. And finally you can analyze this report and act accordingly, repeating the process as many times as needed.

## Use Cases
Common use cases for security environment checks are applications managing sensitive resources, such as in-app purchases and subscriptions, valuable server-side resources or APIs, financial transactions, and anything that has a value that gets managed through your app/client.

Ideally, you should request an attestation whenever your client wants to verify the status of the security environment before proceeding with the high-value action.

Kevlar is a sort of guard statement for those actions, which should decrease the probability of an attacker successfully breaking your application's high-value transaction.


## Accuracy
This tool is meant to be an approximate form of environment analysis and estimation.
It covers a large number of attack vectors and does a good job at it.

This does not mean that it is unbreakable. You can find more details in [philosophy], but essentially
it is a level 0 protection that can be removed by manually reverse engineering your app.

[philosophy]: pages/overview/philosophy.md

This doesn't render it useless, it is very efficient in doing *what it is designed to do*: protecting against automated and unskilled attacks,
which will most certainly be the vast majority of what your app will ever be put through.


## Additions & Alternatives
Kevlar resembles what may look like an in-house protection system. It is open source, flexible and rich of features.

For stricter scenarios where higher fidelity and accuracy are required, you should be using something more specific (and radically different).

- [Play Integrity](https://developer.android.com/google/play/integrity) & <s>[SafetyNet](https://developer.android.com/training/safetynet)</s> from Google;
- [AppCheck](https://firebase.google.com/products/app-check) from Firebase;
- [ProGuard](https://www.guardsquare.com/proguard) and [DexGuard](https://www.guardsquare.com/dexguard) from GuardSquare.

## License
This project is licensed under the Apache License, Version 2.0. Please refer to the `LICENSE.md` file inside the Github repository for the full text.