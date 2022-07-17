# Internals
Detecting system targets and statuses is easy and difficult at the same time.
There are conditions which are really simple and only require a binary check to determine. 
There are other kind of features which are harder to identify and classify reliably, given how different they get across versions of android, versions of the feature and settings of the feature.

## Binary dumping
For a lot of features we can check if the associated binary executable is present. If it is, then we consider that the feature is present as well. This check can be ran at different permission levels with different results.

## Custom checking
Xposed framework requires a specific check on a system file to determine whether it is active or not.