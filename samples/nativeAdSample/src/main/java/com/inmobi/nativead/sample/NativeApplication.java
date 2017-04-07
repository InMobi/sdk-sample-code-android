package com.inmobi.nativead.sample;

import com.inmobi.ads.InMobiAdRequest;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.nativead.sample.utility.NativeFetcher;
import com.inmobi.sdk.InMobiSdk;

import android.support.multidex.MultiDexApplication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NativeApplication extends MultiDexApplication {

    InMobiAdRequest mInMobiAdRequest;
    BlockingQueue<InMobiNative> mNativeQueue = new LinkedBlockingQueue<>();
    BlockingQueue<NativeFetcher> mNativeFetcherQueue = new LinkedBlockingQueue<>();
    InMobiNative.NativeAdRequestListener nativeAdRequestListener;

    @Override
    public void onCreate() {
        super.onCreate();
        InMobiSdk.init(this, "12346789987654321");
        mInMobiAdRequest = new InMobiAdRequest.Builder(PlacementId.YOUR_PLACEMENT_ID)
                .setMonetizationContext(InMobiAdRequest.MonetizationContext.MONETIZATION_CONTEXT_ACTIVITY).build();
        nativeAdRequestListener = new InMobiNative.NativeAdRequestListener() {
            @Override
            public void onAdRequestCompleted(InMobiAdRequestStatus inMobiAdRequestStatus, InMobiNative inMobiNative) {
                if (inMobiAdRequestStatus.getStatusCode() == InMobiAdRequestStatus.StatusCode.NO_ERROR &&
                        null != inMobiNative) {
                    mNativeQueue.offer(inMobiNative);
                    signalNativeResult(true);
                } else {
                    signalNativeResult(false);
                }
            }
        };
        fetchNative(null);
    }

    public void fetchNative(NativeFetcher nativeFetcher) {
        if (null != nativeFetcher) {
            mNativeFetcherQueue.offer(nativeFetcher);
        }
        InMobiNative.requestAd(this, mInMobiAdRequest, nativeAdRequestListener);
    }

    private void signalNativeResult(boolean result) {
        final NativeFetcher nativeFetcher = mNativeFetcherQueue.poll();
        if (null != nativeFetcher) {
            if (result) {
                nativeFetcher.onFetchSuccess();
            } else {
                nativeFetcher.onFetchFailure();
            }
        }
    }

    public InMobiNative getNative() {
        return mNativeQueue.poll();

    }
}