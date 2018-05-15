package com.inmobi.interstitial.sample;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.sdk.InMobiSdk;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject consent = new JSONObject();
        try {
            // Provide correct consent value to sdk which is obtained by User
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InMobiSdk.init(this, "123456789abcdfghjiukljnm09874", consent);
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
                new InMobiInterstitial.InterstitialAdListener2() {
                    @Override
                    public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
                        Log.d(TAG, "Unable to load interstitial ad (error message: " +
                                inMobiAdRequestStatus.getMessage());
                    }

                    @Override
                    public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onAdReceived");
                    }

                    @Override
                    public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
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
                    public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        Log.d(TAG, "onAdRewardActionCompleted " + map.size());
                    }

                    @Override
                    public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onAdDisplayFailed " + "FAILED");
                    }

                    @Override
                    public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onAdWillDisplay " + inMobiInterstitial);
                    }

                    @Override
                    public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onAdDisplayed " + inMobiInterstitial);
                    }

                    @Override
                    public void onAdInteraction(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        Log.d(TAG, "onAdInteraction " + inMobiInterstitial);
                    }

                    @Override
                    public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onAdDismissed " + inMobiInterstitial);
                    }

                    @Override
                    public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {
                        Log.d(TAG, "onUserWillLeaveApplication " + inMobiInterstitial);
                    }
                });
    }

    private void prefetchInterstitial() {
        mInterstitialAd = interstitialApplication.getInterstitial();
        if (null == mInterstitialAd) {
            interstitialApplication.fetchInterstitial(interstitialFetcher);
            return;
        }

        mInterstitialAd.setInterstitialAdListener(new InMobiInterstitial.InterstitialAdListener2() {
            @Override
            public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
                Log.d(TAG, "Unable to load interstitial ad (error message: " +
                        inMobiAdRequestStatus.getMessage());
            }

            @Override
            public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onAdReceived");
            }

            @Override
            public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
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
            public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                Log.d(TAG, "onAdRewardActionCompleted " + map.size());
            }

            @Override
            public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onAdDisplayFailed " + "FAILED");
            }

            @Override
            public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onAdWillDisplay " + inMobiInterstitial);
            }

            @Override
            public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onAdDisplayed " + inMobiInterstitial);
            }

            @Override
            public void onAdInteraction(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                Log.d(TAG, "onAdInteraction " + inMobiInterstitial);
            }

            @Override
            public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onAdDismissed " + inMobiInterstitial);
            }

            @Override
            public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {
                Log.d(TAG, "onUserWillLeaveApplication " + inMobiInterstitial);
            }
        });
        mInterstitialAd.load();
    }
}
