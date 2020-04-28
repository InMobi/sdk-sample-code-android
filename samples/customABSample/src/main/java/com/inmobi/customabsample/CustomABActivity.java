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

public class CustomABActivity extends AppCompatActivity {

    private static final String TAG = CustomABActivity.class.getName();
    private Button bannerIntegration;
    private Button interstitialIntegration;

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

        InMobiSdk.init(this, "1234567890qwerty0987654321qwerty12345", consent, new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@Nullable Error error) {
                if (error == null) {
                    Log.d(TAG, "InMobi SDK Initialization Success");
                    sdkInitSuccess();
                } else {
                    Log.e(TAG, "InMobi SDK Initialization failed: " + error.getMessage());
                    sdkInitFailed();
                }
            }
        });

        setContentView(R.layout.activity_custom_a_b);
        bannerIntegration = (Button) findViewById(R.id.bannerSample);
        interstitialIntegration = (Button) findViewById(R.id.interstitialSample);
    }

    private void sdkInitSuccess() {
        bannerIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomABActivity.this, BannerCustomABActivity.class));
            }
        });
        interstitialIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomABActivity.this, InterstitialCustomABActivity.class));

            }
        });
    }

    private void sdkInitFailed() {
        bannerIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomABActivity.this, "InMobi SDK is not initialized." +
                        "Check logs for more information", Toast.LENGTH_LONG).show();
            }
        });
        interstitialIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomABActivity.this, "InMobi SDK is not initialized." +
                        "Check logs for more information", Toast.LENGTH_LONG).show();
            }
        });
    }

}
