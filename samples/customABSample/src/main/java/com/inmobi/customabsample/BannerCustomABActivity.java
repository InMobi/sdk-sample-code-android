package com.inmobi.customabsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.listeners.BannerAdEventListener;
import com.inmobi.customabsample.customevent.InMobiBannerCustomEvent;

import java.util.Map;

public class BannerCustomABActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BannerCustomABActivity.class.getSimpleName();
    InMobiBanner mInMobiBanner;

    public static final int BANNER_WIDTH = 320;
    public static final int BANNER_HEIGHT = 50;

    private Button load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_custom_a_b);
        load = findViewById(R.id.preload);
        load.setOnClickListener(this);
    }

    private void setupBannerAd() {
        mInMobiBanner = new InMobiBanner(this, PlacementId.YOUR_BANNER_PLACEMENT_ID);
        mInMobiBanner.setBannerSize(BANNER_WIDTH, BANNER_HEIGHT);
        mInMobiBanner.setEnableAutoRefresh(false);
        mInMobiBanner.setListener(new BannerAdEventListener() {
            @Override
            public void onAdFetchFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                Log.d(TAG, "InMobi failed to fetch bid");
            }

            @Override
            public void onAdFetchSuccessful(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                Log.d(TAG, "InMobi successfully fetched an ad with bid " + adMetaInfo.getBid());
                InMobiBannerCustomEvent.addInMobiAdObject(PlacementId.YOUR_BANNER_PLACEMENT_ID, inMobiBanner);
                new InMobiBannerCustomEvent().loadBanner(BannerCustomABActivity.this,
                        customEventBannerListener, PlacementId.YOUR_BANNER_PLACEMENT_ID);
            }
        });
    }

    CustomEventBannerListener customEventBannerListener = new CustomEventBannerListener() {

        @Override
        public void onAdDisplayed() {
            Log.d(TAG, "onAdDisplayed");
        }

        @Override
        public void onAdDismissed() {
            Log.d(TAG, "onAdDismissed");
        }

        @Override
        public void onUserLeftApplication() {
            Log.d(TAG, "onUserLeftApplication");
        }

        @Override
        public void onRewardsUnlocked(Map<Object, Object> rewards) {
            Log.d(TAG, "onRewardsUnlocked");
        }

        @Override
        public void onAdLoadFailed() {
            Log.d(TAG, "onAdLoadFailed");
        }

        @Override
        public void onAdLoadSuccessful(View view) {
            Log.d(TAG, "onAdLoadSuccessful");
            RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.ad_container);
            adContainer.addView(mInMobiBanner);
        }

        @Override
        public void onAdClicked() {
            Log.d(TAG, "onAdClicked");
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.preload:
                setupBannerAd();
                mInMobiBanner.getPreloadManager().preload();
                break;
        }
    }
}
