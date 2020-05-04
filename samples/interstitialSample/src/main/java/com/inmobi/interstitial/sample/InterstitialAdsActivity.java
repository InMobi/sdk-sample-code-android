package com.inmobi.interstitial.sample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;
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
        InMobiSdk.init(this, "1234567890qwerty0987654321qwerty12345", consent, new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@Nullable Error error) {
                if (error == null) {
                    Log.d(TAG, "InMobi SDK Initialization Success");
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
                } else {
                    Log.e(TAG, "InMobi SDK Initialization failed: " + error.getMessage());
                    mLoadAdButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(InterstitialAdsActivity.this, "InMobi SDK is not initialized." +
                                    "Check logs for more information", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        setContentView(R.layout.activity_interstitial_ads);

        mLoadAdButton = (Button) findViewById(R.id.button_load_ad);
        mShowAdButton = (Button) findViewById(R.id.button_show_ad);
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
        mInterstitialAd = new InMobiInterstitial(InterstitialAdsActivity.this, PlacementId.YOUR_PLACEMENT_ID,
                new InterstitialAdEventListener() {
                    @Override
                    public void onAdLoadSucceeded(@NonNull InMobiInterstitial inMobiInterstitial,
                                                  @NonNull AdMetaInfo adMetaInfo) {
                        Log.d(TAG, "onAdLoadSuccessful with bid " +  adMetaInfo.getBid());
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
                        Log.d(TAG, "Unable to load interstitial ad (error message: " +
                                inMobiAdRequestStatus.getMessage());
                    }

                    @Override
                    public void onAdFetchSuccessful(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                        Log.d(TAG, "onAdFetchSuccessful with bid " + adMetaInfo.getBid());
                    }

                    @Override
                    public void onAdClicked(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        Log.d(TAG, "onAdClicked " + map.size());
                    }

                    @Override
                    public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
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
