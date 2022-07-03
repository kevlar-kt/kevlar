# Implementation
A working example is in the github repository under the `:showcase` module.


## In-Place

```kotlin
val antipiracy = KevlarAntipiracy {
    scan {
        // your scan configuration
        pirate()
        store()
        collateral()
    }
}

CoroutineScope(Dispatchers.Default).launch {
	antipiracy.attestate(context)
}
```


## ViewModel + Repository + SharedFlow + DI

Activity:

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

View model code:
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

Repository

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
                collateral()
            }
        }
    }
	
    suspend fun attestate(): AntipiracyAttestation = withContext(externalDispatcher) {
        antipiracy.attestate(context)
    }
}
```
