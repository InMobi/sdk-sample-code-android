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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inmobi.nativeSample.BuildConfig;
import com.inmobi.nativeSample.R;
import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.databinding.FragmentRecyclerFeedBinding;
import com.inmobi.nativeSample.di.Injectable;
import com.inmobi.nativeSample.ui.model.InMobiAdListViewModelFactory;
import com.inmobi.nativeSample.ui.model.InMobiListFragmentViewModel;
import com.inmobi.nativeSample.ui.recyclerview.AdFeedItem;
import com.inmobi.nativeSample.ui.recyclerview.FeedsAdapter;
import com.inmobi.nativeSample.utility.FeedData.FeedItem;
import com.inmobi.nativeSample.utility.SwipeRefreshLayoutWrapper;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Demonstrates the use of InMobiNativeStrand to place ads in a RecyclerView.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class RecyclerFeedFragment extends Fragment implements LifecycleOwner, Injectable {

    private static final String TAG = RecyclerFeedFragment.class.getSimpleName();

    private int[] mAdPositions = new int[]{3, 8, 18};

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mFeedAdapter;
    private List<FeedItem> mFeedItems = new ArrayList<>();
    @Inject
    InMobiAdListViewModelFactory viewModelFactory;
    private InMobiListFragmentViewModel viewModel;
    ProgressBar loadingIndicator;
    @Inject
    RefWatcher refWatcher;

    public static String getTitle() {
        return "RecyclerView Placement";
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
            mFeedAdapter.notifyItemRemoved(adFeedItem.getPosition());
        }
    }


    private void renderLoadingState() {
        mRecyclerView.setVisibility(View.GONE);
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
        mRecyclerView.setVisibility(View.VISIBLE);
        if (null != feedResponse.listData) {
            mFeedItems.clear();
            mFeedItems.addAll(feedResponse.listData);
            mFeedAdapter.notifyDataSetChanged();
        }
    }

    private void renderErrorState(Throwable throwable) {
        loadingIndicator.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        Toast.makeText(getContext(), R.string.ad_display_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentRecyclerFeedBinding recyclerFeedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_feed, container, false);
        mRecyclerView = recyclerFeedBinding.recyclerView;
        mRecyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        final SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayoutWrapper.getInstance(getActivity(),
                new SwipeRefreshLayoutWrapper.Listener() {
                    @Override
                    public boolean canChildScrollUp() {
                        return mRecyclerView.getVisibility() == View.VISIBLE && canViewScrollUp(mRecyclerView);
                    }

                    @Override
                    public void onRefresh() {
                        refreshAds();
                    }
                });
        swipeRefreshLayout.addView(recyclerFeedBinding.getRoot(),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingIndicator = recyclerFeedBinding.loadingIndicator;
        mFeedAdapter = new FeedsAdapter(mFeedItems, getActivity());
        mRecyclerView.setAdapter(mFeedAdapter);
        return swipeRefreshLayout;
    }

    private void createStrands() {
        for (int position : mAdPositions) {
            viewModel.loadAdData(position).observe(this, response -> processResponse(response));
        }
    }

    private void refreshAds() {
        viewModel.destroyAds();
        createStrands();
    }

    @Override
    public void onDetach() {
        if (BuildConfig.DEBUG) {
            refWatcher.watch(this);
        }
        super.onDetach();
    }

    private boolean canViewScrollUp(RecyclerView recyclerView) {
        return ViewCompat.canScrollVertically(recyclerView, -1);
    }

    public static Fragment newInstance(Bundle args) {
        RecyclerFeedFragment recyclerFeedFragment = new RecyclerFeedFragment();
        recyclerFeedFragment.setArguments(args);
        return recyclerFeedFragment;
    }
}