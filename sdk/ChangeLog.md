InMobi Monetization SDK ChangeLog for Android
=============================================

## Build 10.0.3 [28/January/2022]
    • Updated Android Ads License
    • Removed MOAT Viewability support
    • Ad Click Improvements (Support for direct applinks and deeplinks)
    • Crash fixes

## Build 10.0.2 [21/January/2022]
    • Updated Android Ads License
    • Bug fixes and Improvements
    • Removed MOAT Viewability support
    • Ad Click Improvements (Support for direct applinks and deeplinks)

## Build 10.0.1 [19/October/2021]
    • Migrated OMSDK to 1.3
    • Audio Focus in Native Ads
    • Interface Changes
            - APIs Added
                   • InMobiBanner
                        public void setContentUrl(@NonNull String contentUrl)
                   • InMobiInterstitial
                        public void setContentUrl(@NonNull String contentUrl)
                   • InMobiNative
                        public void setContentUrl(@NonNull String contentUrl)
                   • InMobiSdk
                        public static boolean isSDKInitialized()
    • Upgraded Mediation Support for  Facebook SDK 6.7.0 and AdMob SDK 20.4.0.
    • Bug fixes and improvements

## Build 9.2.1 [08/September/2021]
    • Added Partner provided GDPR consent support
    • Crash fixes

## Build 9.2.0 [06/July/2021]
    • Landing page opening management
    • Native support for click and impression tracking
    • MavenCentral auto fetch mandatory dependency
    • Bug fixes and improvements

## Build 9.1.9 [26/April/2021]
    • Unified ID Bug fix

## Build 9.1.7 [26/March/2021]
    • Unified ID support
    • AdPods support
    • Bug fixes and improvements
    
## Build 9.1.6 [16/February/2021]
    • Migrated to AndroidX
    • Bug fixes and improvements
    • Refactored Audience Bidding Support
    • Interface changes
                - APIs Added
                    • InMobiSdk
                        public static String getToken(@Nullable Map<String, String> extras, @Nullable String keywords)
                        public static String getToken()

## Build 9.1.1 [19/October/2020]
    • Bug fixes and improvements
    • Update support for the following mediation adapters
        • Facebook SDK v6.1.0
        • AdMob SDK v19.4.0

## Build 9.1.0 [18/September/2020]
    • Support for Android 11
    • Support for IAB TCF 2.0 consent
    • Improvements and Bug fixes
    
## Build 9.0.9 [25/August/2020]
    • Improvements and Bug fixes

## Build 9.0.8 [13/August/2020]
    • Critical bug fix for Ad Quality

## Build 9.0.7 [08/June/2020]
    • Critical bug fixes and optimizations

## Build 9.0.6 [08/May/2020]
    • Bug Fixes for MAX Audience Bidder & WebView

## Build 9.0.5 [24/April/2020]
    • MAX Audience Bidding Support
    • Custom Audience Bidding Support
    • MoPub Audience Bidding Support
    • Several Threading Optimizations and Improvements
    • Bug Fixes
    • Interface changes
            - APIs Added
                • BannerAdEventListener
                    public void onAdFetchSuccessful(@NonNull InMobiBanner ad, @NonNull AdMetaInfo info)
                    public void onAdLoadSucceeded(@NonNull InMobiBanner ad, @NonNull AdMetaInfo info)
                    public void onAdFetchFailed(@NonNull InMobiBanner ad, @NonNull InMobiAdRequestStatus status)
                • InterstitialAdEventListener
                    public void onAdFetchSuccessful(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info)
                    public void onAdLoadSucceeded(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info)
                    public void onAdDisplayed(@NonNull InMobiInterstitial ad, @NonNull AdMetaInfo info)
                    public void onAdFetchFailed(@NonNull InMobiInterstitial ad, @NonNull InMobiAdRequestStatus status)
                • NativeAdEventListener
                    public void onAdFetchSuccessful(@NonNull InMobiNative ad, @NonNull AdMetaInfo info)
                    public void onAdLoadSucceeded(@NonNull InMobiNative ad, @NonNull AdMetaInfo info)
                • PreloadManager
                    void preload()
                    void load()
                • InMobiBanner
                    @NonNull public PreloadManager getPreloadManager()
                • InMobiInterstitial
                    @NonNull public PreloadManager getPreloadManager()
            - APIs Deprecated
                • BannerAdEventListener
                    public void onAdLoadSucceeded(@NonNull InMobiBanner ad)
                • InterstitialAdEventListener
                    public void onAdLoadSucceeded(@NonNull InMobiInterstitial ad)
                    public void onAdReceived(@NonNull InMobiInterstitial ad)
                    public void onAdDisplayed(@NonNull InMobiInterstitial ad)
                • NativeAdEventListener
                    public void onAdLoadSucceeded(@NonNull InMobiNative ad)
                    public void onAdReceived(@NonNull InMobiNative ad)
                • InMobiBanner
                    public JSONObject getAdMetaInfo()
                    public String getCreativeId()
                • InMobiInterstitial
                    public JSONObject getAdMetaInfo()
                    public String getCreativeId()
                • InMobiNative
                    public JSONObject getAdMetaInfo()
                    public String getCreativeId()


## Build 9.0.4 [03/March/2020]
    • Improvements and bug fixes
    • Added support for InMobi Initialization callback
    • Interface changes
        - APIs Added
            • InMobiSdk
                public static void init(@NonNull final Context context, @NonNull @Size(min = 32, max = 36) String accountId, @Nullable JSONObject consentObject, @Nullable final SdkInitializationListener sdkInitializationListener)
            • SdkInitializationListener
                void onInitializationComplete(@Nullable Error error)
        - APIs Deprecated
            • InMobiSdk
                public static @InitializationStatus String init(@NonNull final Context context, @NonNull @Size(min = 32, max = 36) String accountId)
                public static @InitializationStatus String init(@NonNull final Context context, @NonNull @Size(min = 32, max = 36) String accountId, @Nullable JSONObject consentObject)

