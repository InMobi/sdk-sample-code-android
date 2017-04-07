package com.inmobi.nativestoryboard.sample.prefetch;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNativeStrand;
import com.inmobi.nativestoryboard.sample.StrandsApplication;
import com.inmobi.nativestoryboard.utility.StrandsFetcher;
import com.inmobi.nativestoryboard.sample.listview.AdFeedItem;
import com.inmobi.nativestoryboard.sample.listview.FeedItemAdapter;
import com.inmobi.nativestoryboard.utility.FeedData;
import com.inmobi.nativestoryboard.utility.SwipeRefreshLayoutWrapper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demonstrates the use of InMobiNativeStrand to place ads in a ListView.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class ListViewPrefetchFragment extends ListFragment {

    private static final String TAG = ListViewPrefetchFragment.class.getSimpleName();


    //Position in feed where the Ads needs to be placed once loaded.
    // Note: Actual position where ad is visible depends on
    // 1. availability of ad (case like NO_FILL)
    // 2. Order in which ad response arrives.
    private int[] mAdPositions = new int[]{0, 3, 6, 17};

    private static final int NUM_FEED_ITEMS = 20;

    private AtomicInteger forcedRetry = new AtomicInteger(0);

    private BaseAdapter mFeedAdapter;

    private ArrayList<FeedData.FeedItem> mFeedItems;
    private ConcurrentHashMap<Integer, Boolean> postionMap = new ConcurrentHashMap<>(5, 0.9f, 5);
    private final List<InMobiNativeStrand> mStrands = new ArrayList<>();


    private StrandsApplication strandsApplication;
    private StrandsFetcher strandsFetcher;

    public static String getTitle() {
        return "Prefetch List Placement";
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
        refreshPositionMap();
        strandsApplication = (StrandsApplication) getActivity().getApplication();

        strandsFetcher = new StrandsFetcher() {
            @Override
            public void onFetchSuccess() {
                createStrands();
            }

            @Override
            public void onFetchFailure() {
                if (forcedRetry.getAndIncrement() < 2) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            strandsApplication.fetchStrands(strandsFetcher);
                        }
                    }, 2000);
                }

            }
        };


        mFeedItems = FeedData.generateFeedItems(NUM_FEED_ITEMS);
        mFeedAdapter = new FeedItemAdapter(getActivity(), mFeedItems);
        setListAdapter(mFeedAdapter);
        for (int i = 0; i < mAdPositions.length; i++) {
            Log.e("SOS", "Creating Strands for position: " + mAdPositions[i]);
            createStrands();
        }
        Log.e("SOS", "Loading Strands..");
    }

    private void refreshPositionMap() {
        for (Integer pos : mAdPositions) {
            postionMap.put(pos, false);
        }
    }

    private void createStrands() {
        InMobiNativeStrand nativeStrand = strandsApplication.getStrands();
        if (null == nativeStrand) {
            strandsApplication.fetchStrands(strandsFetcher);
            return;
        }
        nativeStrand.setNativeStrandAdListener(new InMobiNativeStrand.NativeStrandAdListener() {

            @Override
            public void onAdLoadSucceeded(@NonNull InMobiNativeStrand inMobiNativeStrand) {
                AdFeedItem adFeedItem = new AdFeedItem(inMobiNativeStrand);
                int mPosition = getEmptyPosition();
                if (mPosition >= mFeedItems.size()) {
                    mPosition = mFeedItems.size();
                }

                Log.d(TAG, "Strand loaded at position " + mPosition);
                postionMap.put(mPosition, true);
                mFeedItems.add(mPosition, adFeedItem);
                mFeedAdapter.notifyDataSetChanged();

            }

            @Override
            public void onAdLoadFailed(@NonNull InMobiNativeStrand inMobiNativeStrand, @NonNull final InMobiAdRequestStatus inMobiAdRequestStatus) {

                if (mFeedItems.contains(inMobiNativeStrand)) {
                    int position = mFeedItems.indexOf(inMobiNativeStrand);
                    Log.d(TAG, "Ad removed for" + position);
                    postionMap.put(position, false);
                    mFeedAdapter.notifyDataSetChanged();

                }
                Log.d(TAG, "Ad Load failed " + "(" + inMobiAdRequestStatus.getMessage() + ")");
            }

            @Override
            public void onAdImpressed(@NonNull InMobiNativeStrand inMobiNativeStrand) {
                Log.d(TAG, "Impression recorded for strand at position:" + mFeedItems.indexOf(inMobiNativeStrand));
            }

            @Override
            public void onAdClicked(@NonNull InMobiNativeStrand inMobiNativeStrand) {
                Log.d(TAG, "Impression recorded for strand at position:" + mFeedItems.indexOf(inMobiNativeStrand));
            }
        });

        mStrands.add(nativeStrand);
        //Providing activity context to show ad
        nativeStrand.load(getActivity());
    }


    private void refreshAds() {
        clearAds();
        refreshPositionMap();
        for (int i = 0; i < mAdPositions.length; i++) {
            createStrands();
        }
    }

    private void clearAds() {
        mFeedItems.clear();
        mFeedAdapter.notifyDataSetChanged();
        for (InMobiNativeStrand strand : mStrands) {
            strand.destroy();
        }
        mStrands.clear();
        forcedRetry.set(0);
    }

    @Override
    public void onDestroyView() {
        clearAds();
        super.onDestroyView();
    }

    private int getEmptyPosition() {
        int min = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Boolean> entry : postionMap.entrySet()) {
            Log.e("SOS", "Map set: " + postionMap.entrySet());
            int pos = entry.getKey();
            boolean filled = entry.getValue();
            if (!filled) {
                if (min > pos) {
                    min = pos;
                }
            }
        }
        Log.e("SOS", "position returned " + min);
        return min;
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