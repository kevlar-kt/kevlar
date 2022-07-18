# Anatomy of an attack against an Android app

This is a general introductory text detailing the most well known and used attack techniques against Android apps.

We will start with the most basic and gradually build up to the most skilled ones. Attack techniques are exposed in order of time taken to employ (weaponize) them successfully against an unguarded application.

While being different in shape and form, all the attacks follow the same basic principle: you have a target app which gets decompiled, then analyzed, its bytecode is modified (a payload is inserted, safety checks are removed, code is altered or replaced), then recompiled, repackaged and possibly resigned into an apk file.

## 0: Automatic patching
This is by far the most common since it is done completely automatically, without human intervention.

There are various pirate apps able to perform this to some extent (most notably [Lucky Patcher](../modules/antipiracy/database.md)). They more or less all use a string-matching engine to perform a (very sophisticated) string "find-and-replace" over your application bytecode.

This is done to achieve what the target of the attack is: it may be removing advertising from you app, changing signature verification, disabling or enabling some features, inhibiting security mechanisms or purchase protection.

It is the lowest form of attack, since the software itself does not know what it is doing exactly but follows a general pattern.

Against this level of attack you can employ all kevlar's arsenal with the [`antipiracy`](../modules/antipiracy/antipiracy.md) and [`integrity`](../modules/integrity/integrity.md) packages. 
Kevlar has more or less been built and designed to fight this kind of behaviour, by performing safety checks over your package metadata and giving you runtime warnings for the presence of malicious software which is interfering or directing your app. 

## 1: Unskilled custom attacks

## 2: 
