# Dependencies and Integration

### Publishing

All the dependencies are published on MavenCentral. Make sure to have it included in your `reporitories` block in the top level `build.gradle` file.

```groovy
repositories {
    mavenCentral()
}
```

### Project Requirements

- Java 8+ is required;
	```groovy
	compileOptions {
	    sourceCompatibility JavaVersion.VERSION_1_8
	    targetCompatibility JavaVersion.VERSION_1_8
	}
	```
- `minSdk` 19+ is required.

## Antipiracy
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


## Rooting
???+ gradle "Gradle"

	``` java
	dependencies {
    	implementation "io.github.kevlar-kt:rooting:1.1.1"
	}
	```

??? gradle "Kotlin DSL"

	``` kotlin
	dependencies {
	    implementation("io.github.kevlar-kt:rooting:1.1.1")
	}
	```

??? gradle "Maven"

	``` xml
	<dependency>
	    <groupId>io.github.kevlar-kt</groupId>
	    <artifactId>rooting</artifactId>
	    <version>1.1.1</version>
	    <type>pom</type>
	</dependency>
	```


## Integrity

???+ gradle "Gradle"

	``` java
	dependencies {
    	implementation "io.github.kevlar-kt:integrity:1.1.1"
	}
	```

??? gradle "Kotlin DSL"

	``` kotlin
	dependencies {
	    implementation("io.github.kevlar-kt:integrity:1.1.1")
	}
	```

??? gradle "Maven"

	``` xml
	<dependency>
	    <groupId>io.github.kevlar-kt</groupId>
	    <artifactId>integrity</artifactId>
	    <version>1.1.1</version>
	    <type>pom</type>
	</dependency>
	```

