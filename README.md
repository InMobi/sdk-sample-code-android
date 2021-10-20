InMobi SDK for Android
======================

Modified: 19 October, 2021

SDK Version: 10.0.1

Thanks for monetizing with InMobi!
If you haven't already, [sign up](https://www.inmobi.com/user/index?locale=en_us#signup) for an account to start monetizing your app!

## Download
The InMobi SDK for Android is available via:

**mavenCentral AAR**

The InMobi SDK is available as a AAR via mavenCentral; to use it, add the following to your `build.gradle`

```
repositories {
    mavenCentral()
}
dependencies {
    implementation 'com.inmobi.monetization:inmobi-ads:10.0.1'
}
```

**Download from the support portal**

To download the latest SDK as a AAR, please visit [http://inmobi.com/sdk](https://inmobi.com/sdk).

**To continue integrating with the InMobi SDK, please see the [Integration Guidelines](https://support.inmobi.com/monetize/android-guidelines/) for Android.**

## New in this version
    - OMSDK Dependency is automatically imported.
    - Migrated OMSDK to 1.3
    - Audio Focus in Native Ads
    - Interface Changes
            - APIs Added
                   • InMobiBanner
                        public void setContentUrl(@NonNull String contentUrl)
                   • InMobiInterstitial
                        public void setContentUrl(@NonNull String contentUrl)
                   • InMobiNative
                        public void setContentUrl(@NonNull String contentUrl)
                   • InMobiSdk
                        public static boolean isSDKInitialized()
    - Upgraded Mediation Support for  Facebook SDK 6.7.0 and AdMob SDK 20.4.0.
    - Bug fixes and improvements


## Requirements
- Android 4.0.1 (API level 15) and higher
- androidx.browser
- androidx.appcompat
- androidx.recyclerview
- Picasso Library (picasso-2.71828.jar - available on JCenter)
- **Recommended** Google Play Services 17.0.0

## License
To view the license for the InMobi SDK, see [here](https://github.com/InMobi/sdk-sample-code-android/blob/master/sdk/licenses/License.txt). To view the terms of service, visit [https://inmobi.com/terms-of-service](http://inmobi.com/terms-of-service/).
The code for the sample apps is provided under the Apache 2.0 License.

