package com.inmobi.banner.sample;

import android.support.multidex.MultiDexApplication;

import com.inmobi.ads.InMobiAdRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.banner.PlacementId;
import com.inmobi.banner.utility.BannerFetcher;
import com.inmobi.sdk.InMobiSdk;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.inmobi.banner.utility.Constants.BANNER_HEIGHT;
import static com.inmobi.banner.utility.Constants.BANNER_WIDTH;

public class BannerApplication extends MultiDexApplication {

    InMobiAdRequest mInMobiAdRequest;
    BlockingQueue<InMobiBanner> mBannerQueue = new LinkedBlockingQueue<>();
    BlockingQueue<BannerFetcher> mBannerFetcherQueue = new LinkedBlockingQueue<>();
    InMobiBanner.BannerAdRequestListener bannerAdRequestListener;

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
                .setMonetizationContext(InMobiAdRequest.MonetizationContext.MONETIZATION_CONTEXT_ACTIVITY)
                .setSlotSize(BANNER_WIDTH, BANNER_HEIGHT).build();
        bannerAdRequestListener = new InMobiBanner.BannerAdRequestListener() {
            @Override
            public void onAdRequestCompleted(InMobiAdRequestStatus inMobiAdRequestStatus, InMobiBanner inMobiBanner) {
                if (inMobiAdRequestStatus.getStatusCode() == InMobiAdRequestStatus.StatusCode.NO_ERROR &&
                        null != inMobiBanner) {
                    mBannerQueue.offer(inMobiBanner);
                    signalBannerResult(true);
                } else {
                    signalBannerResult(false);
                }

            }
        };
        fetchBanner(null);
    }

    public void fetchBanner(BannerFetcher bannerFetcher) {
        if (null != bannerFetcher) {
            mBannerFetcherQueue.offer(bannerFetcher);
        }
        InMobiBanner.requestAd(this, mInMobiAdRequest, bannerAdRequestListener);
    }

    private void signalBannerResult(boolean result) {
        final BannerFetcher bannerFetcher = mBannerFetcherQueue.poll();
        if (null != bannerFetcher) {
            if (result) {
                bannerFetcher.onFetchSuccess();
            } else {
                bannerFetcher.onFetchFailure();
            }
        }
    }

    public InMobiBanner getBanner() {
        return mBannerQueue.poll();
    }
}