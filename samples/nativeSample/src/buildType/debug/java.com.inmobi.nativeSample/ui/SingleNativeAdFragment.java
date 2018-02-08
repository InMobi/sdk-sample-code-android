package com.inmobi.nativeSample.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativeSample.BuildConfig;
import com.inmobi.nativeSample.R;
import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.databinding.FragmentCustomIntegrationBinding;
import com.inmobi.nativeSample.di.Injectable;
import com.inmobi.nativeSample.ui.model.InMobiAdViewModelFactory;
import com.inmobi.nativeSample.ui.model.InMobiFragmentViewModel;
import com.inmobi.nativeSample.ui.recyclerview.AdFeedItem;
import com.inmobi.nativeSample.utility.FeedData;
import com.inmobi.nativeSample.utility.SwipeRefreshLayoutWrapper;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

/**
 * Demonstrates the use of InMobiNative to place a single ad.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class SingleNativeAdFragment extends Fragment implements LifecycleOwner, Injectable {
    private static final String TAG = SingleNativeAdFragment.class.getSimpleName();

    private FragmentCustomIntegrationBinding fragmentBinding;

    @Inject
    InMobiAdViewModelFactory viewModelFactory;

    private InMobiFragmentViewModel viewModel;

    @Inject
    RefWatcher refWatcher;

    View adLayoutView;
    ProgressBar loadingIndicator;

    private InMobiNative mInMobiNative;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InMobiFragmentViewModel.class);
        viewModel.loadAd().observe(this, response -> processResponse(response));
        viewModel.setPosition(0);
    }

    private void processResponse(Response response) {
        Log.d(TAG, "processResponse :: " + response.status + " response.data : " + response.data);
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState((AdFeedItem) response.data);
                break;

            case ERROR:
                renderErrorState(response.error);
                break;
        }
    }

    private void renderLoadingState() {
        adLayoutView.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void renderDataState(AdFeedItem feedItem) {
        loadingIndicator.setVisibility(View.GONE);
        adLayoutView.setVisibility(View.VISIBLE);
        mInMobiNative = feedItem.getmNativeStrand();
        loadAdIntoView(mInMobiNative);
    }

    private void renderErrorState(Throwable throwable) {
        loadingIndicator.setVisibility(View.GONE);
        adLayoutView.setVisibility(View.GONE);
        Toast.makeText(getContext(), R.string.ad_display_error, Toast.LENGTH_SHORT).show();
    }

    public static String getTitle() {
        return "Custom Placement";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_integration, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayoutWrapper.getInstance(getActivity(),
                new SwipeRefreshLayoutWrapper.Listener() {
                    @Override
                    public boolean canChildScrollUp() {
                        return false;
                    }

                    @Override
                    public void onRefresh() {
                        reloadAd();
                    }
                });
        swipeRefreshLayout.addView(fragmentBinding.getRoot(), 0);
        loadingIndicator = fragmentBinding.loadingIndicator;
        adLayoutView = fragmentBinding.adLayout.getRoot();

        return swipeRefreshLayout;
    }

    @Override
    public void onDetach() {
        if (BuildConfig.DEBUG) {
            refWatcher.watch(this);
        }
        super.onDetach();
    }


    private void reloadAd() {
        viewModel.reloadAd();
    }

    private void loadAdIntoView(@NonNull final InMobiNative inMobiNative) {
        FeedData.AdNativeData adNativeData = new FeedData.AdNativeData(inMobiNative);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        adNativeData.setWidth(displayMetrics.widthPixels);
        fragmentBinding.setModel(adNativeData);
        fragmentBinding.executePendingBindings();
        fragmentBinding.notifyChange();
    }

    public static SingleNativeAdFragment newInstance(Bundle bundle) {
        SingleNativeAdFragment singleNativeAdFragment = new SingleNativeAdFragment();
        singleNativeAdFragment.setArguments(bundle);
        return singleNativeAdFragment;
    }
}
