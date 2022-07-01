# Detection Techniques
This page details the detection techniques (aka tricks) employed in Kevlar Antipiracy.

It should be stated that all checks are ran against the local dataset (what kevlar thinks are pirate apps & stores), which is hardcoded in the library and therefore in your shipped software.

!!! question "Obfuscation complexity"

	Writing good self-obfuscated software is hard. 
	There are a few ways, but essentially you need an installer package which carries the payload and an algorithm to insert the actual software in a randomized stub.
	If you do it well, there is no way to characterize your package and it is therefore more difficult to detect automatically ([Magisk](https://github.com/topjohnwu/Magisk) does this exceptionally well, to the point that it is almost useless to try)
	Doing slightly less than perfection will lead to detection.


## String matching
This is the most basic yet efficient scanning tool. 
Running a basic string match against some package parameters does miracles for detection,
since most non-super-sneaky pirate software has a fixed package name, or a fixed app label, or a fixed class name, or all three.

If kevlar catches even one of those, then the software is detected and will be reported in the attestation.

It doesn't even need to be a full string match really, most of the time you need a partial (contains) match to detect something. 
This is very useful (and implemented) against prefix/postfix switching.


## Regex matching
Same as above, but with regex.


## Abstract Alphabet Inference
This is the most advanced bit in package detection. 

Some packages will swap characters with something similar, but not equal (and usually belonging to obscure charsets),
so that they break string matching and make regex a hell (the character binary encoding is different, even if it looks the same to your eyes).

And since this process is usually randomized, you can not know in advance which of the characters will be swapped and which will be real.

The solution is fast abstract matching, where we describe on a high level which string (word) we are looking for, 
and then kevlar will automatically run the check on every package translating the high level description to known sneaky characters (1 abstract character maps to many sneaky characters).

This can be checked strictly (full match), partially (contains), or probabilistically (given a minimum percentage of matches)

For FSA people, this is like having multiple arcs from one state to the next, with all the sneaky characters as transitions. Word acceptance is equivalent to detection.

!!! info "Sneaky characters"

	The usual roll includes characters from latin fullwidth, cyrillic, greek, a lot of weird characters and like 20 variations of the symbols /, _ and -.
	You can go check out the full list at `AsciiVariations.kt`. I'd advise doing so on an empty stomach.


## Collateral tools
There are some techniques which may have non-zero false positive rates, and they are disabled by default. 
It is a form of aggressive detection, and it should be used in scenarios where you are willing to accept some risk to get (very) slightly better detection

It uses known pirate package name structures and matches for those.

# Tradeoffs
kevlar is not an antivirus. It is not designed to be on that end of the spectrum. It is an heuristic to *quickly* catch *most* of the junk installed on the device.

- If you want more precision, you need more time (There are advanced and stricter checks used in financial software: archive and directory scanning, extended signature checks, and everything done with professional security software, but doing that [defeats the purpose](../../overview/philosophy.md)).
- If you want less running time, you have less precision.