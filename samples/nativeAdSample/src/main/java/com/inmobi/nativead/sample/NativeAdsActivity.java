package com.inmobi.nativead.sample;

import com.inmobi.nativead.sample.newsfeed.NewsFragment;
import com.inmobi.nativead.sample.newsheadline.NewsHeadlinesFragment;
import com.inmobi.sdk.InMobiSdk;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.facebook.drawee.backends.pipeline.Fresco;

public class NativeAdsActivity extends AppCompatActivity implements
        NewsHeadlinesFragment.OnHeadlineSelectedListener,
        NewsFragment.OnTileSelectedListener {

    FragmentPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        InMobiSdk.init(this, "1a19654b05694a2385448499f03f48df");
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);

        setContentView(R.layout.activity_native_ads);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onArticleSelected(int position) {

    }

    @Override
    public void onTileSelected(int position) {

    }
}
