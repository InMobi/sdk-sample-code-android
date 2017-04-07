package com.inmobi.nativestoryboard.sample.prefetch;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNativeStrand;
import com.inmobi.nativestoryboard.sample.R;
import com.inmobi.nativestoryboard.sample.StrandsApplication;
import com.inmobi.nativestoryboard.utility.StrandsFetcher;
import com.inmobi.nativestoryboard.utility.SwipeRefreshLayoutWrapper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demonstrates the use of InMobiNativeStrand prefetch to place a single ad.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class SinglePrefetchFragment extends Fragment
        implements InMobiNativeStrand.NativeStrandAdListener {

    private static final String TAG = SinglePrefetchFragment.class.getSimpleName();

    private ViewGroup mContainer;

    private View mAdView;

    private InMobiNativeStrand mInMobiNativeStrand;

    private StrandsApplication strandsApplication;
    private StrandsFetcher strandsFetcher;
    private AtomicInteger forcedRetry = new AtomicInteger(0);


    public static String getTitle() {
        return "Prefetch Placement";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_integration, container, false);
        mContainer = (ViewGroup) view.findViewById(R.id.container);

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
        swipeRefreshLayout.addView(mContainer);
        return swipeRefreshLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        createStrands();
        Log.d(TAG, "Requesting ad");
    }

    private void createStrands() {
        mInMobiNativeStrand = strandsApplication.getStrands();
        if (null == mInMobiNativeStrand) {
            strandsApplication.fetchStrands(strandsFetcher);
            return;
        }
        mInMobiNativeStrand.setNativeStrandAdListener(this);
        //Providing activity context to show ad
        mInMobiNativeStrand.load(getActivity());
    }

    @Override
    public void onAdLoadSucceeded(@NonNull InMobiNativeStrand nativeStrand) {
        //Pass the old ad view as the first parameter to facilitate view reuse.
        mAdView = nativeStrand.getStrandView(mAdView, mContainer);
        if (mAdView == null) {
            Log.d(TAG, "Could not render Strand!");
        } else {
            mContainer.addView(mAdView);
        }
    }

    @Override
    public void onAdLoadFailed(@NonNull InMobiNativeStrand nativeStrand,
                               @NonNull final InMobiAdRequestStatus inMobiAdRequestStatus) {
        Log.d(TAG, "Ad Load failed (" + inMobiAdRequestStatus.getMessage() + ")");
    }

    @Override
    public void onAdImpressed(@NonNull InMobiNativeStrand inMobiNativeStrand) {
        Log.d(TAG, "Impression recorded successfully");
    }

    @Override
    public void onAdClicked(@NonNull InMobiNativeStrand inMobiNativeStrand) {
        Log.d(TAG, "Ad clicked");
    }

    @Override
    public void onDestroyView() {
        if (null != mInMobiNativeStrand) {
            mInMobiNativeStrand.destroy();
        }
        super.onDestroyView();
    }


    private void clearAd() {
        mContainer.removeAllViews();
        if (null != mInMobiNativeStrand) {
            mInMobiNativeStrand.destroy();
        }
    }

    private void reloadAd() {
        clearAd();
        createStrands();
    }

}
