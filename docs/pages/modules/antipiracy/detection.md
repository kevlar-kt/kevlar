# Detection Techniques
This page details the detection techniques (aka tricks) employed in Kevlar Antipiracy.

It should be stated that all checks are run against the local dataset (what kevlar thinks are pirate apps & stores, a summary of that is in [database](database.md)), which is hardcoded in the library and therefore in your shipped software.

!!! question "Obfuscation complexity"

	Writing good self-obfuscating software is hard. 
	There are a few ways, but essentially you need an installer package that carries the payload and an algorithm to insert the actual software in a randomized stub.
	If you do it well, there is no way to characterize your package and it is, therefore, more difficult (if not borderline impossible) to detect automatically ([MagiskHide](https://github.com/topjohnwu/Magisk) did this exceptionally well, to the point that it is almost useless to try to achieve without switching to aggressive detection which kinda defeats the purpose)
	Doing slightly less than perfection will lead to detection.


## String matching
This is the most basic yet efficient scanning tool. 
Running a basic string match against some package parameters does miracles for detection,
since most non-super-sneaky pirate software has a fixed package name, a fixed app label, a fixed class name, or all three.

If kevlar catches even one of those, then the software is detected and will be reported in the attestation.

It doesn't even need to be a full string match really, most of the time you need a partial (containment) match to detect something. 

This is very useful (and implemented) against prefix/postfix switching.

## Regex matching
Same as above, but with regex. You can have more elaborate patterns or filters, since some packages distribute the same builds under different package names, thinking they are doing something clever.

## Abstract Alphabet Inference
This is the most advanced bit in package detection. 

Some packages will swap characters with something very similar (usually in the label name), but not equal (and usually belonging to obscure charsets),
so that they break classic string matching and make regex a hell to work with (the character's binary encoding is different, even if it looks the same to your eyes).

And since this process is usually randomized, you can not know in advance which of the characters will be swapped and which will be real, so you have to take that into account.

The solution is fast abstract matching, where we describe on a high level which string (word) we are looking for, 
and then kevlar will automatically run the check on every package translating the high-level description to known sneaky characters (1 abstract character maps to many sneaky characters).

This can be checked strictly (full match), partially (contains), or probabilistically (given a minimum percentage of matches)

For FSA people, this is like having multiple arcs from one state to the next, with all the sneaky characters as transitions. Word acceptance is equivalent to detection.

!!! info "Sneaky characters"

	The usual roll includes characters from Latin fullwidth, Cyrillic, greek, a lot of weird uncategorized characters, and like 20 variations of the symbols /, _ and -.
	You can go check out the full list at [`AsciiVariations.kt`](https://github.com/kevlar-kt/kevlar/blob/master/antipiracy/src/main/kotlin/com/kevlar/antipiracy/detection/vectors/alphabet/ascii/AsciiVariations.kt). I'd advise doing so on an empty stomach.


## Collateral tools
Some techniques may have non-zero false positive rates, and they are disabled by default. Kevlar's objective is to achieve accurate detection, with a non-zero false-negative rate, but a zero false-positive rate.

Enabling collateral tools is a form of aggressive detection, and it should be used in scenarios where you are willing to accept some risk to get (very) slightly better detection for the price of non-zero false positives.

The detection works because we know how some specific software tries to obfuscate/randomize its stub. 
We can therefore match for the distribution of those values in all installed packages, and if one matches then it may be what we are looking for. 

!!! info "Collateral example in package name randomization"

	The easiest example to illustrate this technique is with [Lucky Patcher](database.md). 
	Its installer generates a randomized stub, inserts the payload (the actual pirate software), and installs it. 

	While the stub's package name _has_ random bits, it is not completely random. It always starts with "ru.", followed by 8 random characters, followed by a ".", followed by another 9 random characters.

	```mermaid
	stateDiagram-v2
	direction LR
	[*] --> ru.
	ru. --> 8?
	8? --> .
	. --> 9?
	9? --> .
	. --> [*]
	```

	Using this technique will successfully detect lucky patcher's stub if installed, along with any other software whose package name happens to match the given specification
