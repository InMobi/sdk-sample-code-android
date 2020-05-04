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

import java.util.Map;

public class InterstitialCustomABActivity extends AppCompatActivity {

    private InMobiInterstitial mInterstitialAd;

    private Button mPreloadButton;
    private Button mLoadAdButton;
    private Button mShowAdButton;

    private final String TAG = InterstitialCustomABActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_custom_a_b);
        mPreloadButton = findViewById(R.id.button_preload_ad);
        mLoadAdButton = findViewById(R.id.button_load_ad);
        mShowAdButton = findViewById(R.id.button_show_ad);
        mPreloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mInterstitialAd) {
                    setupInterstitial();
                } else {
                    mInterstitialAd.getPreloadManager().preload();
                }
            }
        });
        mLoadAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterstitialAd.getPreloadManager().load();
            }
        });
        mShowAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterstitialAd.show();
                mPreloadButton.setVisibility(View.VISIBLE);
                mShowAdButton.setVisibility(View.GONE);
            }
        });
        setupInterstitial();
    }

    private void setupInterstitial() {
        mInterstitialAd = new InMobiInterstitial(InterstitialCustomABActivity.this, PlacementId.YOUR_INTERSTITIAL_PLACEMENT_ID,
                new InterstitialAdEventListener() {
                    @Override
                    public void onAdLoadSucceeded(@NonNull InMobiInterstitial inMobiInterstitial,
                                                  @NonNull AdMetaInfo adMetaInfo) {
                        Log.d(TAG, "onAdLoadSuccessful with bid " + adMetaInfo.getBid());
                        if (inMobiInterstitial.isReady()) {
                            mLoadAdButton.setVisibility(View.GONE);
                            if (mShowAdButton != null) {
                                mShowAdButton.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Log.d(TAG, "onAdLoadSuccessful inMobiInterstitial not ready");
                        }
                    }

                    @Override
                    public void onAdLoadFailed(@NonNull InMobiInterstitial inMobiInterstitial,
                                               @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        Log.d(TAG, "Unable to load interstitial ad (error message: " +
                                inMobiAdRequestStatus.getMessage());
                    }

                    @Override
                    public void onAdFetchSuccessful(@NonNull InMobiInterstitial inMobiInterstitial,
                                                    @NonNull AdMetaInfo adMetaInfo) {
                        Log.d(TAG, "onAdFetchSuccessful with bid  " + adMetaInfo.getBid());
                        if (mLoadAdButton != null) {
                            mLoadAdButton.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAdFetchFailed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        Log.d(TAG, "onAdFetchFailed due to " + inMobiAdRequestStatus.getMessage());
                    }

                    @Override
                    public void onAdClicked(@NonNull InMobiInterstitial inMobiInterstitial,
                                            @NonNull Map<Object, Object> map) {
                        Log.d(TAG, "onAdClicked " + map);
                    }

                    @Override
                    public void onAdWillDisplay(@NonNull InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onAdWillDisplay");
                    }

                    @Override
                    public void onAdDisplayed(@NonNull InMobiInterstitial inMobiInterstitial,
                                              @NonNull AdMetaInfo adMetaInfo) {
                        Log.d(TAG, "onAdDisplayed");
                    }

                    @Override
                    public void onAdDisplayFailed(@NonNull InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onAdDisplayFailed");
                    }

                    @Override
                    public void onAdDismissed(@NonNull InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onAdDismissed");
                    }

                    @Override
                    public void onUserLeftApplication(@NonNull InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onUserWillLeaveApplication");
                    }

                    @Override
                    public void onRewardsUnlocked(@NonNull InMobiInterstitial inMobiInterstitial,
                                                  @NonNull Map<Object, Object> map) {
                        Log.d(TAG, "onRewardsUnlocked " + map);
                    }
                });
    }
}
