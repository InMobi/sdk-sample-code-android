InMobi SDK for Android
======================

Modified: 20 July, 2022

SDK Version: 10.0.8

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
    implementation 'com.inmobi.monetization:inmobi-ads:10.0.8'
}
```

**Download from the support portal**

To download the latest SDK as a AAR, please visit [http://inmobi.com/sdk](https://inmobi.com/sdk).

**To continue integrating with the InMobi SDK, please see the [Integration Guidelines](https://support.inmobi.com/monetize/android-guidelines/) for Android.**

## New in this version
    • Added Support for Publisher callback - onAdImpression
    • Added Support for Contextual App Targeting
    • Added Support for Picasso version 2.8
    • Added Support for Swish Folders
    • Bug fixes
    • Interface Changes
            - APIs Added
                    • BannerAdEventListener
                        public void onAdImpression(@NonNull InMobiBanner ad)
                    • InterstitialAdEventListener
                	    public void onAdImpression(@NonNull InMobiInterstitial ad)
                    • NativeAdEventListener
                	    public void onAdImpression(@NonNull InMobiNative ad)

## Requirements
- Android 4.0.1 (API level 15) and higher
- androidx.browser
- androidx.appcompat
- androidx.recyclerview
- Picasso Library (picasso-2.8.jar - available on JCenter)
- **Recommended** Google Play Services 18.0.1

## License
To view the license for the InMobi SDK, see [here](https://github.com/InMobi/sdk-sample-code-android/blob/master/sdk/licenses/License.txt). To view the terms of service, visit [https://inmobi.com/terms-of-service](http://inmobi.com/terms-of-service/).
The code for the sample apps is provided under the Apache 2.0 License.

