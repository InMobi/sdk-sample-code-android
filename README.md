InMobi SDK for Android
======================

Modified: July 25, 2019

SDK Version: 7.3.0

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
    compile 'com.inmobi.monetization:inmobi-ads:7.2.8'
}
```

**Download from the support portal**

To download the latest SDK as a JAR, please visit [http://inmobi.com/sdk](https://inmobi.com/sdk).

**To continue integrating with the InMobi SDK, please see the [Integration Guidelines](https://support.inmobi.com/monetize/android-guidelines/) for Android.**

## New in this version
- Added onAdReceived API to indicate that an ad is available in response to a request for an ad
- Added support for Android P
- Added Video events for Native Ads
- Block auto-redirection of ads without user interaction
- Banner XML integration placementId parameter expects "plid-"


## Requirements
- Android 4.0.1 (API level 15) and higher
- android-support-v4.jar, r28
- android-recyclerview-v7.jar,r28
- Picasso Library (picasso-2.5.2.jar - available on JCenter)
- **Recommended** Google Play Services 11.8.0

## License
To view the license for the InMobi SDK, see [here](https://github.com/InMobi/sdk-sample-code-android/blob/master/sdk/License.txt). To view the terms of service, visit [https://inmobi.com/terms-of-service](http://inmobi.com/terms-of-service/). 
The code for the sample apps is provided under the Apache 2.0 License.

