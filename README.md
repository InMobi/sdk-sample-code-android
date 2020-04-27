InMobi SDK for Android
======================

Modified: 24 April, 2020

SDK Version: 9.0.5

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
    implementation 'com.inmobi.monetization:inmobi-ads:9.0.5'
}
```

**Download from the support portal**

To download the latest SDK as a AAR, please visit [http://inmobi.com/sdk](https://inmobi.com/sdk).

**To continue integrating with the InMobi SDK, please see the [Integration Guidelines](https://support.inmobi.com/monetize/android-guidelines/) for Android.**

## New in this version
    - MAX Audience Bidding Support
    - Custom Audience Bidding Support
    - MoPub Audience Bidding Support
    - Several Threading Optimizations and Improvements
    - Bug Fixes
    - Interface changes
        -APIs Added
            -BannerAdEventListener
                -public void onAdFetchSuccessful(@NonNull InMobiBanner ad, @NonNull AdMetaInfo info)
                -public void onAdLoadSucceeded(@NonNull InMobiBanner ad, @NonNull AdMetaInfo info)
                -public void onAdFetchFailed(@NonNull InMobiBanner ad, @NonNull InMobiAdRequestStatus status)
            -InterstitialAdEventListener
                -public void onAdFetchSuccessful(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info)
                -public void onAdLoadSucceeded(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info)
                -public void onAdDisplayed(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info)
                -public void onAdFetchFailed(@NonNull InMobiInterstitial ad, @NonNull InMobiAdRequestStatus status)
            -NativeAdEventListener
                -public void onAdFetchSuccessful(@NonNull InMobiNative ad, @NonNull AdMetaInfo info)
                -public void onAdLoadSucceeded(@NonNull InMobiNative ad, @NonNull AdMetaInfo info)
            -PreloadManager
                -void preload()
                -void load()
            -InMobiBanner
                -@NonNull public PreloadManager getPreloadManager()
            -InMobiInterstitial
                -@NonNull public PreloadManager getPreloadManager()
        - APIs Deprecated
            -BannerAdEventListener
                -public void onAdLoadSucceeded(@NonNull InMobiBanner ad)
            -InterstitialAdEventListener
                -public void onAdLoadSucceeded(@NonNull InMobiInterstitial ad)
                -public void onAdReceived(@NonNull InMobiInterstitial ad)
                -public void onAdDisplayed(@NonNull InMobiInterstitial ad)
            -NativeAdEventListener
                -public void onAdLoadSucceeded(@NonNull InMobiNative ad)
                -public void onAdReceived(@NonNull InMobiNative ad)
            -InMobiBanner
                -public JSONObject getAdMetaInfo()
                -public String getCreativeId()
            -InMobiInterstitial
                -public JSONObject getAdMetaInfo()
                -public String getCreativeId()
            -InMobiNative
                -public JSONObject getAdMetaInfo()
                -public String getCreativeId()


## Requirements
- Android 4.0.1 (API level 15) and higher
- android-support-v4.jar, r28
- android-recyclerview-v7.jar,r28
- Picasso Library (picasso-2.71828.jar - available on JCenter)
- Chrome Custom tabs dependency (needs to be added in build.gradle at app level)
- **Recommended** Google Play Services 17.0.0

## License
To view the license for the InMobi SDK, see [here](https://github.com/InMobi/sdk-sample-code-android/blob/master/sdk/licenses/License.txt). To view the terms of service, visit [https://inmobi.com/terms-of-service](http://inmobi.com/terms-of-service/).
The code for the sample apps is provided under the Apache 2.0 License.

