# Rooting
![Day Scheme](https://raw.githubusercontent.com/kevlar-kt/kevlar/master/docs/assets/images/krd.jpeg#only-light)
![Night Scheme](https://raw.githubusercontent.com/kevlar-kt/kevlar/master/docs/assets/images/krn.jpeg#only-dark)

The rooting module contains tools for the detection of system modifications that may be active on the device running your app.

Once configured, the package quickly runs a battery of tests on the operative system [through shell commands] to check if the required modifications are present on the device.

It is capable of detecting the following system modifications:

- root access
- magisk installations
- busybox binaries
- toybox binaries
- xposed framework

And report the following system conditions:
- test keys
- selinux status


!!! question "Purpose of the `rooting` module"
	You may want to use this package if you care about the system-wide security status, or if you consider that your application running on rooted/modified devices is a security risk.
	