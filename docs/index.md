# Welcome to Kevlar

## Abstract
Kevlar is a security toolkit (library) for Android apps.
It is divided in 3 packages ([antipiracy], [integrity] and [rooting]), each containing specific tooling and components.

[antipiracy]: pages/modules/antipiracy/antipiracy.md
[integrity]: pages/modules/integrity/integrity.md
[rooting]: pages/modules/rooting/rooting.md

Its purpose is to be an auditing tool which can be used to inspect the security environment on an Android device it is running on.
A security environment is the state of a device, which can be probed with the different modules. 

Each module focuses on a specific area:

- antipiracy detects the presence of pirate software installed on the device;
- integrity detects certain types of tampering your app may have been targeted with;
- rooting detects the presence of root access and custom binaries on the system.

Kevlar is intended to be used any time it is deemed necessary to determine whether the device your app is running on can be regarded as secure.

!!! info "Flexible Security Environment"
	Kevlar does not automatically detect a standard unsafe environment and give a 0/1 answer. 
	The kind of environment that is acceptable for your app to run in can be configured.

	You may be indifferent to some things (e.g. root detection) and very sensitive about others (e.g. app tampering & piracy).

	You can customize the set of checks the library executes in each package.

Some common use cases for security environment checks would be
- In app purchases protection (billing)


## Additions & Alternatives
This tool is meant to be an approximate form of environment analysis and estimation.
It covers a large amount of attack vectors and does a decent job at it.

This does not mean that it is unbreakable. You can find more details in [philosophy], but essentially 
it is a level 0 protection which can be removed by manually reverse engineering the code.

[philosophy]: pages/overview/philosophy.md

This doesn't render it useless: it is very efficient at protecting against automated and unskilled attacks, 
which usually are the majority.

For stricter scenarios where a higher fidelity and accuracy is required, you should be using something more specific.