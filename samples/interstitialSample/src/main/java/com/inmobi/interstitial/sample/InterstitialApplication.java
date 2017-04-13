package com.inmobi.interstitial.sample;

import com.inmobi.ads.InMobiAdRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.sdk.InMobiSdk;

import android.support.multidex.MultiDexApplication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InterstitialApplication extends MultiDexApplication {

    InMobiAdRequest mInMobiAdRequest;
    BlockingQueue<InMobiInterstitial> mIntQueue = new LinkedBlockingQueue<>();
    BlockingQueue<InterstitialFetcher> mIntFetcherQueue = new LinkedBlockingQueue<>();
    InMobiInterstitial.InterstitialAdRequestListener interstitialAdRequestListener;

    @Override
    public void onCreate() {
        super.onCreate();
        InMobiSdk.init(this, "1234");
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        mInMobiAdRequest = new InMobiAdRequest.Builder(PlacementId.YOUR_PLACEMENT_ID)
                .setMonetizationContext(InMobiAdRequest.MonetizationContext.MONETIZATION_CONTEXT_ACTIVITY).build();
        interstitialAdRequestListener = new InMobiInterstitial.InterstitialAdRequestListener() {
            @Override
            public void onAdRequestCompleted(InMobiAdRequestStatus inMobiAdRequestStatus, InMobiInterstitial inMobiInterstitial) {
                if (inMobiAdRequestStatus.getStatusCode() == InMobiAdRequestStatus.StatusCode.NO_ERROR &&
                        null != inMobiInterstitial) {
                    mIntQueue.offer(inMobiInterstitial);
                    signalIntResult(true);
                } else {
                    signalIntResult(false);
                }
            }
        };

        fetchInterstitial(null);
    }

    public void fetchInterstitial(InterstitialFetcher interstitialFetcher) {
        if (null != interstitialFetcher) {
            mIntFetcherQueue.offer(interstitialFetcher);
        }
        InMobiInterstitial.requestAd(this, mInMobiAdRequest, interstitialAdRequestListener);
    }

    private void signalIntResult(boolean result) {
        final InterstitialFetcher interstitialFetcher = mIntFetcherQueue.poll();
        if (null != interstitialFetcher) {
            if (result) {
                interstitialFetcher.onFetchSuccess();
            } else {
                interstitialFetcher.onFetchFailure();
            }
        }
    }

    public InMobiInterstitial getInterstitial() {
        return mIntQueue.poll();
    }
}