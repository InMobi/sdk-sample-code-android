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
                        Log.d(TAG, "onAdLoadSuccessful");
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
                    public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
                        super.onAdLoadFailed(inMobiInterstitial, inMobiAdRequestStatus);
                        Log.d(TAG, "Unable to load interstitial ad (error message: " +
                                inMobiAdRequestStatus.getMessage());
                    }

                    @Override
                    public void onAdFetchSuccessful(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                        super.onAdFetchSuccessful(inMobiInterstitial, adMetaInfo);
                        Log.d(TAG, "onAdFetchSuccessful");
                        if (mLoadAdButton != null) {
                            mLoadAdButton.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onAdFetchFailed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        super.onAdFetchFailed(inMobiInterstitial, inMobiAdRequestStatus);
                        Log.d(TAG, "onAdFetchFailed due to " + inMobiAdRequestStatus.getMessage());
                    }

                    @Override
                    public void onAdClicked(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        super.onAdClicked(inMobiInterstitial, map);
                        Log.d(TAG, "onAdClicked " + map.size());
                    }

                    @Override
                    public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
                        super.onAdWillDisplay(inMobiInterstitial);
                        Log.d(TAG, "onAdWillDisplay " + inMobiInterstitial);
                    }

                    @Override
                    public void onAdDisplayed(@NonNull InMobiInterstitial inMobiInterstitial,
                                              @NonNull AdMetaInfo adMetaInfo) {
                        super.onAdDisplayed(inMobiInterstitial, adMetaInfo);
                        Log.d(TAG, "onAdDisplayed " + inMobiInterstitial);
                    }

                    @Override
                    public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
                        super.onAdDisplayFailed(inMobiInterstitial);
                        Log.d(TAG, "onAdDisplayFailed " + "FAILED");
                    }

                    @Override
                    public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
                        super.onAdDismissed(inMobiInterstitial);
                        Log.d(TAG, "onAdDismissed " + inMobiInterstitial);
                    }

                    @Override
                    public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {
                        super.onUserLeftApplication(inMobiInterstitial);
                        Log.d(TAG, "onUserWillLeaveApplication " + inMobiInterstitial);
                    }

                    @Override
                    public void onRewardsUnlocked(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        super.onRewardsUnlocked(inMobiInterstitial, map);
                        Log.d(TAG, "onRewardsUnlocked " + map.size());
                    }
                });
    }
}
