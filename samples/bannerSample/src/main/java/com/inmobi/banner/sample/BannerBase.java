package com.inmobi.banner.sample;

import com.inmobi.banner.sample.R;
import com.inmobi.sdk.InMobiSdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BannerBase extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InMobiSdk.init(this, "1234567890qwerty0987654321qwerty12345");
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
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
        Button prefetchSample = (Button) findViewById(R.id.prefetchSample);
        prefetchSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BannerBase.this, BannerPrefetchActivity.class));
            }
        });
    }
}
