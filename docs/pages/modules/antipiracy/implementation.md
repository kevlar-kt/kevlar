# Implementation

A working example for the antipiracy module can be found in the github repository under the `:showcase` module.

## Dependency

???+ gradle "Gradle"

	``` java
	dependencies {
    	implementation "io.github.kevlar-kt:antipiracy:1.1.1"
	}
	```

??? gradle "Kotlin DSL"

	``` kotlin
	dependencies {
	    implementation("io.github.kevlar-kt:antipiracy:1.1.1")
	}
	```

??? gradle "Maven"

	``` xml
	<dependency>
	    <groupId>io.github.kevlar-kt</groupId>
	    <artifactId>antipiracy</artifactId>
	    <version>1.1.1</version>
	    <type>pom</type>
	</dependency>
	```

## Permissions
Kevlar's antipiracy module works essentially by running heuristics and filters on the list of installed packages, against a local dataset, matching and reporting the results as they are found.

Since Android 11 (API 30), there have been [some changes](https://developer.android.com/training/package-visibility) regarding the behaviour of `PackageManager` (and the visibility of installed packages). Specifically, if you query all the installed packages (just like kevlar does, via `PackageManager#getInstalledApplications`), only a [specified subset](https://developer.android.com/training/package-visibility/automatic) of installed apps are visible by default, and they are packages that may interact with your app.

This, in turn, would break the attestation process, because malicious software may not be returned in said package list, and won't therefore be detected.

There are a few workarounds for this: either listing in the app's manifest a few query entries that will enable `PackageManager` to return the packages that kevlar's engine may detect, or adding a special permission to your app's manifest (with a few drawbacks)

You can more about this in [privacy](privacy.md).


### Kevlar Manifest Queries
The following queries will enable Kevlar to read, among the other app-related packages, all the packages which it will be looking for. This fixes the problem, as you can have a working antipiracy system, without adding the `QUERY_ALL_PACKAGES` permission

Include the queries statement in your manifest like so:

``` xml title="AndroidManifest.xml (single line)" hl_lines="5-12"
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <queries>
        <package android:name="uret.jasi2169.patcher" /> <package android:name="zone.jasi2169.uretpatcher" /> <package android:name="org.creeplays.hack" /> <package android:name="apps.zhasik007.hack" /> <package android:name="com.leo.playcard" /> <package android:name="com.appsara.app" /> <package android:name="com.xmodgame" /> <package android:name="org.sbtools.gamehack" /> <package android:name="com.zune.gamekiller" /> <package android:name="com.killerapp.gamekiller" /> <package android:name="cn.lm.sq" /> <package android:name="com.aag.killer" /> <package android:name="com.github.oneminusone.disablecontentguard" /> <package android:name="com.oneminusone.disablecontentguard" /> <package android:name="madkite.freedom" /> <package android:name="jase.freedom" /> <package android:name="cc.jase.freedom" /> <package android:name="cc.madkite.freedom" /> <package android:name="cc.cz.madkite.freedom" /> <package android:name="com.devadvance.rootcloak" /> <package android:name="com.devadvance.rootcloakplus" /> <package android:name="com.formyhm.hideroot" /> <package android:name="com.formyhm.hiderootpremium" /> <package android:name="com.amphoras.hidemyroot" /> <package android:name="com.amphoras.hidemyrootadfree" /> <package android:name="com.zachspong.temprootremovejb" /> <package android:name="cm.aptoide.pt" /> <package android:name="com.happymod.apk" /> <package android:name="happygames.io" /> <package android:name="org.blackmart.market" /> <package android:name="com.blackmartalpha" /> <package android:name="com.mobogenie" /> <package android:name="me.onemobile.android" /> <package android:name="com.repodroid.app" /> <package android:name="com.getjar.reward" /> <package android:name="com.slideme.sam.manager" /> <package android:name="ac.market.store" /> <package android:name="net.appcake" /> <package android:name="com.appcake" /> <package android:name="com.zmapp" /> <package android:name="org.mobilism.android" /> <package android:name="com.allinone.free" /> <package android:name="com.chelpus.lackypatch" /> <package android:name="com.dimonvideo.luckypatcher" /> <package android:name="com.forpda.lp" /> <package android:name="com.android.vendinc" /> <package android:name="com.android.vending.licensing.ILicensingService" /> <package android:name="com.android.vending.billing.InAppBillingService.LOCK" />

        <intent>
                <action android:name="android.intent.action.SEND_MULTIPLE" />
                <data android:mimeType="*/*" />
        </intent>
    </queries>
    

    <application
        android:name="com.kevlar.showcase.App"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">

        <activity
            android:name="com.kevlar.showcase.ui.activities.main.MainActivity"
            android:exported="true">

            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```


??? Multi-line manifest file

	``` xml title="AndroidManifest.xml"
	<?xml version="1.0" encoding="utf-8"?>
	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
	    xmlns:tools="http://schemas.android.com/tools">
	
	    <queries>
	        <package android:name="uret.jasi2169.patcher" />
	        <package android:name="zone.jasi2169.uretpatcher" />
	        <package android:name="org.creeplays.hack" />
	        <package android:name="apps.zhasik007.hack" />
	        <package android:name="com.leo.playcard" />
	        <package android:name="com.appsara.app" />
	        <package android:name="com.xmodgame" />
	        <package android:name="org.sbtools.gamehack" />
	        <package android:name="com.zune.gamekiller" />
	        <package android:name="com.killerapp.gamekiller" />
	        <package android:name="cn.lm.sq" />
	        <package android:name="com.aag.killer" />
	        <package android:name="com.github.oneminusone.disablecontentguard" />
	        <package android:name="com.oneminusone.disablecontentguard" />
	        <package android:name="madkite.freedom" />
	        <package android:name="jase.freedom" />
	        <package android:name="cc.jase.freedom" />
	        <package android:name="cc.madkite.freedom" />
	        <package android:name="cc.cz.madkite.freedom" />
	        <package android:name="com.devadvance.rootcloak" />
	        <package android:name="com.devadvance.rootcloakplus" />
	        <package android:name="com.formyhm.hideroot" />
	        <package android:name="com.formyhm.hiderootpremium" />
	        <package android:name="com.amphoras.hidemyroot" />
	        <package android:name="com.amphoras.hidemyrootadfree" />
	        <package android:name="com.zachspong.temprootremovejb" />
	        <package android:name="cm.aptoide.pt" />
	        <package android:name="com.happymod.apk" />
	        <package android:name="happygames.io" />
	        <package android:name="org.blackmart.market" />
	        <package android:name="com.blackmartalpha" />
	        <package android:name="com.mobogenie" />
	        <package android:name="me.onemobile.android" />
	        <package android:name="com.repodroid.app" />
	        <package android:name="com.getjar.reward" />
	        <package android:name="com.slideme.sam.manager" />
	        <package android:name="ac.market.store" />
	        <package android:name="net.appcake" />
	        <package android:name="com.appcake" />
	        <package android:name="com.zmapp" />
	        <package android:name="org.mobilism.android" />
	        <package android:name="com.allinone.free" />
	        <package android:name="com.chelpus.lackypatch" />
	        <package android:name="com.dimonvideo.luckypatcher" />
	        <package android:name="com.forpda.lp" />
	        <package android:name="com.android.vendinc" />
	        <package android:name="com.android.vending.licensing.ILicensingService" />
	        <package android:name="com.android.vending.billing.InAppBillingService.LOCK" />
	
	        <intent>
	                <action android:name="android.intent.action.SEND_MULTIPLE" />
	                <!-- <category android:name="android.intent.category.DEFAULT" /> -->
	                <data android:mimeType="*/*" />
	        </intent>
	    </queries>
	    
	
	    <application
	        android:name="com.kevlar.showcase.App"
	        android:icon="@mipmap/ic_launcher"
	        android:label="@string/app_name"
	        android:theme="@style/AppTheme">
	
	        <activity
	            android:name="com.kevlar.showcase.ui.activities.main.MainActivity"
	            android:exported="true">
	
	            <intent-filter>
	                <action android:name="android.intent.action.MAIN" />
	
	                <category android:name="android.intent.category.LAUNCHER" />
	            </intent-filter>
	        </activity>
	    </application>
	</manifest>
	```
    









### `QUERY_ALL_PACKAGES` Permission
This is a very particular permission, as it allows your app to be treated pre-api30 and receive the full, unfiltered app list. It is declared below:


``` xml title="AndroidManifest.xml"
    <uses-permission
        android:name="android.permission.QUERY_ALL_PACKAGES"
        tools:ignore="QueryAllPackagesPermission" />
``` 

!!! danger "Google Play Store Admission"
	Apps built using this permission may not be allowed on the play store, unless they belong in a specific category 



```xml
<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES"
    tools:ignore="QueryAllPackagesPermission" />
```

## Initialization & Attestations
You need to create a `KevlarAntipiracy` instance (which is the way you will be requesting attestations), along with your desired parameters (either global, local or in your repository layer, if you are using MVVM/MVC).

Once you have that, you just go ahead and call `antipiracy.attestate()` in a coroutine and your system will be analyzed, according to the provided parameters.

`AntipiracyAttestation` will be returned from the call (it's a sealed class), containing the found software list, if any.

Note that we will be initializing `KevlarAntipiracy` with custom scan settings, but you could leave it as default.


## In-Place
This is the most concise way to implement piracy checks.

```kotlin title="InPlace.kt"
val antipiracy = KevlarAntipiracy {
    scan {
        // your scan configuration
        pirate()
        store()
    }
}

CoroutineScope(Dispatchers.Default).launch {
	// Attestation request
    when (val attestation = antipiracy.attestate(context)) {
        is AntipiracyAttestation.Blank -> {
            // Pending attestation, no information yet. 
        	// Don't do anything.
        }
        is AntipiracyAttestation.Clear -> {
            // Good to go.
        }
        is AntipiracyAttestation.Failed -> {
            // Pirate software detected.
        }
    }
}
```

This packs everything in one file. It is not excellent when writing a modern applications but it does its job.

## ViewModel + Repository + SharedFlow + DI with Hilt

#### Activity:
```kotlin title="AntipiracyActivity.kt"
@AndroidEntryPoint
class AntipiracyActivity : AppCompatActivity() {

    private val vm: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
	    
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.attestation.collectLatest {
                    when (it) {
                        is AntipiracyAttestation.Blank -> {
                            // Pending attestation, no information yet.
                            // Don't do anything.
                        }
                        is AntipiracyAttestation.Clear -> {
                            // Good to go.
                        }
                        is AntipiracyAttestation.Failed -> {
                            // Pirate software detected.
                        }
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            vm.requestAttestation()
        }
    }
}
```

#### View model:
```kotlin title="ActivityViewModel.kt"
@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val securityRepository: AntipiracyRepository
) : ViewModel() {

    private val _attestationState = MutableStateFlow(KevlarAntipiracy.blankAttestation())

    val attestation: SharedFlow<AntipiracyAttestation> = _attestationState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = KevlarAntipiracy.blankAttestation()
    )

    fun requestAttestation() {
        viewModelScope.launch {
            _attestationState.value = securityRepository.attestate()
        }
    }
}

```

#### Repository
```kotlin title="AntipiracyRepository.kt"
class AntipiracyRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
    private val antipiracy = KevlarAntipiracy {
        scan {
            // your scan configuration DSL here
            pirate()
            store()
        }
    }
	
    suspend fun attestate(): AntipiracyAttestation = withContext(externalDispatcher) {
        antipiracy.attestate(context)
    }
}
```
