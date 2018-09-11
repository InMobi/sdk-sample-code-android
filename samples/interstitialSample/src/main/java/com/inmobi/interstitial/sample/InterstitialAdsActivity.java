package com.inmobi.interstitial.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.inmobi.sdk.InMobiSdk;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InterstitialAdsActivity extends AppCompatActivity {

    private InMobiInterstitial mInterstitialAd;
    private Button mLoadAdButton;
    private Button mShowAdButton;
    private Button mPrefetch;
    private InterstitialApplication interstitialApplication;
    private InterstitialFetcher interstitialFetcher;
    private final String TAG = InterstitialAdsActivity.class.getSimpleName();
    private final Handler mHandler = new Handler();
    private AtomicInteger forcedRetry = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, "1234567890qwerty0987654321qwerty12345");
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        setContentView(R.layout.activity_interstitial_ads);
        interstitialApplication = (InterstitialApplication) this.getApplication();

        interstitialFetcher = new InterstitialFetcher() {
            @Override
            public void onFetchSuccess() {
                setupInterstitial();
            }

            @Override
            public void onFetchFailure() {
                if (forcedRetry.getAndIncrement() < 2) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            interstitialApplication.fetchInterstitial(interstitialFetcher);
                        }
                    }, 5000);
                } else {
                    adjustButtonVisibility();
                }
            }
        };

        mPrefetch = (Button) findViewById(R.id.button_prefetch);
        mLoadAdButton = (Button) findViewById(R.id.button_load_ad);
        mShowAdButton = (Button) findViewById(R.id.button_show_ad);
        mPrefetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoadAdButton.setVisibility(View.GONE);
                mShowAdButton.setVisibility(View.GONE);
                forcedRetry.set(0);
                prefetchInterstitial();
            }
        });
        mLoadAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPrefetch.setVisibility(View.GONE);
                if (null == mInterstitialAd) {
                    setupInterstitial();
                } else {
                    mInterstitialAd.load();
                }
            }
        });

        mShowAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterstitialAd.show();
            }
        });
        setupInterstitial();
    }

    @Override
    public void onResume() {
        super.onResume();
        adjustButtonVisibility();
    }

    private void adjustButtonVisibility() {
        mPrefetch.setVisibility(View.VISIBLE);
        mLoadAdButton.setVisibility(View.VISIBLE);
        mShowAdButton.setVisibility(View.GONE);
    }

    private void setupInterstitial() {
        mInterstitialAd = new InMobiInterstitial(InterstitialAdsActivity.this, PlacementId.YOUR_PLACEMENT_ID,
                new InterstitialAdEventListener() {
                    @Override
                    public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
                        super.onAdLoadSucceeded(inMobiInterstitial);
                        Log.d(TAG, "onAdLoadSuccessful");
                        if (inMobiInterstitial.isReady()) {
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
                    public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
                        super.onAdReceived(inMobiInterstitial);
                        Log.d(TAG, "onAdReceived");
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
                    public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
                        super.onAdDisplayed(inMobiInterstitial);
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

                    @Override
                    public void onRequestPayloadCreated(byte[] bytes) {
                        super.onRequestPayloadCreated(bytes);
                        Log.d(TAG, "onRequestPayloadCreated " + bytes);
                    }

                    @Override
                    public void onRequestPayloadCreationFailed(InMobiAdRequestStatus inMobiAdRequestStatus) {
                        super.onRequestPayloadCreationFailed(inMobiAdRequestStatus);
                        Log.d(TAG, "onRequestPayloadCreationFailed " + inMobiAdRequestStatus);
                    }
                });
    }

    private void prefetchInterstitial() {
        mInterstitialAd = interstitialApplication.getInterstitial();
        if (null == mInterstitialAd) {
            interstitialApplication.fetchInterstitial(interstitialFetcher);
            return;
        }

        mInterstitialAd.setListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
                super.onAdLoadSucceeded(inMobiInterstitial);
                Log.d(TAG, "onAdLoadSuccessful");
                if (inMobiInterstitial.isReady()) {
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
            public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
                super.onAdReceived(inMobiInterstitial);
                Log.d(TAG, "onAdReceived");
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
            public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
                super.onAdDisplayed(inMobiInterstitial);
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

            @Override
            public void onRequestPayloadCreated(byte[] bytes) {
                super.onRequestPayloadCreated(bytes);
                Log.d(TAG, "onRequestPayloadCreated " + bytes);
            }

            @Override
            public void onRequestPayloadCreationFailed(InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onRequestPayloadCreationFailed(inMobiAdRequestStatus);
                Log.d(TAG, "onRequestPayloadCreationFailed " + inMobiAdRequestStatus);
            }
        });


        mInterstitialAd.load();
    }
}
