<p align="center">
  <a href="https://github.com/kevlar-kt/kevlar"><img width="100" src="https://github.com/kevlar-kt/kevlar/raw/master/art/kevlar-kt/web/icon-512.png"></a>
</p>

<h1 align="center">Kevlar</h1>

<p align="center">
  <a href="https://github.com/kevlar-kt/kevlar/releases"><img src="https://img.shields.io/github/v/tag/kevlar-kt/kevlar" alt="Latest tag" /></a>
  <a href="https://github.com/kevlar-kt/kevlar/actions?query=workflow%3A%22Build%22"><img src="https://github.com/kevlar-kt/kevlar/actions/workflows/push-debug-build.yaml/badge.svg" alt="Android CI" /></a>
  <a href="https://source.android.com/setup/start/build-numbers"><img src="https://img.shields.io/badge/minSdk-19-00E676.svg" alt="Android Min Sdk"></a>
  <a href="https://kotlinlang.org/docs/releases.html"><img src="https://img.shields.io/badge/kotlin-1.9-orange.svg" alt="Kotlin"></a>
  <a href="https://androidweekly.net/issues/issue-528"><img src="https://img.shields.io/badge/AndroidWeekly-528-5bb3e2" alt="AndroidWeekly Issue"></a>
  <a href="https://us12.campaign-archive.com/?u=f39692e245b94f7fb693b6d82&id=15eb56d1f5"><img src="https://img.shields.io/badge/KotlinWeekly-315-%238a78e8" alt="Kotlin"></a>
  <a href="https://github.com/kevlar-kt/kevlar/blob/master/LICENSE.md"><img src="https://img.shields.io/badge/license-Apache%202.0-blue.svg" alt="License"></a>
</p>


## Index
See the [project's website](https://kevlar-kt.github.io/kevlar) for docs and reference.

See the [showcase module](https://github.com/kevlar-kt/kevlar/tree/master/showcase/src/main/kotlin/com/kevlar/showcase) for code samples and library usage.

See the [integration](https://kevlar-kt.github.io/kevlar/pages/overview/dependencies_integration/) page for the dependencies, check a package's implementation page for a comprehensive and specific guide.


## Summary
Kevlar is a security toolkit (library) for Android apps. It is divided in 3 packages (antipiracy, rooting and integrity), each containing specific tooling and components.

Its purpose is to be an auditing tool, used to inspect the security environment on Android devices.



## Dependencies

### Antipiracy

```gradle
dependencies {
    implementation 'com.github.kevlar-kt:antipiracy:1.1.0'
}
```

### Rooting

```gradle
dependencies {
    implementation 'com.github.kevlar-kt:rooting:1.1.0'
}
```


### Integrity

```gradle
dependencies {
    implementation 'com.github.kevlar-kt:integrity:1.1.0'
}
```



## License

```
Copyright 2022 Kevlar Contributors
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
