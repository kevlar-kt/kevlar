# Philosophy

## what does it do
Kevlar produces attestation regarding the security state of an Android device running your app.
The idea behind it is to be able to capture every meaningful information about the security environment,


These modules provide an array of detection methods which can be used to ensure the app is running in its proper intended environment

!!! note

	A general rule of thumb is to include only what you need. There is little to no point in using antipiracy protection if you do not have sensitive in-app material and/or transactions


## does it always work?
No. It does most of the times, but there is not guaranteed of 100% success rate. And any software trying to claim that is factually wrong (there is a theorem in computability and decidability theory literally stating that you can not do that).
What kevlar provides is an accurate heuristic, which is relatively cheap to run and provides an assessment within an acceptable margin of error.
It would only be natural given that..



## naming decisions & philosophy
Kevlar is a material used in bulletproof vests. And beside starting with a k (which at this point appears to be an implicit requirement for any cool kotlin library), it perfectly embodies its philosophy.

A soldier in the field is a target for the enemy force.
Soldiers shoot bullets at each other and who is hit dies. Wearing a bulletproof vest is a way of reducing the chance of you dying. It makes practical sense: it is a light material, not difficult to carry around, and it provides life-saving protection.

Kevlar is a lightweight body armor for your app, to check and shield against incoming hostile attacks.

If you are being targeted with high precision sniper fire, or a javelin anti-tank launcher, or a Royal Naval vessel in the immediate vicinity launches a missile strike on your location, even if you are James Bond there is little left to do.

If your app is reverse engineered, the security bits stripped away and then recompiled, you are going to be vulnerable, and there is nothing you can do about it. But while being inperfect, it manages to be efficient and deflect the majority of automated and non-specialized patches and attacks, which is the majority of what you will (hopefully) be exposed to.

And another similarity is about what you want to protect. People don't usually wear bulletproof vest at home ([redacted us joke]). It doesn't make sense, since the attack is not likely. Same thing goes with putting kevlar on a calculator app. You can. It does not make sense.

--- Now, given the success rate of this library vs the success rate of kevlar vests against bullets, this could have easily been called full body armor, but that definitely didn't sound good.


## History
This project has been alive since 2017 (by the former name billing-protector) to counteract automatic attacks against android applications (AAAAA). 
The original project was aimed at protecting apps from Lucky Patcher, a notorious patch tool used to modify (recompile with automated code replacement and modification, more details in security/attacks) apps to bypass IAP (In app purchases), and essentially crack them


## obvious disclaimers
As said before, this software is not guaranteed to work on every instance with 100% accuracy. The developers are not responsible in any way for this software mistakenly (not) assesting a device stare.