## Build 9.0.3 [14/February/2020]
    • Support for Open Auction

## Build 9.0.2 [27/January/2020]
    • Added support for success/failure status for InMobi Initialization.
    • Proactive detection of abnormal network calls by fraudulent creatives.
    • Improvements and Bug Fixes.
    • Interface Changes
            - APIs Updated
                • InMobiSdk
                    public static @InitializationStatus String init(@NonNull final Context context, @NonNull @Size(min = 32, max = 36) String accountId)
                    public static @InitializationStatus String init(@NonNull final Context context, @NonNull @Size(min = 32, max = 36) String accountId, @Nullable JSONObject consentObject)

## Build 9.0.1 [25/October/2019]
    • Add support for IAB GDPR consent string
    • Update OMSDK to v1.2.19
    • Banner refresh support from SSUI
    • Support for Facebook Audience Network 5.5
    • Bug Fixes for SDK and AudienceBidder Plugin

## Build 9.0.0 [20/September/2019]
    • Modular SDK
        - Added an ability to integrate Mediation as a separate module.
    • Added support for Android 10
    • Support for Mopub 5.8
    • Interface Changes
        - APIs added
            • AerServTransactionInformation
                public String getCreativeID()
            • InMobiBanner
                public void destroy()
        - APIs removed
            • InMobiNative
                public InMobiNative(Context context, long placementId, NativeAdListener listener)
                public void setNativeAdListener(NativeAdListener listener)
            • InMobiNative.NativeAdListener
            • InMobiBanner
                public void setListener(BannerAdListener listener)
            • InMobiBanner.BannerAdListener
            • InMobiInterstitial
                public InMobiInterstitial(Context context, long placementId, InterstitialAdListener2 listener)
                public void setInterstitialAdListener(InterstitialAdListener2 listener)
            • InMobiInterstitial.InterstitialAdListener2
    • Bug fixes and performance improvements



## Build 8.2.1 [21/August/2019]
    • Bug Fixes for SDK and AudienceBidder Plugin

## Build 8.2.0 [02/Aug/2019]
    • Chrome Custom tabs support
    • Thread Optimizations
    • Bug Fixes for SDK and AudienceBidder Plugin

## Build 8.1.3 [05/July/2019]
    • Audience Bidder support for DFP
    • Bug Fixes

## Build 8.1.2 [13/Jun/2019]
    • Updated AdColony SDK support to 3.3.10
    • Updated Audience Network (Facebook) support to 5.3.1

## Build 8.1.1 [28/May/2019]
    • Improved Stability with crash fixes
    • Thread and Memory Optimizations
    • Support for new Picasso version 2.71828

    ### API added
        • NativeAdEventListener
            public void onAdReceived(InMobiNative ad)

## Build 8.1.0 [15/May/2019]
    • AudienceBidder
        • Added support for AppNexus
        • Added keyword implementation for MoPub
    • Added DFP plugin

## Build 8.0.9 [24/Apr/2019]
    • Bug fixes

## Build 8.0.8 [17/Apr/2019]
    • Bug fixes

## Build 8.0.7 [05/Apr/2019]
    • Bug fixes
    • Updated AudienceBidder to v1.0.1
        • Support for Amazon Publsiher Service
        • Support for granular keywords
        • Fix issue with updateBid not working with MoPub's refresh
        • Replaced source file with a aar
        • Change in API (see documentation)

## Build 8.0.5 [11/Mar/2019]
    • Added InMobi's Audience Bidder for MoPub
    • Update support for the following mediation adapters
        • AdColony SDKv 3.3.7
        • AdMob SDKv 17.1.1
        • AppLovin SDKv 9.1.0
        • AppNext SDKv 2.4.4.472
        • Audience Network (Facebook) SDKv 5.1.0
        • Chartboost SDKv 7.3.1
        • Flurry (Yahoo) SDKv 11.4.0
        • MoPub SDKv 5.4.1
        • MyTarget SDKv 5.3.1
        • Oath (AOL) SDKv 6.8.2
        • Unity SDKv 3.0.0
        • Vungle SDKv 6.3.24
    • Deprecated Tremor support
    • Deprecated Flurry (Yahoo) Banner
    • Added support for additional reporting for AppLovin and Chartboost
    • Bug fixes
## Build 8.0.1 [28/Nov/2018]
    • Bug Fixes

## Build 8.0.0 [17/Oct/2018]
    • Unification of InMobi SDK and AerServ SDK

## Build 7.2.1 [20/Sept/2018]
    • Hot-fix for Android

