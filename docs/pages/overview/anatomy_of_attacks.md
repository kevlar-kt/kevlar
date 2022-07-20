# Anatomy of an attack against an Android app

This is a general informal introductory text detailing the most well-known and used attack techniques against Android apps.

We will start with the most basic and gradually build up to the most skilled ones. 
Attack techniques are presented in order of time taken to employ (weaponize) them successfully against an unguarded application.

While being different in shape and form, all the attacks follow the same basic principle: you have a target application that we want to attack (with a target in mind. Usually bypassing certain mechanisms, overriding rules, or adding/removing features), which is decompiled, then analyzed; its bytecode is modified according to the target of the attack (a payload is inserted, safety checks are removed, code is altered or replaced), then it all gets recompiled, repackaged and possibly resigned into an apk file which is installed and run.

Popular tools for this are apktool on desktop and ApkEditor on mobile.

!!! warning 
	This page tends to be a little subjective because there is not a science studying those things and I am not an expert in this field, so take everything with a grain of salt.

## 0: Automatic patching
This is by far the most common since (once the engine is ready) it is done completely automatically, without any human intervention or brainpower.

There are various pirate apps able to perform this to some extent (most notably [Lucky Patcher](../modules/antipiracy/database.md)). 
They all more or less all use a string-matching engine to perform a (very sophisticated) pattern "find-and-replace" over your application bytecode.

This is done to achieve what the target of the attack is: it may be removing advertising from your app, changing signature verification, disabling or enabling some features, inhibiting security mechanisms, or purchasing protection.
The target determines the pattern used to do the dirty work. Then the app is rebuilt and it's done.

It is the lowest form of attack since the software itself does not know what it is doing exactly but follows a general rule set. But this does not render it any less dangerous: if your software is unprepared, you will fall for this.

Against this level of attack you can employ all kevlar's arsenal with the [`antipiracy`](../modules/antipiracy/antipiracy.md) and [`integrity`](../modules/integrity/integrity.md) packages. 
Kevlar has more or less been built and designed to fight this kind of attack, by performing safety checks over your package metadata and giving you runtime warnings for the presence of malicious software which is interfering or directing your app.

The nice thing (for us developers) is that the automation of the attack is very hard to do, even on a basic level, and putting up barriers for this is comparatively quick.


## 1: Unskilled custom attacks
This tends to be a less likely kind of attack because an actual human brain is looking at your decompiled bytecode and trying to figure out what needs to be done to achieve its goal.

When I say unskilled I mean done by amateurs without in-depth knowledge of the techniques involved.

They will essentially be manually searching through your bytecode and looking for code pieces that they recognize and want to remove/edit/expand. This is usually done by searching a determinate resource, which is referenced somewhere in the code, finding that code, and trying to change its logic.

!!! note "Bytecode note"
	Bytecode is the raw language that the JVM executes. 
	In Android, everything shifts from JVM to ART but it's the same thing.
	
	There are reconstruction utilities, which will try to take the bytecode and un-compile it to grab the original source code. Most likely that is what an attacker might want to be looking at.

!!! important "Obfuscation"
	A machine does not care whether your code is obfuscated. A human brain does. You should always heavily shrink, optimize and obfuscate your production code (i.e. using ProGuard on release builds) to make it harder for attackers to make sense of it.


## 2: Root access enabled attacks
When a device is rooted, there are a whole lot of new variables to take into account when looking at security, since the operating system itself is compromised and will obey the user commands.

It's not like you can do a lot if you are running on that system, but what you can do is preemptively disable some application features to protect them from modification, if applicable and necessary according to your security standards.

That's what a lot of games or bank software do since they are targeted by custom attacks that can, with the help of root access, alter the application behavior without having to recompile it (on the fly). Either they shut down completely or they inhibit come functionality.
Casing point every Supercell game.


!!! danger "Data editing"
	An easy way to crack many (not well secured) applications is by editing the files (XML SharedPreferences or SQLite databases) on which they store data.
	I've seen countless `"is_pro"` strings inside SharedPreferences or XML/JSON settings where you can enable paid features without the app checking it, and it only takes a few bytes flips to have complete access to anything you want.
	
	That specific attack can be made harder by [encrypting](https://github.com/cioccarellia/ksprefs) and obfuscating your application preference strings.


## 3: Professional custom attack
There is very little to say here: if someone knows what he/she is doing, they are going to strip away every bit of self-checking and security controls you put in place, and they will implement whatever target they have.

While obfuscation helps, security through obscurity should not even be an option, and other techniques will make it harder to decipher what your code does, eventually, a determined mind will find a way to do what it wants to, and there is nothing you can do about it. After all, if you can modify your code to write software that adheres to the attack targets, so can they.

!!! faq "Cracked software distribution"
	Every commercial app I wrote that surpassed about 100k downloads (TurboUnfollow and Androoster) has been custom patched and redistributed on the internet. This happens regularly and, while being sad, is a reminder that perfect security protection does not exist. 

This happens because you do not have control over the device your user is running your app on. It stops being your control when the bytes are transferred and installed as a package on the device, then they are binary data open for modifications and hacks.
The only efficient way to mitigate this is by regulating what you have control over. Server-side access and APIs, data validation, in-house or professional security checks, and so on, will limit the control your app has over its working and puts it in your secure hands.

## Extra concepts

### Conditional Consistency
It is a little bit meta to talk about your application code being modified, either by recompilation or root attacks, since you can not know how it will be changed. This process can introduce all sorts of discrepancies and inconsistencies in the newly created software. 

Coding software with conditional consistency is a practice where you insert security checks that can tell when the software is itself not genuine or modified. And while ALL those checks can theoretically be stripped away or disabled, this makes it harder to find them all and avoid detection.

This is a bit overkill for android apps, but more advanced software uses those concepts to add extra nets of protection.