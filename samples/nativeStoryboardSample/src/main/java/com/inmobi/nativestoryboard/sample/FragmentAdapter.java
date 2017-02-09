package com.inmobi.nativestoryboard.sample;

import com.inmobi.nativestoryboard.sample.listview.ListViewFeedFragment;
import com.inmobi.nativestoryboard.sample.recyclerview.RecyclerFeedFragment;
import com.inmobi.nativestoryboard.sample.singlestrand.SingleStrandFragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_TABS = 3;

    private static final int POSITION_CUSTOM_INTEGRATION = 0;

    private static final int POSITION_LIST_VIEW_INTEGRATION = 1;

    private static final int POSITION_RECYCLER_VIEW_INTEGRATION = 2;

    public FragmentAdapter(FragmentManager fm, TabLayout tabLayout) {
        super(fm);
        for (int position = 0; position < NUM_TABS; position++) {
            tabLayout.addTab(tabLayout.newTab().setText(getTitle(position)));
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_CUSTOM_INTEGRATION:
                return new SingleStrandFragment();

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
                return SingleStrandFragment.getTitle();

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
