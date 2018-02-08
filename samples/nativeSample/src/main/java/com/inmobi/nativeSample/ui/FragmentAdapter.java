package com.inmobi.nativeSample.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class FragmentAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_TABS = 3;

    private static final int POSITION_CUSTOM_INTEGRATION = 0;

    private static final int POSITION_LIST_VIEW_INTEGRATION = 1;

    private static final int POSITION_RECYCLER_VIEW_INTEGRATION = 2;

    FragmentAdapter(FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        for (int position = 0; position < NUM_TABS; position++) {
            tabLayout.addTab(tabLayout.newTab().setText(getTitle(position)));
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_CUSTOM_INTEGRATION:
                return SingleNativeAdFragment.newInstance(null);
            case POSITION_LIST_VIEW_INTEGRATION:
                return ListFeedFragment.newInstance(null);
            case POSITION_RECYCLER_VIEW_INTEGRATION:
                return RecyclerFeedFragment.newInstance(null);

            default:
                throw new IllegalArgumentException("No fragment for position:" + position);
        }
    }

    private String getTitle(int position) {
        switch (position) {
            case POSITION_CUSTOM_INTEGRATION:
                return SingleNativeAdFragment.getTitle();

            case POSITION_LIST_VIEW_INTEGRATION:
                return ListFeedFragment.getTitle();

            case POSITION_RECYCLER_VIEW_INTEGRATION:
                return RecyclerFeedFragment.getTitle();

            default:
                throw new IllegalArgumentException("No Title for position:" + position);
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}
