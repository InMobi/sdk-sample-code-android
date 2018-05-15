package com.inmobi.interstitial.sample;

import com.inmobi.ads.InMobiAdRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.sdk.InMobiSdk;

import android.support.multidex.MultiDexApplication;

import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject consent = new JSONObject();
        try {
            // Provide correct consent value to sdk which is obtained by User
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InMobiSdk.init(this, "123456789abcdfghjiukljnm09874", consent);
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