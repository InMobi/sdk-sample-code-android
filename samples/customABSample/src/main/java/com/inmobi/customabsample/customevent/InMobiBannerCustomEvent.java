package com.inmobi.customabsample.customevent;

import android.util.Log;

import androidx.annotation.NonNull;

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.listeners.BannerAdEventListener;

public class InMobiBannerCustomEvent implements CustomEventBanner {

    private static final String TAG = InMobiBannerCustomEvent.class.getName();
    private InMobiBanner mInMobiBanner;

    /**
        Will be called when publisher receives a bid for a Banner ad from InMobi
        and should pass the same InMobiBanner ad object for which bid is received
        so that custom event class can load an ad using same ad object
        @param inMobiBanner: InMobiBanner Ad Object
     */
    public void setInMobiBanner(@NonNull InMobiBanner inMobiBanner) {
        mInMobiBanner = inMobiBanner;
    }

    /**
     this method is a sample method which Publisher primary mediation (PPM) will be calling when
     InMobi line item is executed
     The parameters passed in the method can vary from one PPM to the other
     */
    @Override
    public void loadAd(@NonNull final CustomEventBannerListener customEventListener) {
        if (mInMobiBanner != null) {
            mInMobiBanner.setListener(new BannerAdEventListener() {
                @Override
                public void onAdLoadSucceeded(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                    Log.d(TAG, "InMobiBanner ad loaded with bid: " + adMetaInfo.getBid());
                    customEventListener.onAdLoadSuccess(inMobiBanner);
                }

                @Override
                public void onAdLoadFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                    Log.d(TAG, "InMobiBanner ad load failed with message: " + inMobiAdRequestStatus.getMessage());
                    customEventListener.onAdLoadFailed();
                }
            });
            mInMobiBanner.getPreloadManager().load();
        }
    }
}

