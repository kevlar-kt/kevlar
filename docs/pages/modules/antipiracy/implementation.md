# Implementation

A working example for the antipiracy module can be found in the github repository under the `:showcase` module.

## Dependency

???+ gradle "Gradle"

	``` java
	dependencies {
    	implementation "com.github.kevlar-kt:antipiracy:1.0.0"
	}
	```

??? gradle "Kotlin DSL"

	``` kotlin
	dependencies {
	    implementation("com.github.kevlar-kt:antipiracy:1.0.0")
	}
	```

??? gradle "Maven"

	``` xml
	<dependency>
	    <groupId>com.github.kevlar-kt</groupId>
	    <artifactId>antipiracy</artifactId>
	    <version>1.0.0</version>
	    <type>pom</type>
	</dependency>
	```

## Permissions
You can read the details in [privacy](privacy.md), but essentially since android R (API 30) it is necessary to include the following permission in your app's `AndroidManifest.xml` in order to successfully query the package list.

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
