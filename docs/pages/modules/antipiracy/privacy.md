# Privacy & Package Queries
The antipiracy module is designed to check user-wide security. As such, it is the layer that is
closest to the user data and thus needs to take extra care to enforce user privacy requirements.

## Package Queries
A crucial element in this process is retrieving the application list, in which all the checks 
are subsequently run on.

This is increasingly getting difficult. Since Android 11 (API 30) the full package list 
[is filtered by default](https://developer.android.com/training/package-visibility), and there are
more privacy-friendly (less intrusive) methods for querying apps.
Those don't work as well with pirate app detection because usually pirate software actively 
tries to hide its presence with [camouflage techniques](detection.md)

A nice technique is declaring the appropriate queries in the app's manifest, so that the
list given to kevlar, although not complete, will contain all the packages referenced by
the queries. And since kevlar searches for known pirate apps/stores (and thus packages),
we can add these as packages we're interested in receiving, and we're set.

It gets a little more complex for packages which actively try to hide themselves / randomize their 
properties to avoid detections, but it can be done.

You can find the guide on how to add that to the manifest in the [implementation](implementation.md) page.

!!! example "Unintentional Computational Speedup"
	By limiting the amount of packages returned by the package manager, the battery of checks (which has to be ran for each package), will take less, and therefore further reduce the execution time for the attestation. So not bad news after all!


## User Privacy & Attitude
Another delicate aspect of querying installed packages is the way you communicate to the user that
you just scanned all the packages and found pirate software, and therefore will not let them proceed to the purchase they were about to happily crack.

While obviously, you should not by any means be afraid to deny the purchase/transaction/service to a malevolent user, there are varying degrees of directness with which you can communicate your decision to the end user.

Usually, they are not best pleased with the discovery, so a little bit of touch is advised.

Some implementations go the other way and just do not work/act as broken, while in reality they did a much deeper check, found that the environment is unsafe, assumed that the currently running software is pirated, and refused to proceed. Usually, this works because the user knows it is doing something wrong and it expects that something may break or that some self-checking is in place.

!!! warning "Protection & Reviews"

    I have implemented in all my commercial Android software some sort of piracy checks, and while I'd like to show you
	some store reviews, I will refrain from exposing your uninitialized eyes to the unfathomable pain I went through while
	reading them in the first place.

	Let's just say it usually goes down like this:

	1. User tries to crack your app with pirate software;
	2. Your app picks up the pirate software, and refuses to start the in-app transaction because the environment is unsecured;
	3. Said user suddenly becomes a mobile security expert and goes on the store to angrily review your app as garbage/spyware/malware/trojan/...