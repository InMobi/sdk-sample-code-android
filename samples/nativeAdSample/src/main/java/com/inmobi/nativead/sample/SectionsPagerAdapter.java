package com.inmobi.nativead.sample;

import com.inmobi.nativead.sample.newsfeed.NewsFragment;
import com.inmobi.nativead.sample.newsheadline.NewsHeadlinesFragment;
import com.inmobi.nativead.sample.photofeed.PhotosFeedFragment;
import com.inmobi.nativead.sample.photopages.PhotoPagesFragment;
import com.inmobi.nativead.sample.prefetch.NewsHeadlinesPrefetchFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewsHeadlinesFragment headlinesFragment = new NewsHeadlinesFragment();
                Bundle args = new Bundle();
                args.putBoolean(NewsHeadlinesFragment.ARGS_PLACE_NATIVE_ADS, true);
                headlinesFragment.setArguments(args);
                return headlinesFragment;
            case 1:
                return new PhotosFeedFragment();
            case 2:
                return new NewsFragment();
            case 3:
                return new PhotoPagesFragment();
            case 4:
                return new NewsHeadlinesPrefetchFragment();
            default:
                return new PlaceholderFragment();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return context.getString(R.string.title_section3).toUpperCase(l);
            case 3:
                return context.getString(R.string.title_section4).toUpperCase(l);
            case 4:
                return context.getString(R.string.title_section5).toUpperCase(l);
            default:
                return PlaceholderFragment.getTitle().toUpperCase(l);
        }
    }
}