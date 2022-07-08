# Antipiracy
![Day Scheme](https://raw.githubusercontent.com/kevlar-kt/kevlar/master/docs/assets/images/kapd.jpeg#only-light)
![Night Scheme](https://raw.githubusercontent.com/kevlar-kt/kevlar/master/docs/assets/images/kapn.jpeg#only-dark)

The antipiracy module contains tools for the detection of different categories of pirate software which may be installed and running on target devices. 

At its core, this package runs a battery of tests against the installed applications to check for potential active pirate software, 
which depending on your policies may be a security environment issue. 
It then compiles the results into an attestation (it can either be uninitialized, clear or failed)
which is returned to your app, where you can check what has been found and act accordingly.

To [implement](implementation.md) this, you initialize `KevlarAntipiracy` with your desired settings, and then you can submit attestation requests.
Each attestation request will cause Kevlar to grab the package list, perform the checks and return an attestation.

The settings you can provide influence what will be included in the attetsation. 

## Attestation process overview
When you require an attestation [through `antipiracy.attestate(context)`], the antipiracy module executes the following operations:

1. The installed package list is queried from the `PackageManager`. Make sure to [have the right permissions](privacy.md);
2. The test battery is initialized (to match your scan parameters) and ran on all packages, against the precompiled dataset onboard the library;
3. The results are collected, processed, filtered and returned.

## Performance
Package detection and testing makes use of coroutines to run scan operation in parallel for each package to minimize the time taken to run through all packages.

Most of the checks consist of some type of string analysis (characters & regex matching and abstract alphabet inference), which have pretty decent running times while being precise and reliable tools.

The different scan options are taken into account intelligently in order to analyze and run the battery of tests on all packages in one pass, regardless of how many checks are to be performed.

!!! tip "Timing"
	
	The full attestation process takes from start to finish ≈ 100-200ms. It is mainly influenced by the processing power of the device, the numer of apps installed, and your scan configuration.

