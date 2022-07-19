# Dependencies and Integration

All the dependencies are published on MavenCentral. Make sure to have it included in your `reporitories` block in the top level `build.gradle` file

```groovy
repositories {
    mavenCentral()
}
```

## Antipiracy
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


## Rooting
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


## Integrity

???+ gradle "Gradle"

	``` java
	dependencies {
    	implementation "com.github.kevlar-kt:integrity:1.0.0"
	}
	```

??? gradle "Kotlin DSL"

	``` kotlin
	dependencies {
	    implementation("com.github.kevlar-kt:integrity:1.0.0")
	}
	```

??? gradle "Maven"

	``` xml
	<dependency>
	    <groupId>com.github.kevlar-kt</groupId>
	    <artifactId>integrity</artifactId>
	    <version>1.0.0</version>
	    <type>pom</type>
	</dependency>
	```
