# Privacy & Package Queries
The antipiracy module is designed to check user-wide security. As such, it is the layer which is
closest to the user data and thus needs to take extra care to enforce user privacy requirements.

## Package Queries
A crucial element in this process is retrieving the application list, which all the checks 
are subsequently ran on.

This is increasingly getting difficult. Since Android 11 (api 30) the full package list 
[is filtered by default](https://developer.android.com/training/package-visibility), and there are
more privacy-friendly (less intrusive) methods for querying apps.
Those don't work as well with pirate app detection because usually pirate software actively 
tries to hide its presence with [camouflage techniques](detection.md)

A not-so-elegant but 100% functional solution is to add the `QUERY_ALL_PACKAGES` permission 
in the manifest, so that your app has clearance to query the whole app spectrum, enabling
pirate apps detection.

```xml
<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES"
    tools:ignore="QueryAllPackagesPermission" />
```

## User Privacy & Attitude
Another delicate aspect with querying installed packages is the way you communicate to the user that
you just scanned all the packages and found a pirate software, and therefore will not let them proceed to the purchase they were about to happily crack.

While obviously you should not by any means be afraid to deny the purchase/transaction/service to a malevolent user, there are varying degrees of directness with with you can communicate your decision to the end user.

Usually they are not best pleased with the discovery, so a little bit of touch is advised.

Some implementations go the other way and just do not work/act as broken, while in reality they did a much deeper check, found that the environment is unsafe, assumed that the current running software is pirated and refuse to proceed. Usually this works because the user knows it is doing something wrong and it expects that something may break or that some self-checking is in place.

!!! warning "Protection & Reviews"

    I have implemented in all my commercial Android software piracy checks, and while I'd like to show you
	some store reviews, I will refrain from exposing your uninitialized eyes to the unfathomable pain I went through while
	reading them in the first place.

	Let's just say it usually goes down like this:

	1. User tries to crack your app with pirate software;
	2. Your app picks up the pirate software, and refuses to start the in-app transaction because the environment is unsecure;
	3. Said user suddently becomes a mobile security expert and goes on the store to angrily review your app as garbage/spyware/malware/trojan/...