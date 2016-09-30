package com.inmobi.nativestoryboard.sample.listview;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNativeStrand;
import com.inmobi.nativestoryboard.PlacementId;
import com.inmobi.nativestoryboard.utility.FeedData;
import com.inmobi.nativestoryboard.utility.SwipeRefreshLayoutWrapper;

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

    private final List<InMobiNativeStrand> mStrands = new ArrayList<>();

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
            final InMobiNativeStrand nativeStrand = new InMobiNativeStrand(getActivity(),
                    PlacementId.YOUR_PLACEMENT_ID_HERE, new StrandAdListener(position));
            mStrands.add(nativeStrand);
        }
    }

    private void loadAds() {
        for (InMobiNativeStrand strand : mStrands) {
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
        for (InMobiNativeStrand strand : mStrands) {
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


    private final class StrandAdListener implements InMobiNativeStrand.NativeStrandAdListener {

        private int mPosition;

        StrandAdListener(int position) {
            mPosition = position;
        }

        @Override
        public void onAdLoadSucceeded(@NonNull InMobiNativeStrand inMobiNativeStrand) {
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
        public void onAdLoadFailed(@NonNull InMobiNativeStrand inMobiNativeStrand, @NonNull final InMobiAdRequestStatus inMobiAdRequestStatus) {
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
        public void onAdImpressed(@NonNull InMobiNativeStrand inMobiNativeStrand) {
            Log.d(TAG, "Impression recorded for strand at position:" + mPosition);
        }

        @Override
        public void onAdClicked(@NonNull InMobiNativeStrand inMobiNativeStrand) {
            Log.d(TAG, "Click recorded for ad at position:" + mPosition);
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
