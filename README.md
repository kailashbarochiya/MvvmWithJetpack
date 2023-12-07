# README #

This README would normally document whatever steps are necessary to get your application up and
running.

### Software / SDKs ###

* Java 11+
* Kotlin
* Download Android Studio Latest version
* Android SDK
* Min API: Android 5.0 (Lollipop) | API Level 21
* Target API: Android 13 | API Level 33
* compileSdkVersion: 33
* Git

### Android Studio Plugins ###

* Git

### Improve Build Speed ###

* org.gradle.daemon=true
* org.gradle.parallel=true
* android.useAndroidX=true
* android.enableJetifier=true
* kotlin.code.style=official
* kapt.use.worker.api=false
* firebasePerformanceInstrumentationEnabled=false
* org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8


### Build Types ###

| Build Type | Configuration                                                                      |
|:-----------|:-----------------------------------------------------------------------------------|
| `release`  | shrinkResources true                                                               |
|            | minifyEnabled true                                                                 |
|            | zipAlignEnabled true                                                               |
|            | debuggable false                                                                   |
|            | signingConfig signingConfigs.config                                                |                    |
|            | proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro' |
|
| `debug`    | shrinkResources false                                                              |
|            | minifyEnabled false                                                                |
|            | zipAlignEnabled false                                                              |
|            | debuggable true                                                                    |
|            | signingConfig signingConfigs.config                                                ||
|            | proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro' |



### Third Party Libraries ###

* Hilt
* Jetpack Component (Mvvm,Data-binding,View Binding)
* Glide(Image loading)
* API call (Ok-http,Retrofit,Mvvm with Kotlin Coroutine and Flow)
* Storage (Data- store with Preferences) -preferences
* Scalable size (com.intuit.sdp:sdp-android) - need to remove
* Navigation Graph (androidx.navigation:navigation)
* Map and Location(com.google.android.gms:play-services)
* Scanner (com.journeyapps:zxing-android-embedded:3.6.0)