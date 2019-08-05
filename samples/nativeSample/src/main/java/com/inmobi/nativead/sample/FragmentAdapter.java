package com.inmobi.nativead.sample;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.tabs.TabLayout;

import com.inmobi.nativead.sample.listview.ListViewFeedFragment;
import com.inmobi.nativead.sample.recyclerview.RecyclerFeedFragment;
import com.inmobi.nativead.sample.singlestrand.SingleNativeAdFragment;

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
                return new SingleNativeAdFragment();

            case POSITION_LIST_VIEW_INTEGRATION:
                return new ListViewFeedFragment();

            case POSITION_RECYCLER_VIEW_INTEGRATION:
                return new RecyclerFeedFragment();

            default:
                throw new IllegalArgumentException("No fragment for position:" + position);
        }
    }

    private String getTitle(int position) {
        switch (position) {
            case POSITION_CUSTOM_INTEGRATION:
                return SingleNativeAdFragment.getTitle();

            case POSITION_LIST_VIEW_INTEGRATION:
                return ListViewFeedFragment.getTitle();

            case POSITION_RECYCLER_VIEW_INTEGRATION:
                return RecyclerFeedFragment.getTitle();

            default:
                throw new IllegalArgumentException("No Title for position:" + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