## Build 7.2.0 [12/Sept/2018]
    • Added support for Android P
    • Added Video events for Native Ads
    • Block auto-redirection of ads without user interaction
    • Banner XML integration placementId parameter expects "plid-"
    • Bug Fixes

    ### Interface changes
        • APIs added:
                • InMobiNative
                    public InMobiNative(Context context, long placementId, NativeAdEventListener listener)
                    public void setListener(NativeAdEventListener listener)
                • InMobiBanner
                    public void setListener(BannerAdEventListener listener)
                • InMobiInterstitial
                    public InMobiInterstitial(Context context, long placementId, InterstitialAdEventListener listener)
                    public void setListener(InterstitialAdEventListener listener)
                • BannerAdEventListener
                     public void onAdLoadSucceeded(InMobiBanner ad)
                     public void onAdLoadFailed(InMobiBanner ad, InMobiAdRequestStatus status)
                     public void onAdClicked(InMobiBanner ad, Map<Object, Object> params)
                     public void onAdDisplayed(InMobiBanner ad)
                     public void onAdDismissed(InMobiBanner ad)
                     public void onUserLeftApplication(InMobiBanner ad)
                     public void onRewardsUnlocked(InMobiBanner ad, Map<Object, Object> rewards)
                • InterstitialAdEventListener
                    public void onAdLoadSucceeded(InMobiInterstitial ad)
                    public void onAdLoadFailed(InMobiInterstitial ad, InMobiAdRequestStatus status)
                    public void onAdReceived(InMobiInterstitial ad)
                    public void onAdClicked(InMobiInterstitial ad, Map<Object, Object> params)
                    public void onAdWillDisplay(InMobiInterstitial ad)
                    public void onAdDisplayed(InMobiInterstitial ad)
                    public void onAdDisplayFailed(InMobiInterstitial ad)
                    public void onAdDismissed(InMobiInterstitial ad)
                    public void onUserLeftApplication(InMobiInterstitial ad)
                    public void onRewardsUnlocked(InMobiInterstitial ad, Map<Object, Object> rewards)
                • NativeAdEventListener
                    public void onAdLoadSucceeded(InMobiNative ad)
                    public void onAdLoadFailed(InMobiNative ad, InMobiAdRequestStatus requestStatus)
                    public void onAdFullScreenDismissed(InMobiNative ad)
                    public void onAdFullScreenWillDisplay(InMobiNative ad)
                    public void onAdFullScreenDisplayed(InMobiNative ad)
                    public void onUserWillLeaveApplication(InMobiNative ad)
                    public void onAdImpressed(InMobiNative ad)
                    public void onAdClicked(InMobiNative ad)
                    public void onAdStatusChanged(InMobiNative nativeAd)
                • VideoEventListener
                    public void onVideoCompleted(InMobiNative ad)
                    public void onVideoSkipped(InMobiNative ad)
                    public void onAudioStateChanged(InMobiNative inMobiNative, boolean isMuted)

        • Deprecated API:
                • InMobiNative
                    public InMobiNative(Context context, long placementId, NativeAdListener listener)
                    public void setNativeAdListener(NativeAdListener listener)
                • InMobiNative.NativeAdListener
                • InMobiBanner
                    public void setListener(BannerAdListener listener)
                • InMobiBanner.BannerAdListener
                • InMobiInterstitial
                    public InMobiInterstitial(Context context, long placementId, InterstitialAdListener2 listener)
                    public void setInterstitialAdListener(InterstitialAdListener2 listener)
                • InMobiInterstitial.InterstitialAdListener2



## Build 7.1.1 [17/May/2018]
    • Fixed memory leak in InmobiBanner when multiple instances are created per activity

## Build 7.1.0 [09/April/2018]
    • Added support for GDPR compliance
    • Added skip callback to Native Ads
    • Fixed refresh issue with InMobiBanner
    • Upgraded google play services to 11.8.0
    • Bug Fixes

    ### Interface changes
        • APIs added:
                • InMobiSdk class
                    public static void init(final Context context, String accountId, JSONObject consentObject)
                    public static void updateGDPRConsent(JSONObject consentObject)

                • InMobiNative.NativeAdListener
                    public void onUserSkippedMedia(InMobiNative nativeAd)

            • APIs removed:
                • InMobiSdk class
                    public enum Ethnicity
                    public static void setEthnicity(Ethnicity ethnicity)
                    public enum HouseHoldIncome
                    public static void setHouseHoldIncome(HouseHoldIncome houseHoldIncome)
                    public static void setIncome(int income)
                    public static void setNationality(String nationality)

## Build 7.0.4 [12/Feb/2018]
    • Bug fixes

## Build 7.0.2 [11/Dec/2017]
    • Bug fixes

## Build 7.0.1 [09/Nov/2017]
    • Bug fixes

    ### Interface changes
            • APIs added:
                • InMobiNative class
                    public View getPrimaryViewOfWidth(Context context, View convertView, ViewGroup parent, int viewWidthInPixels);

            • Deprecated API:
                • InMobiNative class
                    public View getPrimaryViewOfWidth(View convertView, ViewGroup parent, int viewWidthInPixels);

## Consolidated change-logs for v6.9.0, v6.9.1 and 7.0.0 [15/Sep/2017]
    • Introduction to Brand new Native Ad solution (Merged InMobiNative & InMobiNativeStrand):
           * Native ads can now support videos, carousel and static Ad types
           * Prefetching Native Ads for better load times
           * Better way to customize the Ad to match the App’s native environment
    • Interactive Video Ad Experience:
           * Brand new video Ad experience to increase user engagement in Interstitial Ad Format
    • Rich End Cards:
           * Engaging end-cards are now supported via Rich-Media End-cards
    • Support for GIF images
    • Enhanced Metrics
    • Stability and performance improvements
    • Dex count improvements
    • Performance improvements
    • Bug fixes


    ### Interface changes
            • APIs added:
                • InMobiNative class
                    public InMobiNative(Context context, long placementId, NativeAdListener listener);
                    public showOnLockScreen(@NonNull LockScreenListener lockScreenListener);
                    public void takeAction();
                    public View getPrimaryViewOfWidth(View convertView, ViewGroup parent, int viewWidthInPixels);
                    public JSONObject getCustomAdContent();
                    public String getAdTitle();
                    public String getAdDescription();
                    public String getAdIconUrl();
                    public String getAdLandingPageUrl();
                    public String getAdCtaText();
                    public float getAdRating();
                    public boolean isAppDownload();
                    public boolean isReady();
                    public void destroy();
                    public void setDownloaderEnabled(final boolean downloaderEnabled);
                    public Downloader getDownloader();

                • InMobiNative.NativeAdListener
                    void onAdFullScreenDismissed(InMobiNative nativeAd);
                    void onAdFullScreenWillDisplay(InMobiNative nativeAd);
                    void onAdFullScreenDisplayed(InMobiNative nativeAd);
                    void onUserWillLeaveApplication(InMobiNative nativeAd);
                    void onAdImpressed(InMobiNative nativeAd);
                    void onAdClicked(InMobiNative nativeAd);
                    void onMediaPlaybackComplete(InMobiNative nativeAd);
                    void onAdStatusChanged(InMobiNative nativeAd);

                • InMobiNative.LockScreenListener
                    void onActionRequired(InMobiNative nativeAd);

                • InMobiNative.Downloader
                    public int getDownloadProgress();
                    public int getDownloadStatus();

            • APIs removed
                • InMobiNative class
                    public InMobiNative(long placementId, NativeAdListener listener)
                    public InMobiNative(Activity activity, long placementId, NativeAdListener listener);
                    public final Object getAdContent();
                    public static void bind(final View view, final InMobiNative inMobiNative);
                    public static void unbind(final View view);
                    public final void reportAdClick(Map<String, String> extras);

                • InMobiNative.NativeAdListener
                    void onAdDismissed(InMobiNative ad);
                    void onAdDisplayed(InMobiNative ad);
                    void onUserLeftApplication(InMobiNative ad);

                • InMobiNative.NativeAdRequestListener

                • InMobiNativeStrand

