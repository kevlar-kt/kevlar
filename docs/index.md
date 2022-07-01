# Welcome to Kevlar

## Abstract
Kevlar is a security toolkit (library) for Android apps.
It is divided in 3 packages ([antipiracy], [integrity] and [rooting]), each containing specific tooling and components.

[antipiracy]: pages/modules/antipiracy/antipiracy.md
[integrity]: pages/modules/integrity/integrity.md
[rooting]: pages/modules/rooting/rooting.md

Its purpose is to be an auditing tool, used to inspect the security environment on Android devices.

A security environment is the security state of a device, which can be probed with the different packages. 

Each package focuses on a specific security environment area:

- antipiracy detects the presence of pirate software installed on the device (user-wise security);
- integrity detects certain types of tampering your app may have been targeted with (app-wise security);
- rooting detects the presence of root access and custom binaries on the system (system-wise security).

Kevlar is intended to be used any time it is deemed necessary to determine whether the device your app is running on can be regarded as secure, 
according to your policies and security requirements.

Common use cases for security environment checks are applications managing sensitive resources, such as in-app purchases and subscriptions, 
costly server-side resources, financial transactions, and anything that has value.


## Security Environment
The security environment is the status of the device. 
This counts system-wide security (system modifications, rooting, custom binaries, custom roms, emulator, test-keys), 
user-wide security (pirate stores and pirate apps),
and app-wide security (tampering, recompiling, changing signature)

!!! info "Flexible Security Environment"
	Kevlar does not automatically detect a "standard" unsafe environment and give a 0/1 answer.
	The kind of environment that is acceptable for your app to run in can be configured in each package.

	You may be indifferent to some things (e.g. root detection) and very sensitive about others (e.g. app tampering & piracy).
	You can customize the set of checks the library executes in each package.



## Additions & Alternatives
This tool is meant to be an approximate form of environment analysis and estimation.
It covers a large amount of attack vectors and does a decent job at it.

This does not mean that it is unbreakable. You can find more details in [philosophy], but essentially 
it is a level 0 protection which can be removed by manually reverse engineering the code.

[philosophy]: pages/overview/philosophy.md

This doesn't render it useless: it is very efficient at protecting against automated and unskilled attacks, 
which usually are the majority.

For stricter scenarios where a higher fidelity and accuracy is required, you should be using something more specific.