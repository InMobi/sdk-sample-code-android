InMobi SDK for Android
======================

Modified: Sep 18, 2017

SDK Version: 7.0.2

Thanks for monetizing with InMobi!
If you haven't already, [sign up](https://www.inmobi.com/user/index?locale=en_us#signup) for an account to start monetizing your app!

## Download
The InMobi SDK for Android is available via: 

**jCenter JAR**

The InMobi SDK is available as a JAR via jCenter; to use it, add the following to your `build.gradle`

```
repositories {
    jcenter()
}
dependencies {
    implementation 'com.inmobi.monetization:inmobi-ads:7.0.2'
}
```

**Download from the support portal**

To download the latest SDK as a JAR, please visit [http://inmobi.com/sdk](https://inmobi.com/sdk).

**To continue integrating with the InMobi SDK, please see the [Integration Guidelines](https://support.inmobi.com/monetize/android-guidelines/) for Android.**

## New in this version for native sample
- Uses ViewModels and LiveData from [Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) and the Data Binding library with an MVVM architecture.
- Uses [RxJava 2](https://github.com/ReactiveX/RxJava) to implement concurrency, and abstract the data layer.
- Uses [Dagger 2](https://google.github.io/dagger) to add support for dependency injection.
- Unit Test case for view model

## New in this version
- InMobi SDK v7.0.2 for Android introduces brand new Native Ad solution.
- Interactive Video Ad Experience
- Engaging end-cards are now supported via Rich-Media End-cards
- Support for GIF images

## Requirements
- Android 4.0.1 (API level 15) and higher
- android-support-v4.jar, r24 
- android-recyclerview-v7.jar,r24
- Picasso Library (picasso-2.5.2.jar - available on JCenter)
- **Recommended** Google Play Services 8.4.0

## License
To view the license for the InMobi SDK, see [here](https://github.com/InMobi/sdk-sample-code-android/blob/master/sdk/License.txt). To view the terms of service, visit [https://inmobi.com/terms-of-service](http://inmobi.com/terms-of-service/). 
The code for the sample apps is provided under the Apache 2.0 License.