## Build 6.2.4 [17/July/2017]
    • Bug fixes

## Build 6.2.3 [02/June/2017]
    • Bug fixes

## Build 6.2.2 [30/May/2017]
    • Bug fixes

## Build 6.2.1 [18/Apr/2017]
    • Infeed video fixes

## Build 6.2.0 [06/Apr/2017]
    • Support for monetizing the lock screen
    • Fix for an issue while loading resource in WebView.
    • Making Picasso and RecyclerView mandatory for Interstitial and Native Strands Ad formats.

    ### Interface changes
        • APIs added:
            • InMobiSdk class
               public static void init (Context context, String accountId);

            • InMobiAdRequest
               public enum MonetizationContext;

            • InMobiAdRequest.Builder
               public Builder(long placementId);
               public Builder setMonetizationContext(MonetizationContext monetizationContext);
               public Builder setSlotSize(int widthInDp, int heightInDp);
               public Builder setKeywords(String keywords);
               public Builder setExtras(Map<String, String> extras);
               public InMobiAdRequest build();

            • InMobiBanner
                public InMobiBanner(Context context, AttributeSet attributeSet);
                public InMobiBanner(Context context, long placementId);
                public static void requestAd(Context context, InMobiAdRequest adRequest, BannerAdRequestListener listener);
                public void load(Context context);
                public void pause();
                public void resume();

            • InMobiBanner.BannerAdRequestListener
                public void onAdRequestCompleted(InMobiAdRequestStatus status, InMobiBanner inMobiBanner);

            • InMobiInterstitial
                public InMobiInterstitial(Context context, long placementId, InterstitialAdListener2 listener);
                public static void requestAd(Context context, InMobiAdRequest adRequest, InterstitialAdRequestListener interstitialAdRequestListener);

            • InMobiInterstitial.InterstitialAdRequestListener
                public void onAdRequestCompleted(InMobiAdRequestStatus status, InMobiInterstitial inMobiInterstitial);

            • InMobiNative
                public InMobiNative(long placementId, NativeAdListener listener);
                public static void requestAd(Context context, InMobiAdRequest adRequest, NativeAdRequestListener listener);
                public void load(Context context);

            • InMobiNative.NativeAdRequestListener
                public void onAdRequestCompleted(InMobiAdRequestStatus status, InMobiNative inMobiNative);

            • InMobiNativeStrand
                public InMobiNativeStrand(Context context, long placementId, NativeStrandAdListener listener);
                public static void requestAd(Context context,InMobiAdRequest adRequest, NativeStrandAdRequestListener listener);
                public void load(Context context);
                public void pause();
                public void resume();

            • InMobiNativeStrand.NativeStrandAdRequestListener
                public void onAdRequestCompleted(InMobiAdRequestStatus status, InMobiNativeStrand nativeStrandAd);


## Build 6.1.1 [16/Feb/2017]
    • Hot-fix for impression tracking for Native fullscreen video


## Build 6.1.0 [02/Feb/2017]
    • Added support for in-feed video ads
    • Improvements to video experience
    • Improved handling of intent schemes and fallback URLs
    • Bug fixes
    • APIs removed
      • InMobiStrandAdapter
            public InMobiStrandAdapter(Context context, long placementId, Adapter originalAdapter, InMobiStrandPositioning.InMobiClientPositioning clientPositioning)
            public InMobiStrandAdapter(Activity activity, final long placementId,Adapter originalAdapter,InMobiStrandPositioning.InMobiClientPositioning clientPositioning)
            public void clearStrands()
            public void destroy()
            public int getAdjustedPosition(int originalPosition)
            public int getCount()
            public Object getItem(int position)
            public long getItemId(int position)
            public int getItemViewType(int position)
            public int getOriginalPosition(int position)
            public View getView(int position, View view, ViewGroup viewGroup)
            public int getViewTypeCount()
            public boolean hasStableIds()
            public void insertItem(int originalPosition)
            public boolean isEmpty()
            public boolean isStrand(int position)
            public void load()
            public void refreshAds(ListView listView)
            public void removeItem(int originalPosition)
            public void setExtras(Map<String, String> extras)
            public void setKeywords(String keywords)
            public void setListener(InMobiStrandAdapter.NativeStrandAdListener listener)
            public void setOnClickListener(ListView listView, AdapterView.OnItemClickListener listener)
            public void setOnItemLongClickListener(ListView listView, AdapterView.OnItemLongClickListener listener)
            public void setOnItemSelectedListener(ListView listView, AdapterView.OnItemSelectedListener listener)
            public void setSelection(ListView listView, int originalPosition)
            public void smoothScrollToPosition(ListView listView, int originalPosition)


      • InMobiStrandAdapter.NativeStrandAdListener
            public void onAdLoadSucceeded(int position)
            public void onAdRemoved(int position)


