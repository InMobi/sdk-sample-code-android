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
import com.inmobi.unification.sdk.InitializationStatus;

import org.json.JSONException;
import org.json.JSONObject;

public class BannerBase extends AppCompatActivity {

    private static final String TAG = BannerBase.class.getName();
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

        @InitializationStatus String initStatus = InMobiSdk.init(this,
                "1234567890qwerty0987654321qwerty12345", consent);

        switch (initStatus) {
            case InitializationStatus.SUCCESS:
                Log.d(TAG, "InMobi SDK Initalization Success");
                break;
            case InitializationStatus.INVALID_ACCOUNT_ID:
            case InitializationStatus.UNKNOWN_ERROR:
                Log.d(TAG, "InMobi SDK Initalization Failed. Check logs for more information");
        }

        setContentView(R.layout.banner_base);
        Button xmlIntegration = (Button) findViewById(R.id.xmlSample);
        xmlIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BannerBase.this, BannerXmlActivity.class));
            }
        });
        Button normalIntegration = (Button) findViewById(R.id.normalBanner);
        normalIntegration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BannerBase.this, BannerAdsActivity.class));

            }
        });
    }
}
