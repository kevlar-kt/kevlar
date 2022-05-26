# Antipiracy
The antipiracy module contains tools for the detection of different categories of pirate software which may be installed and running on target devices. 

At its core, this package runs a battery of tests against the installed applications to check for potential active pirate software, 
which depending on your policies may be a security environment issue. 
It then compiles the results into an attestation (which can either be uninitialized, clear or failed)
which is returned to your app, to check what has been found and act accordingly.

## Attestation process overview
When you require an attestation, the antipiracy module executes the following operations:

1. The installed package list is queried from the `PackageManager`;
2. The test battery is initialized (to match your scan parameters) and ran in parallel for all packages;
3. The results are collected, filtered and returned

## Performance
Package detection and testing makes use of coroutines to run scan operation in parallel for each package. 
Most of the checks consist of some type of string analysis (regex matching or alphabet inference), 
which do not involve resource-intensive algorithms.

!!! tip "Timing"
	
	As useless as it may sound, I timed the full attestation process on my OnePlus 7T with 125 
	installed apps and it took on average ≈ 225ms.

