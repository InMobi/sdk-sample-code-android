InMobi SDK for Android
======================

Modified: October 1, 2019

SDK Version: 9.0.0

Thanks for monetizing with InMobi!
If you haven't already, [sign up](https://www.inmobi.com/user/index?locale=en_us#signup) for an account to start monetizing your app!

## Download
The InMobi SDK for Android is available via: 

**jCenter AAR**

The InMobi SDK is available as a AAR via jCenter; to use it, add the following to your `build.gradle`

```
repositories {
    jcenter()
}
dependencies {
    implementation 'com.inmobi.monetization:inmobi-ads:9.0.0'
}
```

**Download from the support portal**

To download the latest SDK as a AAR, please visit [http://inmobi.com/sdk](https://inmobi.com/sdk).

**To continue integrating with the InMobi SDK, please see the [Integration Guidelines](https://support.inmobi.com/monetize/android-guidelines/) for Android.**

## New in this version
- Modular SDK
    - Added an ability to integrate Mediation as a separate module.
- Added support for Android 10
- Support for Mopub 5.8
- Interface Changes
    - APIs added
        - AerServTransactionInformation
            public String getCreativeID()
        - InMobiBanner
            public void destroy()
    - APIs removed
        - InMobiNative
            public InMobiNative(Context context, long placementId, NativeAdListener listener)
            public void setNativeAdListener(NativeAdListener listener)
        - InMobiNative.NativeAdListener
        - InMobiBanner
            public void setListener(BannerAdListener listener)
        - InMobiBanner.BannerAdListener
        - InMobiInterstitial
            public InMobiInterstitial(Context context, long placementId, InterstitialAdListener2 listener)
            public void setInterstitialAdListener(InterstitialAdListener2 listener)
        - InMobiInterstitial.InterstitialAdListener2
- Bug fixes and performance improvements

## Requirements
- Android 4.0.1 (API level 15) and higher
- android-support-v4.jar, r28
- android-recyclerview-v7.jar,r28
- Picasso Library (picasso-2.71828.jar - available on JCenter)
- Chrome Custom tabs dependency (needs to be added in build.gradle at app level)
- **Recommended** Google Play Services 17.0.0

## License
To view the license for the InMobi SDK, see [here](https://github.com/InMobi/sdk-sample-code-android/blob/master/sdk/License.txt). To view the terms of service, visit [https://inmobi.com/terms-of-service](http://inmobi.com/terms-of-service/). 
The code for the sample apps is provided under the Apache 2.0 License.

