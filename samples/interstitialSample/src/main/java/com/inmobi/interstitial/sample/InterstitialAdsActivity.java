package com.inmobi.interstitial.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.exceptions.SdkNotInitializedException;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.unification.sdk.InitializationStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class InterstitialAdsActivity extends AppCompatActivity {

    private InMobiInterstitial mInterstitialAd;
    private Button mLoadAdButton;
    private Button mShowAdButton;
    private final String TAG = InterstitialAdsActivity.class.getSimpleName();

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

        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        @InitializationStatus String initStatus = InMobiSdk.init(this,
                "1234567890qwerty0987654321qwerty12345", consent);
        switch (initStatus) {
            case InitializationStatus.SUCCESS:
                Log.d(TAG, "InMobi SDK Initialization Success");
                break;
            case InitializationStatus.INVALID_ACCOUNT_ID:
            case InitializationStatus.UNKNOWN_ERROR:
                Log.d(TAG, "InMobi SDK Initialization Failed. Check logs for more information");
                break;
        }

        setContentView(R.layout.activity_interstitial_ads);

        mLoadAdButton = (Button) findViewById(R.id.button_load_ad);
        mShowAdButton = (Button) findViewById(R.id.button_show_ad);
        mLoadAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        mLoadAdButton.setVisibility(View.VISIBLE);
        mShowAdButton.setVisibility(View.GONE);
    }

    private void setupInterstitial() {
        try {
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
                    });
        } catch (SdkNotInitializedException e) {
            Log.e(TAG, "Exception while creating InMobiInterstitial object", e);
            Toast.makeText(this, "Problem creating InMobiInterstitial Object," +
                    " Check logs for more information", Toast.LENGTH_LONG).show();
        }
    }
}
