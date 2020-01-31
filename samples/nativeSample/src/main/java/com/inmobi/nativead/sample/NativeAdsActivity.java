package com.inmobi.nativead.sample;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.inmobi.sdk.InMobiSdk;
import com.inmobi.unification.sdk.InitializationStatus;

import org.json.JSONException;
import org.json.JSONObject;

public class NativeAdsActivity extends AppCompatActivity {

    private static final String TAG = NativeAdsActivity.class.getName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize Inmobi SDK before any API call.
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        JSONObject consent = new JSONObject();
        try {
            // Provide correct consent value to sdk which is obtained by User
            consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        @InitializationStatus String initStatus = InMobiSdk.init(this,
                "12345678901234567890123456789012", consent);

        switch (initStatus) {
            case InitializationStatus.SUCCESS:
                Log.d(TAG, "InMobi SDK Initialization Success");
                break;
            case InitializationStatus.INVALID_ACCOUNT_ID:
            case InitializationStatus.UNKNOWN_ERROR:
                Log.d(TAG, "InMobi SDK Initialization Failed. Check logs for more information");
                break;
        }

        setContentView(R.layout.activity_native_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), tabLayout);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}