## Build infeed-beta-V2 [29/Dec/2016]
    • Bug fix related to firing of beacons

## Build infeed-beta-V1 [13/Dec/2016]
    • InMobi SDK now supports video in infeed format

## Build 6.0.4 [24/Nov/2016]
    • Enhancements to End-Card experience
    • Support for auto-close fullscreen native video ads
    • Bug fixes

## Build 6.0.3 [03/Nov/2016]
    • Hot-fix


## Build 6.0.2 [28/Oct/2016]
    • Hot-fix for crashes


## Build 6.0.1 [14/Oct/2016]
    • Fixed a crash due to missing Google Play Services

## Build 6.0.0 [28/Sep/2016]
A brand new SDK that turbo-charges performance, improves stability and introduces a fully re-engineered fullscreen video experience that aims to deliver a truly native, stall-free experience that should improve render rates. What's more, SDK 6.0.0 for Android is compliant with Android-N, the latest version of Android, and is also a drop-in replacement for publishers who are already integrating with SDK 5.x.x for Android. The complete list of changes follows.

### Interface changes
    • APIs added:
        • InMobiSdk class
           public static void init (Activity activity, String accountId)

        • InMobiBanner
            public InMobiBanner(Activity activity, AttributeSet attributeSet)
            public InMobiBanner(Activity activity, long placementId)

        • InMobiInterstitial
            public InMobiInterstitial(Activity activity, long placementId, InterstitialAdListener2 listener)

        • InMobiNative
            public InMobiNative(Activity activity, long placementId, NativeAdListener listener)
            public void setNativeAdEventListener(NativeAdEventsListener listener)

        • InMobiStrandAdapter
            public InMobiStrandAdapter(@NonNull final Activity activity, final long placementId,
                                           @NonNull final Adapter originalAdapter,
                                           @NonNull final InMobiStrandPositioning.InMobiClientPositioning clientPositioning)

        • InMobiNativeStrand
            public InMobiNativeStrand(Activity activity, long placementId, NativeStrandAdListener listener)

    • APIs deprecated:
        • InMobiSdk class
           public static void init (Context context, String accountId)

        • InMobiBanner
            public InMobiBanner(Context context, AttributeSet attributeSet)
            public InMobiBanner(Context context, long placementId)

        • InMobiInterstitial
            public InMobiInterstitial(Context context, long placementId, InterstitialAdListener listener)

        • InMobiNative
            public InMobiNative(long placementId, NativeAdListener listener)

        • InMobiStrandAdapter
            public InMobiStrandAdapter(@NonNull final Context context, final long placementId,
                                           @NonNull final Adapter originalAdapter,
                                           @NonNull final InMobiStrandPositioning.InMobiClientPositioning clientPositioning)

        • InMobiNativeStrand
            public InMobiNativeStrand(Context context, long placementId, NativeStrandAdListener listener)

### Other improvements and changes
    • Improved memory collection for Native Storyboards ad units
    • Fix a crash due to incorrect handling of cleared references
    • Fix a crash with the SDK due to incorrect encoding of server response
    • Fix issues with interstitial ads on devices running 4.4.x
    • Fix an issue with Video ads on Android KITKAT devices when integrating
      via Unity plugin
    • Improve debugging support for publishers and support engineers
    • Several bug fixes
    • Drop support for API levels below 15

### Known and Open issues
    • Putting app in background when video ad is playing in multi-window mode in
      Android N does not pause video playback
    • Fullscreen native video player does not scale viewport for multi-window mode
      in Android N
    • Impression tracking issues for Native ads displayed in Notification pane
    • Impression tracking issues for Native ads in certain scenarios in multi-window
      mode in Android N
    • No support for adding Native Storyboards ads on the lock screen


## Build 5.3.1 [16/May/2016]
    • Fixed an issue with ESET marking the InMobi SDK as "potentially unsafe"
    • Fixed crashes while closing fullscreen ads
    • Fixed a crash on Android 4.4.2
    • Fixed a crash if app starts when a WebView update is in progress (for Android 5.0+)
    • Fixed an issue with click reporting for expired campaigns
    • Fixed an issue with ad layouts for Native Storyboards ad units
    • Added missing support to set keywords for Native Storyboards ad requests
    • Added support for Google Play Services version 8.4.0


