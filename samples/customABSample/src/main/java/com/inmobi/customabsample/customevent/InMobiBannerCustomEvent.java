package com.inmobi.customabsample.customevent;

import android.content.Context;

import androidx.annotation.NonNull;

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.listeners.BannerAdEventListener;
import com.inmobi.customabsample.CustomEventBannerListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class InMobiBannerCustomEvent {

    private static HashMap<Long, WeakReference<InMobiBanner>> bannerMap = new HashMap<>();

    /**
        this method is a sample method which Publisher primary mediation (PPM) will be calling when
        InMobi line item is executed
        The parameters passes in the method can vary from one PPM to the other
        @param placementId is necessary to get the banner ad object for which you received the bid
     */
    public void loadBanner(Context context, final CustomEventBannerListener customEventBannerListener, long placementId) {
        // Error checking
        if (context == null) {
            customEventBannerListener.onAdLoadFailed();
            return;
        }

        InMobiBanner inMobiBanner = bannerMap.get(placementId).get();

        if (inMobiBanner == null) {
            customEventBannerListener.onAdLoadFailed();
            return;
        }

        inMobiBanner.setListener(new BannerAdEventListener() {
            @Override
            public void onAdLoadSucceeded(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo info) {
                if (customEventBannerListener != null) {
                    customEventBannerListener.onAdLoadSuccessful(inMobiBanner);
                }
            }

            @Override
            public void onAdLoadFailed(@NonNull final InMobiBanner inMobiBanner,
                                       @NonNull final InMobiAdRequestStatus inMobiAdRequestStatus) {
                customEventBannerListener.onAdLoadFailed();
            }


            @Override
            public void onAdClicked(@NonNull final InMobiBanner inMobiBanner, final Map<Object, Object> map) {
                customEventBannerListener.onAdClicked();
            }

            @Override
            public void onAdDisplayed(@NonNull final InMobiBanner inMobiBanner) {
                customEventBannerListener.onAdDisplayed();
            }


            @Override
            public void onAdDismissed(@NonNull final InMobiBanner inMobiBanner) {
                customEventBannerListener.onAdDismissed();
            }


            @Override
            public void onUserLeftApplication(@NonNull final InMobiBanner inMobiBanner) {
                customEventBannerListener.onUserLeftApplication();
            }

            @Override
            public void onRewardsUnlocked(@NonNull InMobiBanner ad, @NonNull Map<Object, Object> rewards) {
                customEventBannerListener.onRewardsUnlocked(rewards);
            }
        });
        inMobiBanner.getPreloadManager().load();
    }

    /**
        Will be called when publisher receives a bid for a Banner ad from InMobi
        and should pass the same InMobiBanner ad object for which bid is received
        so that custom event class can load an ad using same ad object
        @param placementId: InMobiBanner placement id
        @param inMobiBanner: InMobiBanner Ad Object
     */
    public static void addInMobiAdObject(Long placementId, InMobiBanner inMobiBanner) {
        // WeakReference is created to avoid memory leaks
        bannerMap.put(placementId, new WeakReference<InMobiBanner>(inMobiBanner));
    }
}

