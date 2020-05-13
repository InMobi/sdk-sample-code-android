package com.inmobi.customabsample.customevent;


import androidx.annotation.NonNull;

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.InterstitialAdEventListener;

public class InMobiInterstitialCustomEvent implements CustomEventInterstitial {

    private static final String TAG = InMobiBannerCustomEvent.class.getName();
    private InMobiInterstitial mInMobiInterstitial;
    private CustomEventInterstitialListener listener;

    /**
     Will be called when publisher receives a bid for a Banner ad from InMobi
     and should pass the same InMobiInterstitial ad object for which bid is received
     so that custom event class can load an ad using same ad object
     @param inMobiInterstitial: InMobiInterstitial Ad Object
     */
    public void setInMobiInterstitial(@NonNull InMobiInterstitial inMobiInterstitial) {
        mInMobiInterstitial = inMobiInterstitial;
    }

    /**
     this method is a sample method which Publisher primary mediation (PPM) will be calling when
     InMobi line item is executed
     The parameters passed in the method can vary from one PPM to the other
     */
    @Override
    public void loadAd(final CustomEventInterstitialListener customEventInterstitialListener) {
        listener = customEventInterstitialListener;
        if (mInMobiInterstitial != null) {
            mInMobiInterstitial.setListener(new InterstitialAdEventListener() {
                @Override
                public void onAdLoadSucceeded(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                    listener.onAdLoadSuccess();
                }

                @Override
                public void onAdLoadFailed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                    listener.onAdLoadFailed();
                }
            });
            mInMobiInterstitial.getPreloadManager().load();
        }
    }

    @Override
    public void showAd() {
        mInMobiInterstitial.setListener(new InterstitialAdEventListener() {
            @Override
            public void onAdDisplayed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                listener.onAdShowSuccess();
            }

            @Override
            public void onAdDisplayFailed(@NonNull InMobiInterstitial inMobiInterstitial) {
                listener.onAdShowFailed();
            }
        });
        mInMobiInterstitial.show();
    }
}