## Build 5.3.0 [07/Apr/2016]

    • Introduced a new Ad Format - Native Storyboards.
    • Fix for Interstitial & Rewarded video being played under the Unity Application window on Android 4.4.(Issue was observed starting SDK - 5.2.2)
    • APIs added:

        • InMobiNativeStrand
            public InMobiNativeStrand(Context context, long placementId, InMobiNativeStrand.NativeStrandAdListener listener)
            public void load()
            public void setExtras(Map<String, String> extras)
            public View getStrandView(View convertView, ViewGroup parent)
            public void destroy()

        • InMobiNativeStrand.NativeStrandAdListener
            public void onAdLoadSucceeded(InMobiNativeStrand nativeStrand)
            public void onAdClicked(InMobiNativeStrand nativeStrand)
            public void onAdImpressed(InMobiNativeStrand nativeStrand)
            public void onAdLoadFailed(InMobiNativeStrand nativeStrand, InMobiAdRequestStatus requestStatus)

        • InMobiStrandPositioning.InMobiClientPositioning
            public InMobiClientPositioning()
            public InMobiClientPositioning addFixedPosition(int position)
            public InMobiClientPositioning enableRepeatingPositions(int interval)

        • InMobiStrandAdapter
            public InMobiStrandAdapter(Context context, long placementId, Adapter originalAdapter, InMobiStrandPositioning.InMobiClientPositioning clientPositioning)
            public void clearStrands()
            public void destroy()
            public int getAdjustedPosition(int originalPosition)
            public int getCount()
            public Object getItem(int position)
            public long getItemId(int position)
            public int getItemViewType(int position)
            public int getOriginalPosition(int position)
            public View getView(int position, View view, ViewGroup viewGroup)
            public int getViewTypeCount()
            public boolean hasStableIds()
            public void insertItem(int originalPosition)
            public boolean isEmpty()
            public boolean isStrand(int position)
            public void load()
            public void refreshAds(ListView listView)
            public void removeItem(int originalPosition)
            public void setExtras(Map<String, String> extras)
            public void setKeywords(String keywords)
            public void setListener(InMobiStrandAdapter.NativeStrandAdListener listener)
            public void setOnClickListener(ListView listView, AdapterView.OnItemClickListener listener)
            public void setOnItemLongClickListener(ListView listView, AdapterView.OnItemLongClickListener listener)
            public void setOnItemSelectedListener(ListView listView, AdapterView.OnItemSelectedListener listener)
            public void setSelection(ListView listView, int originalPosition)
            public void smoothScrollToPosition(ListView listView, int originalPosition)

        • InMobiStrandAdapter.NativeStrandAdListener
            public void onAdLoadSucceeded(int position)
            public void onAdRemoved(int position)

## Build 5.2.3 [07/Mar/2016]

    • Fix an issue that prevented SDK initialization on Android 4.2.2

## Build 5.2.2 [29/Feb/2016]

    • Fix several crashes
    • Fix an issue with prompts for location access while initializing the SDK
    • Fix an issue with WebView deadlock on Android 4.4.2

## Build 5.2.1 [08/Feb/2016]

    • Fix crashes
    • Bug fixes for orientation lock by creative and missing viewable change
      events on certain devices

## Build 5.2.0 [11/Jan/2016]

    • Detection of fraud events on the creative
    • Bug fixes related to older version of WebViews and multi-threading

## Build 5.1.1 [02/Dec/2015]

    • Fixed minor bugs and crashes

## Build 5.1.0 [19/Nov/2015]

    • SDK is now Android M compatible and handles user permissions as per the new Android permission model.
    • Improved video ads performance.
    • Fixed minor bugs

## Build 5.0.1 [19/Oct/2015]

    • Fixes a crash happening on some devices when launching the app

## Build 4.5.7 [9/Oct/2015]

    • Fixed a vulnerability issue with WebView usage

## Build 5.0.0 [07/Oct/2015]

- APIs added:
        • InMobiSdk class
          public static void init (Context context, String accountId)

        • InMobiBanner class
          public interface BannerAdListener
          public enum AnimationType
          public InMobiBanner(Context context, long placementId)
          public void load()
          public void setExtras(Map<String, String> extras)
          public void setListener(BannerAdListener listener)
          public void setEnableAutoRefresh(boolean enabled)
          public void setAnimationType(AnimationType animationType)

        • InMobiInterstitial class
          public interface InterstitialAdListener
          public InMobiInterstitial(Context context, long placementId, InterstitialAdListener listener)
          public void load()
          public boolean isReady()
          public void show(final int enterAnimationResourcedId, final int exitAnimationResourceId)
          public void setExtras(Map<String, String> extras)

        • InMobiNative class
          public interface NativeAdListener
          public InMobiNative(long placementId, NativeAdListener listener)
          public final void load()
          public final void resume()
          public final void pause()
          public static final void bind(View view, InMobiNative nativeAd)
          public static final void unbind(View view)
          public final void reportAdClickAndOpenLandingPage(Map<String, String> extras)
          public final void reportAdClick(Map<String, String> extras)
          public final void setExtras(Map<String, String> extras)

        • InMobiCustomNative class
          public InMobiCustomNative(long placementId, NativeAdListener listener)
          public static void bind(View view, InMobiNative nativeAd, URL impressionTrackerUrl)
          public static void bind(View view, InMobiNative nativeAd, String impressionTrackerJs)
          public final void reportAdClick(URL clickTrackerUrl, Map<String, String> extras)
          public final void reportAdClick(String clickTrackerJs, Map<String, String> extras)
          public final void reportAdClickAndOpenLandingPage(URL clickTrackerUrl, Map<String, String> extras)
          public final void reportAdClickAndOpenLandingPage(String clickTrackerJs, Map<String, String> extras)

- APIs updated:
          Moved APIs from InMobi class to InMobiSdk class
          Moved APIs from IMBanner class to InMobiBanner class
          Moved APIs from IMInterstitial class to InMobiInterstitial class
          Moved APIs from IMNative class to InMobiNative class

