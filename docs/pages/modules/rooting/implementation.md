# Implementation

A working example for the rooting module can be found in the github repository under the `:showcase` module.

## Dependency

???+ gradle "Gradle"

	``` java
	dependencies {
    	implementation "com.github.kevlar-kt:rooting:1.0.0"
	}
	```

??? gradle "Kotlin DSL"

	``` kotlin
	dependencies {
	    implementation("com.github.kevlar-kt:rooting:1.0.0")
	}
	```

??? gradle "Maven"

	``` xml
	<dependency>
	    <groupId>com.github.kevlar-kt</groupId>
	    <artifactId>rooting</artifactId>
	    <version>1.0.0</version>
	    <type>pom</type>
	</dependency>
	```

## Initialization & Attestations
You need to create a `KevlarRooting` instance (which is the way you will be requesting attestations), along with your desired parameters (either global, local or in your repository layer, if you are using MVVM/MVC).

The `rooting` package has 2 attestations: `TargetRootingAttestation` and `StatusRootingAttestation`.
Depending on what your security requirements impose, you may need to run one or both checks.

You go ahead and request whichever attestation(s) you need. The will produce two different sealed classes (since the results will be different in case of failure, the attestations will contain different data).

We go ahead and create a working single-attestation example (for system modifications aka targets). For two attestations refer to the sample code in the `:showcase` module of the github repository.


## In-Place
This is the most concise way to implement rooting.


```kotlin title="InPlace.kt"
val rooting = KevlarRooting {
    targets {
        root()
        busybox()
    }
}


CoroutineScope(Dispatchers.Default).launch {
	// Attestation request
    when (val attestation = rooting.attestateTargets(context)) {
        is TargetRootingAttestation.Blank -> {
            // Pending attestation, no information yet. 
        	// Don't do anything.
        }
        is TargetRootingAttestation.Clear -> {
            // Good to go.
        }
        is TargetRootingAttestation.Failed -> {
            // System modifications detected.
        }
    }
}
```


## ViewModel + Repository + SharedFlow + DI with Hilt

#### Activity:
```kotlin title="RootingActivity.kt"
@AndroidEntryPoint
class RootingActivity : AppCompatActivity() {

    private val vm: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
	    
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.taregtsAttestation.collectLatest {
                    when (it) {
                        is TargetRootingAttestation.Blank -> {
                            // Pending attestation, no information yet.
                            // Don't do anything.
                        }
                        is TargetRootingAttestation.Clear -> {
                            // Good to go.
                        }
                        is TargetRootingAttestation.Failed -> {
                            // Pirate software detected.
                        }
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            vm.requestTargetsAttestation()
        }
    }
}
```

#### View model:
```kotlin title="ActivityViewModel.kt"
@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val securityRepository: RootingRepository
) : ViewModel() {
	
    private val _targetAttestationState = MutableStateFlow(KevlarRooting.blankTargetAttestation())

    val targetAttestation: SharedFlow<TargetRootingAttestation> = _rootAttestationState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = KevlarRooting.blankTargetAttestation()
    )

    fun requestTargetsAttestation() {
        viewModelScope.launch {
            _rootAttestationState.value = rootingRepository.attestateRoot()
        }
    }
}

```

#### Repository
```kotlin title="RootingRepository.kt"
class RootingRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
    private val rooting = KevlarRooting {
        targets {
            root()
            busybox()
        }
    }
	
    suspend fun attestateRoot(): TargetRootingAttestation = withContext(externalDispatcher) {
        rooting.attestateTargets(context)
    }
}
```
