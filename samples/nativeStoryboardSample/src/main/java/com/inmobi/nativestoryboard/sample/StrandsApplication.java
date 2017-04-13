package com.inmobi.nativestoryboard.sample;

import com.inmobi.ads.InMobiAdRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNativeStrand;
import com.inmobi.nativestoryboard.PlacementId;
import com.inmobi.nativestoryboard.utility.StrandsFetcher;
import com.inmobi.sdk.InMobiSdk;

import android.support.multidex.MultiDexApplication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StrandsApplication extends MultiDexApplication {
    InMobiAdRequest mInMobiAdRequest;
    BlockingQueue<InMobiNativeStrand> mStrandQueue = new LinkedBlockingQueue<>();
    BlockingQueue<StrandsFetcher> mStrandFetcherQueue = new LinkedBlockingQueue<>();
    InMobiNativeStrand.NativeStrandAdRequestListener mNativeStrandAdRequestListener;

    @Override
    public void onCreate() {
        super.onCreate();
        InMobiSdk.init(this, "12346789pqrstuvwxy987654321pqwr");
        mInMobiAdRequest = new InMobiAdRequest.Builder(PlacementId.YOUR_PLACEMENT_ID_HERE)
                .setMonetizationContext(InMobiAdRequest.MonetizationContext.MONETIZATION_CONTEXT_ACTIVITY).build();
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        mNativeStrandAdRequestListener = new InMobiNativeStrand.NativeStrandAdRequestListener() {
            @Override
            public void onAdRequestCompleted(InMobiAdRequestStatus inMobiAdRequestStatus, InMobiNativeStrand inMobiNativeStrand) {
                if (inMobiAdRequestStatus.getStatusCode() == InMobiAdRequestStatus.StatusCode.NO_ERROR &&
                        null != inMobiNativeStrand) {
                    mStrandQueue.offer(inMobiNativeStrand);
                    signalStrandsResult(true);
                } else {
                    signalStrandsResult(false);
                }
            }
        };
        fetchStrands(null);
    }

    public void fetchStrands(StrandsFetcher strandsFetcher) {
        if (null != strandsFetcher) {
            mStrandFetcherQueue.offer(strandsFetcher);
        }
        InMobiNativeStrand.requestAd(this, mInMobiAdRequest, mNativeStrandAdRequestListener);
    }

    private void signalStrandsResult(boolean result) {
        final StrandsFetcher strandsFetcher = mStrandFetcherQueue.poll();
        if (null != strandsFetcher) {
            if (result) {
                strandsFetcher.onFetchSuccess();
            } else {
                strandsFetcher.onFetchFailure();
            }
        }
    }

    public InMobiNativeStrand getStrands() {
        return mStrandQueue.poll();
    }
}