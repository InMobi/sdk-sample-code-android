package com.inmobi.banner.sample;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;

import org.json.JSONException;
import org.json.JSONObject;

public class BannerBase extends AppCompatActivity {

    private static final String TAG = BannerBase.class.getName();
    private Button normalIntegration;
    private Button xmlIntegration;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        setContentView(R.layout.banner_base);
        xmlIntegration = (Button) findViewById(R.id.xmlSample);
        normalIntegration = (Button) findViewById(R.id.normalBanner);
    }

    private void sdkInitSuccess() {
        xmlIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BannerBase.this, BannerXmlActivity.class));
            }
        });
        normalIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BannerBase.this, BannerAdsActivity.class));

            }
        });
    }

    private void sdkInitFailed() {
        xmlIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BannerBase.this, "InMobi SDK is not initialized." +
                        "Check logs for more information", Toast.LENGTH_LONG).show();
            }
        });
        normalIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BannerBase.this, "InMobi SDK is not initialized." +
                        "Check logs for more information", Toast.LENGTH_LONG).show();
            }
        });
    }
}
