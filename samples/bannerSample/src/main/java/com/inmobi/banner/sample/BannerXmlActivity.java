package com.inmobi.banner.sample;

import com.inmobi.ads.InMobiBanner;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class BannerXmlActivity extends AppCompatActivity {

    private InMobiBanner mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_xml);
        mBanner = (InMobiBanner) findViewById(R.id.banner);
        mBanner.load();
    }
}
