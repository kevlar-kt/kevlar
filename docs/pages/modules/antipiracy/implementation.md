# Implementation
A working example for the antipiracy module can be found in the github repository under the `:showcase` module.
You need to create a `KevlarAntipiracy` instance (which is the way you will be requesting scans), along with your desired parameters (either global, local or in your repository layer, if you are using MVVM/MVC).

Once you have that, you just go ahead and call `antipiracy.attestate()` in a coroutine and your system will be analyzed, according to the provided parameters.

`AntipiracyAttestation` will be returned from the call (it's a sealed class), containing the found software list, if any.

## In-Place

```kotlin
val antipiracy = KevlarAntipiracy {
    scan {
        // your scan configuration
        pirate()
        store()
    }
}

CoroutineScope(Dispatchers.Default).launch {
    when (val attestation = antipiracy.attestate(context)) {
        is AntipiracyAttestation.Blank -> {
            // Pending attestation, no information yet.
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


## ViewModel + Repository + SharedFlow + DI with Dagger

#### Activity:

```kotlin
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

#### View model code:
```kotlin
@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val securityRepository: SecurityRepository
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

```kotlin
class SecurityRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val externalDispatcher: CoroutineDispatcher
) {
    private val antipiracy by lazy {
        KevlarAntipiracy {
            scan {
	            // your scan configuration
                pirate()
                store()
            }
        }
    }
	
    suspend fun attestate(): AntipiracyAttestation = withContext(externalDispatcher) {
        antipiracy.attestate(context)
    }
}
```