- APIs removed:
          InMobi class
          public static void setDeviceIdMask(int mask)
          public static void initialize(Context context, final String appId)
          public static void setSexualOrientation(SexualOrientation sexualOrientation)
          public static void setHasChildren(HasChildren hasChildren)
          public static void setMaritalStatus(MaritalStatus maritalStatus)

          IMBanner class
          public IMBanner(Activity activity, String appId, int adSize)
          public IMBanner(Activity activity, long slotId)
          public void loadBanner()
          public void stopLoading()
          public void setIMBannerListener(IMBannerListener listener)
          public void destroy()
          public void setAdSize(int adSize)
          public void setSlotId(long slotId)
          public void setAppId(String appId)
          public void setRequestParams(Map<String, String> requestParams)
          public void setRefTagParam(String key, String value)

          IMInterstitial class
          public enum State
          public State getState()
          public void loadInterstitial()
          public void stopLoading()
          public void setIMInterstitialListener(IMInterstitialListener listener)
          public void destroy()
          public void setAppId(String appId)
          public void setSlotId(long slotId)
          public void show(long animationTimeInMillis)
          public void setIMIncentivisedListener(IMIncentivisedListener listener)

          IMIncentivisedListener interface

          IMNative class
          public void attachToView(final ViewGroup view)
          public void detachFromView()
          public void handleClick(final HashMap<String, String> params)
          public String getContent()
          public void loadAd()

- Deprecated support for Android API level below 14 and removed support for API level 8
- The minimum interval between two successive load calls should be 20 seconds, otherwise an ad load failure callback with error code EARLY_REFRESH_REQUEST will be fired.
- Analytics support removed
- Major Bug Fixes

## Build 4.5.6 [10/Sep/2015]

• Fixes a crash on Android devices running 4.4

## Build 4.5.5 [22/May/2015]

• Fixes a crash on Android devices running 2.3.7 and below (that is, API level 9)

## Build 4.5.4 [15/May/2015]

• Fixes an issue with rewarded video ads and Instant Play video ads
• Fixes an issue with location collection on devices running Android 4.4
• Fix issues with re-using an interstitial ad for multiple ad requests

## Build 4.5.3 [12/Feb/2015]

• Minor Bug Fixes

## Build 4.5.2 [20/Nov/2014]

• Programmatically created banner ad does not require LayoutParams to be set
• Location access is turned off by default (Enable via your InMobi account)
• Improved handling of SSL certificates
• Improvements to the Conversion tracker module
• Fixed an occasional flickering issue in banner ad when using hardware acceleration
• MRAID video ads display correctly now if the targetSDKVersion is 19+
• Optimized handling of simultaneous multiple ad requests

## Build 4.5.1 [18/Sept/2014]

• Bug Fix for impression counting

## Build 4.5.0 [05/Sept/2014]

• Removed setLocationInquiryAllowed api from InMobi class. Use the publisher portal to configure location targeting.
• Support added for Attribute API
• Remove App Gallery Support

## Build 4.4.3 [30/Jul/2014]

• Compliant with Google Play Advertising Id Policies.

## Build 4.4.2 [16/Jul/2014]

• Added video autoplay support
• Minor bug fixes

## Build 4.4.1 [13/Jun/2014]

• Support added for Google Play advertising id. Google play services jar is mandatory for integration.
• Minor bug fixes

## Build 4.4.0 [04/Jun/2014]

• Introduced Video instant play capability
• Mraid enhancements for orientation lock
• Minor Bug Fixes

## Build 4.3.0 [28/Apr/2014]

• Reward Ads support
• Native Ads sample apps provided in the bundle
• Security related improvements
• Minor bug fixes
• MRAID Enhancements
	- Added support for Calendar Events. Requires permissions "android.permission.READ_CALENDAR" and "android.permission.WRITE_CALENDAR" in android manifest file

## Build 4.4.1 [31/Jan/2014]

• Major bug fixes

## Build 4.1.0 [23/Dec/2013]

• Native Ad Support added
• Minor bug fixes

## Build 4.0.4 [15/Nov/2013]

• Minor bug fix

## Build 4.0.3 [14/Nov/2013]

• Bug Fixes
• Minor fix for call click to action ("android.permission.CALL_PHONE" not required)
• Internal release

## Build 4.0.2 [16/Oct/2013]

• Bug Fixes
• Removed InMobiExtras
• setRefTagParam, setKeywords and setRequestParams API added to IMBanner
• setKeywords and setRequestParams API added to IMInterstitial
• Removed addNetworkExtras and removeNetworkExtras API from IMBanner and IMInterstitial

## Build 4.0.1 [16/Oct/2013]

• Major Bug Fixes
• Internal release

## Build 4.0.0 [11/Sept/2013]

• Analytics support
• Built in AdTracker

## Build 3.7.1 [17/Jun/2013]

• Major Bug fixes

## Build 3.7.0 [14/May/2013]

- Features:
        • Added HTML 5 Video Support
        • MRAID 2.0
        • Added "https" support.
        • Introduced transparent interstitial ad.
        • Added mandatory permission ACCESS_NETWORK_STATE
        • Added optional permission VIBRATE, RECORD_AUDIO and WRITE_EXTERNAL_STORAGE

- New APIs:
        • IMAdView:
            public void destroy();
            public void disableHardwareAcceleration();
            public boolean isModal();
            public LayoutParams getAdViewPosition();

        • IMAdInterstitial:
            public void destroy();
            public void disableHardwareAcceleration();</nowiki>

-Deprecated APIs:
        public void setTestMode(boolean mode)
        public boolean isTestMode()
        public void setAdBackgroundColor(String color)
        public void setAdBackgroundGradientColor(String topColor, String bottomColor)
        public void setAdTextColor(String color)

## Build 3.6.2 [19/Dec/2012]

• Bug fixes (Start auto refresh when app comes to foreground)
• Adding JavaScript annotation as per Android 4.2.

## Build 3.7.0 [31/Oct/2012]

• Disabled hardware acceleration for <code>IMWebview</code> in Android versions 4.0 and later.
• Bug fixes (Proper <code>onShow</code> and <code>onDismiss</code> callbacks).
• In this release, developers need not set the <code>IMAdView</code> width and height based on the ad size. The SDK will set the correct density-independent size for viewing ads based on <code>AdSize</code>.

## Build 3.6.0 [15/Sept/2012]

