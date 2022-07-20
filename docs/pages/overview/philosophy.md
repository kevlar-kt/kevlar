# Philosophy

## What does it do?
It warns your application when a specific security requirement is not met by the system, userspace, or app.

Each kevlar package shares a common structure: they all produce an attestation regarding (an aspect of) the security state of the Android device running your app.
The idea behind it is being able to quickly capture every meaningful security information regarding a certain field, with a certain accuracy.

These modules provide an array of detection methods that can be used to ensure the app is running in its proper intended environment

!!! note
	A general rule of thumb is to include only what you need, and nothing more. 
	There is little to no point in using antipiracy protection if you do not have sensitive in-app material and/or transactions you want to protect, same with rooting or integrity.


## Does it always work?
No.
It does most of the time, but there is no guarantee of a 100% success rate.

What kevlar provides is an accurate heuristic, which is relatively cheap to run and provides a valuable assessment within an acceptable margin of error.

If you require a higher success rate, you will need much more sophisticated and ad-hoc levels of protection. 
Kevlar catches what can be caught in a reasonable amount of time, space, and lines of code. 
In a similar fashion as the [pareto distribution](https://en.wikipedia.org/wiki/Pareto_distribution), to slightly improve the detection rate you will need much more complicated software, much more time, or much more space.

And for the record, any software trying to claim that it achieves 100% detection is factually wrong (there is a theorem in computability and decidability theory stating that you can not do that).


## Naming decisions
Kevlar is a material used in bulletproof vests. 
And besides starting with a **k** (which at this point appears to be an implicit requirement for any cool kotlin library), it perfectly embodies its philosophy.

A soldier in the field is a target for the enemy force.
Soldiers shoot bullets at each other and who is hit die. Wearing a bulletproof vest is a way of reducing the chance of you dying. 
It makes practical sense: it is a light material, not difficult to carry around, and it provides life-saving protection.

Kevlar is lightweight body armor for your app, to check and shield against incoming hostile attacks.

If you are being targeted with high precision sniper fire, a javelin anti-tank launcher, or a Royal Naval vessel in the immediate vicinity launches a missile strike on your location,
even if you are James Bond there is little left to do.

If your app is reverse engineered, the security bits stripped away and then recompiled (more details in [Anatomy of an Attack](anatomy_of_attacks.md)), you are going to be vulnerable, and there is nothing you can do about it. 

But while being imperfect, it manages to be efficient and deflect the majority of automated and non-specialized patches and attacks, which is the majority of what you will (hopefully) be exposed to.

Another similarity is about what you want to protect. 
People don't usually wear a bulletproof vest at home *[redacted U.S. joke]*.
It doesn't make much sense, since the attack is not likely. 
The same thing goes with putting kevlar on a calculator app. 
You can. It does not make much sense if it does not have valuable commercial features or APIs (On WolframAlpha it does).


!!! tldr "Success rates"
	Given the success rate of this library against patches versus the success rate of kevlar vests against bullets, this could have easily been called full body armor, but that didn't sound as cool.


## Security Through Obscurity
Kevlar does not rely on [security through obscurity](https://en.wikipedia.org/wiki/Security_through_obscurity). Its source code, logic, and dataset is public. 

Since the purpose of kevlar is to be a generic barrier to defeat automated and unskilled attacks, 
going to the extent of obfuscating code that, if ever reverse engineered, would just be stripped away, seems like a fruitless idea.

It would be necessary to introduce such mechanisms only if hostile software would try to automatically target specific kevlar components. 
While I doubt this would ever happen, introducing basic internal safety checks or compile-time randomized obfuscation would surely make that kind of detection unfeasible.


## Accuracy and Precision
It is fairly accurate (since there are just a few checks to run to check the security status, we are pretty confident that most of the time we will get the information right),
and has excellent precision (since checks are deterministic and predictable, given an initial condition, the result will always be the same, accurate or not)

(See [accuracy vs precision](https://en.wikipedia.org/wiki/Accuracy_and_precision))


## History
This project has been alive since 2017 (by the former name billing-protector) to counteract automatic attacks against android applications (AAAAA).
The original project was mainly aimed at protecting apps from Lucky Patcher, an infamous patch tool used to modify (recompile with automated code replacement and modification, more details in security/attacks) apps to bypass IAP (In-app purchases), and essentially crack them. It has evolved in a more general and flexible 