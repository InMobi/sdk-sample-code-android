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
import com.inmobi.customabsample.customevent.CustomEventBannerListener;
import com.inmobi.customabsample.customevent.InMobiBannerCustomEvent;

public class BannerCustomABActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BannerCustomABActivity.class.getSimpleName();
    private InMobiBanner mInMobiBanner;

    private RelativeLayout adcontainer;
    public static final int BANNER_WIDTH = 320;
    public static final int BANNER_HEIGHT = 50;

    private Button load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_custom_a_b);
        load = findViewById(R.id.load);
        adcontainer = findViewById(R.id.ad_container);
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
                InMobiBannerCustomEvent customEvent = new InMobiBannerCustomEvent();
                customEvent.setInMobiBanner(inMobiBanner);
                customEvent.loadAd(new CustomEventBannerListener() {
                    @Override
                    public void onAdLoadSuccess(View view) {
                        Log.d(TAG, "onAdLoadSuccess");
                        adcontainer.addView(view);
                    }

                    @Override
                    public void onAdLoadFailed() {
                        Log.d(TAG, "onAdLoadFailed");
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.load:
                setupBannerAd();
                adcontainer.removeAllViews();
                mInMobiBanner.getPreloadManager().preload();
                break;
        }
    }
}
