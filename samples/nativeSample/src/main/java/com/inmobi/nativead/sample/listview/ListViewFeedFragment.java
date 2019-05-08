package com.inmobi.nativead.sample.listview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.inmobi.nativead.PlacementId;
import com.inmobi.nativead.utility.FeedData;
import com.inmobi.nativead.utility.SwipeRefreshLayoutWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates the use of InMobiNativeStrand to place ads in a ListView.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class ListViewFeedFragment extends ListFragment {

    private static final String TAG = ListViewFeedFragment.class.getSimpleName();

    private final List<InMobiNative> mStrands = new ArrayList<>();

    //Position in feed where the Ads needs to be placed once loaded.
    // Note: Actual position where ad is visible depends on
    // 1. availability of ad (case like NO_FILL)
    // 2. Order in which ad response arrives.
    private int[] mAdPositions = new int[]{3, 6, 17};

    private static final int NUM_FEED_ITEMS = 20;

    private BaseAdapter mFeedAdapter;
    private ArrayList<FeedData.FeedItem> mFeedItems;
    private Map<Integer, FeedData.FeedItem> mFeedMap = new HashMap<>();

    public static String getTitle() {
        return "ListView Placement";
    }

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
        mFeedItems = FeedData.generateFeedItems(NUM_FEED_ITEMS);
        mFeedAdapter = new FeedItemAdapter(getActivity(), mFeedItems);
        setListAdapter(mFeedAdapter);
        createStrands();
        loadAds();
    }

    private void createStrands() {
        for (int position : mAdPositions) {
            final InMobiNative nativeStrand = new InMobiNative(getActivity(),
                    PlacementId.YOUR_PLACEMENT_ID_HERE, new StrandAdListener(position));

            mStrands.add(nativeStrand);
        }
    }

    private void loadAds() {
        for (InMobiNative strand : mStrands) {
            strand.load();
        }
    }

    private void refreshAds() {
        clearAds();
        createStrands();
        loadAds();
    }

    private void clearAds() {
        Iterator<FeedData.FeedItem> feedItemIterator = mFeedItems.iterator();
        while (feedItemIterator.hasNext()) {
            final FeedData.FeedItem feedItem = feedItemIterator.next();
            if (feedItem instanceof AdFeedItem) {
                feedItemIterator.remove();
            }
        }
        mFeedAdapter.notifyDataSetChanged();
        for (InMobiNative strand : mStrands) {
            strand.destroy();
        }
        mStrands.clear();
        mFeedMap.clear();
    }

    @Override
    public void onDestroyView() {
        clearAds();
        super.onDestroyView();
    }


    private final class StrandAdListener extends NativeAdEventListener {

        private int mPosition;

        StrandAdListener(int position) {
            mPosition = position;
        }

        @Override
        public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNativeStrand) {
            super.onAdLoadSucceeded(inMobiNativeStrand);
            Log.d(TAG, "Strand loaded at position " + mPosition);
            if (!mFeedItems.isEmpty()) {
                FeedData.FeedItem oldFeedItem = mFeedMap.get(mPosition);
                if (oldFeedItem != null) {
                    mFeedMap.remove(mPosition);
                    mFeedItems.remove(oldFeedItem);
                }
                AdFeedItem adFeedItem = new AdFeedItem(inMobiNativeStrand);
                mFeedMap.put(mPosition, adFeedItem);
                mFeedItems.add(mPosition, adFeedItem);
                mFeedAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onAdLoadFailed(@NonNull InMobiNative inMobiNativeStrand, @NonNull final InMobiAdRequestStatus inMobiAdRequestStatus) {
            super.onAdLoadFailed(inMobiNativeStrand, inMobiAdRequestStatus);
            Log.d(TAG, "Ad Load failed  for" + mPosition + "(" + inMobiAdRequestStatus.getMessage() + ")");
            if (!mFeedItems.isEmpty()) {
                FeedData.FeedItem oldFeedItem = mFeedMap.get(mPosition);
                if (oldFeedItem != null) {
                    mFeedMap.remove(mPosition);
                    mFeedItems.remove(oldFeedItem);
                    mFeedAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Ad removed for" + mPosition);
                }
            }
        }

        @Override
        public void onAdReceived(InMobiNative inMobiNative) {
            super.onAdReceived(inMobiNative);
            Log.d(TAG, "onAdReceived");
        }

        @Override
        public void onAdFullScreenDismissed(InMobiNative inMobiNative) {
            super.onAdFullScreenDismissed(inMobiNative);
            Log.d(TAG, "Ad fullscreen dismissed.");
        }

        @Override
        public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {
            super.onAdFullScreenWillDisplay(inMobiNative);
            Log.d(TAG, "Ad going fullscreen.");
        }

        @Override
        public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {
            super.onAdFullScreenDisplayed(inMobiNative);
            Log.d(TAG, "Ad fullscreen displayed.");
        }

        @Override
        public void onUserWillLeaveApplication(InMobiNative inMobiNative) {
            super.onUserWillLeaveApplication(inMobiNative);
            Log.d(TAG, "User left app.");
        }

        @Override
        public void onAdImpressed(@NonNull InMobiNative inMobiNativeStrand) {
            super.onAdImpressed(inMobiNativeStrand);
            Log.d(TAG, "Impression recorded for strand at position:" + mPosition);
        }

        @Override
        public void onAdClicked(@NonNull InMobiNative inMobiNativeStrand) {
            super.onAdClicked(inMobiNativeStrand);
            Log.d(TAG, "Click recorded for ad at position:" + mPosition);
        }

        @Override
        public void onAdStatusChanged(@NonNull InMobiNative inMobiNative) {
            super.onAdStatusChanged(inMobiNative);
            Log.d(TAG, "Ad status changed");
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
