# Hardcoded data

When an application is being tampered with, its code can change. 
Its behaviour can mutate. Its features may (and will) be radically different that what you have developed in production.

This introduces all kinds of issues regarding consistency of the code itself. The key to detection is being able to avoid some of those code changes on hardcoded data, and detecting inconsistencies

!!! danger "Nintendo Antipiracy & Consistency checks"
	Some nintendo games are known to have self-checking and inconsistency detection for antipiracy purposes. 
	They may, for instance, read from a non-existent memory slot [on purpose] as a form of check, and if the read operation is successful then it is likely that the program is being run in a pirate environment (tape, emulator or custom hardware), as opposed to an original one, where the read operation would have failed.

### Package name change
A simple example would be an attack which aims at changing the package name of your app.

They usually just change application metadata and packages, and try to recompile it.

If it is done well, they may try to also run a text search inside application strings to look for the package, just in case it was saved in a string variable, and change it too. 

If it is done extremely well (not implemented anywhere afaik), then they may also try to break some of the easy string obfuscation (base64 or other text encodings & charsets) and produce a different string which de-obfuscates to what the changed package name is.

!!! warning "About signature changes"
	Whatever you sign is eternal.
	Signature verification is extremely important because nobody else can sign software with your signature (since you have your private key in the keystore).

	Modifying even one bit inside your app requires the attacker to sign the application. And since they don't have access to your keystore, they can only resign it with their key or a debug key. But the point is, it is not your one. As soon as you detect that your signature does't match the one on the package that's running, you know that something has gone wrong.


## Protection through Obfuscation
As much as this may look like security through obscurity, it is a necessary technique employed to make sure that automatic attacks against your app's code do not also intercept your hardcoded data. 

This is because an automated attack may try to find any hardcoded metadata and replace it with the new value they are trying to change to.
Creating a safe container for those truth values is vital for the checks, so that they can compare the original hardcoded value with the runtime value, and match them.
