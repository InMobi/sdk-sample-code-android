package com.inmobi.nativestoryboard.sample.adplacer;

import com.inmobi.ads.InMobiStrandAdapter;
import com.inmobi.ads.InMobiStrandPositioning;
import com.inmobi.nativestoryboard.PlacementId;
import com.inmobi.nativestoryboard.utility.FeedData;
import com.inmobi.nativestoryboard.utility.FeedData.FeedItem;
import com.inmobi.nativestoryboard.utility.SwipeRefreshLayoutWrapper;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Demonstrates integration via {@link InMobiStrandAdapter}.
 */
public final class AdPlacerFeedFragment extends ListFragment {

    private static final String TAG = AdPlacerFeedFragment.class.getSimpleName();

    private InMobiStrandAdapter mStrandAdapter;

    private ArrayList<FeedItem> mFeedItems;

    private final InMobiStrandAdapter.NativeStrandAdListener mAdListener = new InMobiStrandAdapter.NativeStrandAdListener() {
        @Override
        public void onAdLoadSucceeded(int i) {
            Log.d(TAG, "Strand loaded at position " + i);
        }

        @Override
        public void onAdRemoved(int i) {
            Log.d(TAG, "Strand removed at position: " + i);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View listFragmentView = super.onCreateView(inflater, container, savedInstanceState);
        final SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayoutWrapper.getInstance(getActivity(),
                new SwipeRefreshLayoutWrapper.Listener() {
                    @Override
                    public boolean canChildScrollUp() {
                        final ListView listView = getListView();
                        return listView.getVisibility() == View.VISIBLE && canListViewScrollUp(listView);
                    }

                    @Override
                    public void onRefresh() {
                        refreshAds();
                    }
                });
        swipeRefreshLayout.addView(listFragmentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return swipeRefreshLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFeedItems = FeedData.generateFeedItems(50);
        //Load the ads after activity has been created.
        prepareToLoadAds();
    }

    private void prepareToLoadAds() {
        final BaseAdapter originalAdapter = new FeedItemAdapter(getActivity(), mFeedItems);
        final InMobiStrandPositioning.InMobiClientPositioning clientPositioning =
                new InMobiStrandPositioning.InMobiClientPositioning();
        clientPositioning.addFixedPosition(2)
                .addFixedPosition(4)
                .enableRepeatingPositions(5);

        if (mStrandAdapter != null) {
            mStrandAdapter.destroy();
        }
        mStrandAdapter = new InMobiStrandAdapter(getActivity(),
                PlacementId.YOUR_PLACEMENT_ID_HERE, originalAdapter, clientPositioning);
        mStrandAdapter.setListener(mAdListener);
        originalAdapter.notifyDataSetChanged();
        mStrandAdapter.load();
        setListAdapter(mStrandAdapter);
    }

    public static String getTitle() {
        return "Ad Placer";
    }

    @Override
    public void onDestroyView() {
        if (mStrandAdapter != null) {
            mStrandAdapter.destroy();
        }
        super.onDestroyView();
    }

    private void refreshAds() {
        if (null == mStrandAdapter || null == getListView()) {
            Log.e(TAG, "The list-view or the adapter cannot be null!");
        } else {
            mStrandAdapter.refreshAds(getListView());
        }
    }

    private boolean canListViewScrollUp(ListView listView) {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(listView, -1);
        } else {
            return listView.getChildCount() > 0 &&
                    (listView.getFirstVisiblePosition() > 0
                            || listView.getChildAt(0).getTop() < listView.getPaddingTop());
        }
    }
}