• Android 4.1 Jelly Bean platform supported.
• Introduction of <code>InMobiCommons</code> SDK. <code>InMobiAdNetwork</code> SDK has dependency on the <code>InMobiCommons</code> SDK. Both should be included in the project build path as mentioned in the integration guidelines. Ensure that the <code>InMobiAdNetwork</code> and <code>InMobiCommons</code> SDK JARs are from the latest release bundle. Refer to JavaDocs on <code>InMobiCommons</code> SDK on the API exposed.
• The InMobi SDK sample application has been renamed as <code>IMAdNetworkSample</code>. Its target Android OS version is 3.2 (API level 13).
• Numerous bug fixes.
• From this release of the SDK, adding the <code>IMAdview</code> to the view hierarchy will not load a new ad immediately. For this, <code>loadNewAd</code> must be called specifically.
• Renamed the <code>adSlot</code> parameter to <code>adSize</code> in <code>IMAdView</code>. '''Caution:''' The <code>adSlot</code> attribute from the XML has been renamed to <code>adSize</code> in SDK v3.6.0. If you do not to make this change in your XML, you will get an <code>android.view.InflateException</code>, with cause as <code>java.lang.IllegalArgumentException</code>, with an appropriate message.

- New APIs:

        <code>IMCommonUtil</code>: New class introduced

         <nowiki>IMAdView:
            public int getAdSize();
            public void setAdSize(int adSize);
            public void stopLoading();</nowiki>

         <nowiki>IMAdInterstitial:
            public void stopLoading();
            public String getAppId()
            public void setAppId(String appId)</nowiki>

         <nowiki>IMAdRequest:
            public void setDateOfBirth(Calendar dateOfBirth);
            public Calendar getDateOfBirth();</nowiki>

- Removed APIs:

        <code>IMSDKUtil</code>: Class removed

         <nowiki>IMAdRequest:
            public void setDeviceIDMask(int mask) (Moved to IMCommonUtil class from InMobiCommons SDK)
            public int getDeviceIDMask() (Moved to IMCommonUtil class from InMobiCommons SDK)
            public Date getDateOfBirth();</nowiki>

         <nowiki>IMAdView:
            public int getAdSlot();
            public void setAdSlot(int adSlot);</nowiki>

- New Error Codes:
        • Added new error codes <code>AD_FETCH_TIMEOUT</code> (thrown when ad fetch time is more than 1 minute) and <code>AD_RENDERING_TIMEOUT</code> (thrown when ad rendering time is more than 1 minute)
        • These errors will be thrown when <code>stopLoading</code> is called.
        • Added error code <code>INVALID_APP_ID</code> (thrown when publisher tries to load an ad with invalid or inactive app ID)

- Verification steps introduced to check the sanity of integration:
        • <code>InMobiAdNetwork</code> and <code>InMobiCommons</code> SDK JARs do not belong to the same release bundle. (Caution: The SDK throws <code>java.lang.RuntimeException</code> here. Refer to the FAQ in the Integration Guidelines)

        • Application does not provide <code>INTERNET</code> permissions.
        • Application does not include <code>com.inmobi.androidsdk.IMBrowserActivity</code> in the manifest file.
        • Application does not provide ALL or ANY of <code>android:configChanges</code> attributes for <code>com.inmobi.androidsdk.IMBrowserActivity</code> in the manifest file.

## Build 3.5.4 [01/Aug/2012]

• Fixed minor bugs
• Introduced addition of slotId for future use
• Manifest Instruction changes
 <nowiki><activity
   android:name="com.inmobi.androidsdk.IMBrowserActivity"
   android:configChanges="keyboardHidden|orientation|keyboard|screenSize|smallestScreenSize" /></nowiki>

NOTE: Please note that if you are targeting your app for API level below 13, <code>screenSize</code> and <code>smallestScreenSize</code> should be removed from the config changes.

## Build 3.5.3 [16/Jul/2012]

• Support for Kindle Fire added.

## Build 3.5.2 [11/May/2012]

• Prefixed <code>im_</code> to all asset files to avoid collision.
• Minor changes to the obfuscation done using ProGuard.

## Build 3.5.1 [24/Apr/2012]

• Fixed minor issues occurring while refreshing an expanded ad.
• Obfuscated code using ProGuard.

## Build 3.5.0 [11/Apr/2012]

• Introduced an <code>onLeaveApplication</code> callback for <code>IMAdListener</code> and <code>IMAdInterstitialListener</code>.
• Fixed the issue of Google Play (Android Market) URL opening in the embedded browser.
• Fixed the crash related to ad refresh in some of the older versions of Android (2.1 and earlier).
• Improved expanded ad orientation and alignment.

- New APIs:
        '''<code>IMAdListener</code>'''

         public void onLeaveApplication(IMAdView adView);

        '''<code>IMAdInterstitialListener</code>'''

         public void onLeaveApplication(IMAdInterstitial adInterstitial);

        '''<code>IMAdRequest</code>

         <nowiki>public enum IMIDType {
           ID_LOGIN, ID_SESSION
        }</nowiki>

         public void addIDType(IMIDType idtype, String value)
         public String getIDType(IMIDType idtype)
         public void removeIDType(IMIDType idtype)
         public void setLocationWithCityStateCountry(String city, String state, String country)
         public String getLocationWithCityStateCountry()
         public static int ID_DEVICE_NONE = 1;
         public static int ID_DEVICE_ODIN_1 = 2;
         public void setDeviceIDMask(int mask)

- Removed APIs:
        '''<code>IMAdRequest</code>'''
         public void setUDIDHashingAllowed(boolean isUDIDHashAllowed)
         public boolean isUDIDHashingAllowed()

## Build 3.0.1 [16/Sept/2011]

• Displaying better descriptive error messages when using <code>logcat</code>.
