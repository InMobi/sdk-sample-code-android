package com.inmobi.nativeSample.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inmobi.nativeSample.R;
import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.databinding.FragmentListFeedBinding;
import com.inmobi.nativeSample.di.Injectable;
import com.inmobi.nativeSample.ui.list.FeedsListAdapter;
import com.inmobi.nativeSample.ui.model.InMobiAdListViewModelFactory;
import com.inmobi.nativeSample.ui.model.InMobiListFragmentViewModel;
import com.inmobi.nativeSample.ui.recyclerview.AdFeedItem;
import com.inmobi.nativeSample.ui.recyclerview.FeedsAdapter;
import com.inmobi.nativeSample.utility.FeedData.FeedItem;
import com.inmobi.nativeSample.utility.SwipeRefreshLayoutWrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * Demonstrates the use of InMobiNativeStrand to place ads in a RecyclerView.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class ListFeedFragment extends Fragment implements LifecycleOwner, Injectable {

    private static final String TAG = ListFeedFragment.class.getSimpleName();

    private int[] mAdPositions = new int[]{1, 3, 7};

    private ListView mListView;
    private FeedsListAdapter mFeedAdapter;
    private List<FeedItem> mFeedItems = new ArrayList<>();
    @Inject
    InMobiAdListViewModelFactory viewModelFactory;
    private InMobiListFragmentViewModel viewModel;
    ProgressBar loadingIndicator;

    public static String getTitle() {
        return "ListView Placement";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InMobiListFragmentViewModel.class);
        viewModel.response().observe(this, response -> processResponse(response));
        createStrands();
    }


    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState(response);
                break;

            case FEEDS_SUCCESS:
                updateFeedResponse(response);
                break;

            case FEEDS_ERROR:
                renderErrorState(response.error);
                break;

            case ERROR:
                removeAdsStrands(response.data);
                break;

            default:
                break;
        }
    }

    private void removeAdsStrands(FeedItem feedItem) {
        if (feedItem instanceof AdFeedItem) {
            AdFeedItem adFeedItem = (AdFeedItem) feedItem;
            mFeedItems.remove(adFeedItem.getPosition());
            mFeedAdapter.notifyDataSetChanged();
        }
    }


    private void renderLoadingState() {
        mListView.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void renderDataState(Response feedResponse) {
        if (null != feedResponse.data) {
            AdFeedItem adFeedItem = (AdFeedItem) feedResponse.data;
            FeedItem oldFeedItem = mFeedItems.get(adFeedItem.getPosition());
            if (oldFeedItem instanceof AdFeedItem) {
                mFeedItems.remove(oldFeedItem);
            }
            mFeedItems.add(adFeedItem.getPosition(), adFeedItem);
            mFeedAdapter.notifyDataSetChanged();
        }
    }

    private void updateFeedResponse(Response feedResponse) {
        loadingIndicator.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        if (null != feedResponse.listData) {
            mFeedItems.clear();
            mFeedItems.addAll(feedResponse.listData);
            mFeedAdapter.notifyDataSetChanged();
        }
    }

    private void renderErrorState(Throwable throwable) {
        loadingIndicator.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        Toast.makeText(getContext(), R.string.ad_display_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentListFeedBinding listFeedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_feed, container, false);
        mListView = listFeedBinding.listview;
        final SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayoutWrapper.getInstance(getActivity(),
                new SwipeRefreshLayoutWrapper.Listener() {
                    @Override
                    public boolean canChildScrollUp() {
                        return mListView.getVisibility() == View.VISIBLE && canViewScrollUp(mListView);
                    }

                    @Override
                    public void onRefresh() {
                        refreshAds();
                    }
                });
        swipeRefreshLayout.addView(listFeedBinding.getRoot(),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingIndicator = listFeedBinding.loadingIndicator;
        mFeedAdapter = new FeedsListAdapter(mFeedItems, getActivity());
        mListView.setAdapter(mFeedAdapter);
        return swipeRefreshLayout;
    }

    private void createStrands() {
        for (int position : mAdPositions) {
            viewModel.loadAdData(position).observe(this, response -> processResponse(response));
        }
    }

    private void refreshAds() {
        clearAds();
        viewModel.destroyAds();
        createStrands();
    }

    private void clearAds() {
        Iterator<FeedItem> feedItemIterator = mFeedItems.iterator();
        while (feedItemIterator.hasNext()) {
            FeedItem feedItem = feedItemIterator.next();
            if (feedItem instanceof AdFeedItem) {
                feedItemIterator.remove();
            }
        }
    }

    private boolean canViewScrollUp(ListView listView) {
        return listView.canScrollVertically(-1);
    }

    public static Fragment newInstance(Bundle args) {
        ListFeedFragment listFeedFragment = new ListFeedFragment();
        listFeedFragment.setArguments(args);
        return listFeedFragment;
    }
}