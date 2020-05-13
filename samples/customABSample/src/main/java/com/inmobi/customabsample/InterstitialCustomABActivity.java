package com.inmobi.customabsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.inmobi.customabsample.customevent.CustomEventInterstitialListener;
import com.inmobi.customabsample.customevent.InMobiInterstitialCustomEvent;

import java.util.Map;

public class InterstitialCustomABActivity extends AppCompatActivity implements View.OnClickListener {

    private InMobiInterstitial mInterstitialAd;

    private Button mLoadAdButton;
    private Button mShowAdButton;

    private final String TAG = InterstitialCustomABActivity.class.getSimpleName();
    private InMobiInterstitialCustomEvent customEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_custom_a_b);
        mLoadAdButton = findViewById(R.id.load);
        mShowAdButton = findViewById(R.id.show);
        mLoadAdButton.setOnClickListener(this);
        mShowAdButton.setOnClickListener(this);
    }

    private void setupInterstitial() {
        mInterstitialAd = new InMobiInterstitial(InterstitialCustomABActivity.this, PlacementId.YOUR_INTERSTITIAL_PLACEMENT_ID,
                new InterstitialAdEventListener() {

                    @Override
                    public void onAdFetchSuccessful(@NonNull InMobiInterstitial inMobiInterstitial,
                                                    @NonNull AdMetaInfo adMetaInfo) {
                        Log.d(TAG, "onAdFetchSuccessful with bid  " + adMetaInfo.getBid());
                        if (mShowAdButton != null) {
                            mLoadAdButton.setVisibility(View.VISIBLE);
                        }
                        customEvent = new InMobiInterstitialCustomEvent();
                        customEvent.setInMobiInterstitial(inMobiInterstitial);
                        customEvent.loadAd(new CustomEventInterstitialListener() {
                            @Override
                            public void onAdLoadSuccess() {
                                Log.d(TAG, "onAdLoadSuccess");
                                mShowAdButton.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdLoadFailed() {
                                Log.d(TAG, "onAdLoadFailed");

                            }

                            @Override
                            public void onAdShowSuccess() {
                                Log.d(TAG, "onAdShowSuccess");
                            }

                            @Override
                            public void onAdShowFailed() {
                                Log.d(TAG, "onAdShowFailed");
                            }
                        });
                    }

                    @Override
                    public void onAdFetchFailed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        Log.d(TAG, "onAdFetchFailed due to " + inMobiAdRequestStatus.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.load:
                setupInterstitial();
                mInterstitialAd.getPreloadManager().preload();
                break;
            case R.id.show:
                customEvent.showAd();
                break;
        }
    }
}
