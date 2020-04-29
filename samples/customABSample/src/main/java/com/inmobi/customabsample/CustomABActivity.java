package com.inmobi.customabsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomABActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CustomABActivity.class.getName();
    private Button mBannerIntegration;
    private Button mInterstitialIntegration;
    private @SDKState String sdkInitStatus;

    public @interface SDKState {
        String SDK_INITIALIZING = "SDK_INITIALIZING";
        String SDK_INITIALIZED = "SDK_INITIALIZED";
        String SDK_INITIALIZE_FAILED = "SDK_INITIALIZATION_FAILED";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        JSONObject consent = new JSONObject();
        try {
            // Provide correct consent value to sdk which is obtained by User
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sdkInitStatus = SDKState.SDK_INITIALIZING;
        InMobiSdk.init(this, "1234567890qwerty0987654321qwerty12345", consent, new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@Nullable Error error) {
                if (error == null) {
                    Log.d(TAG, "InMobi SDK Initialization Success");
                    sdkInitStatus = SDKState.SDK_INITIALIZED;
                } else {
                    Log.e(TAG, "InMobi SDK Initialization failed: " + error.getMessage());
                    sdkInitStatus = SDKState.SDK_INITIALIZE_FAILED;
                }
            }
        });

        setContentView(R.layout.activity_custom_a_b);
        mBannerIntegration = (Button) findViewById(R.id.bannerSample);
        mBannerIntegration.setOnClickListener(this);
        mInterstitialIntegration = (Button) findViewById(R.id.interstitialSample);
        mInterstitialIntegration.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.bannerSample:
                switch(sdkInitStatus) {
                    case SDKState.SDK_INITIALIZE_FAILED:
                        Toast.makeText(CustomABActivity.this, "InMobi SDK is not initialized." +
                                "Check logs for more information", Toast.LENGTH_LONG).show();
                        break;
                    case SDKState.SDK_INITIALIZED:
                        startActivity(new Intent(CustomABActivity.this, BannerCustomABActivity.class));
                        break;
                    case SDKState.SDK_INITIALIZING:

                        break;
                }
                break;
            case R.id.interstitialSample:
                switch(sdkInitStatus) {
                    case SDKState.SDK_INITIALIZE_FAILED:
                        Toast.makeText(CustomABActivity.this, "InMobi SDK is not initialized." +
                                "Check logs for more information", Toast.LENGTH_LONG).show();
                        break;
                    case SDKState.SDK_INITIALIZED:
                        startActivity(new Intent(CustomABActivity.this, InterstitialCustomABActivity.class));
                        break;
                    case SDKState.SDK_INITIALIZING:
                        Toast.makeText(CustomABActivity.this, "Please wait for InMobi SDK" +
                                "to complete the initialization process", Toast.LENGTH_LONG).show();
                        break;
                }
                break;
        }
    }
}
