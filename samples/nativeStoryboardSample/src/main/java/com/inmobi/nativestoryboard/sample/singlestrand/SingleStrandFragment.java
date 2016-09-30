package com.inmobi.nativestoryboard.sample.singlestrand;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNativeStrand;
import com.inmobi.nativestoryboard.PlacementId;
import com.inmobi.nativestoryboard.sample.R;
import com.inmobi.nativestoryboard.utility.SwipeRefreshLayoutWrapper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Demonstrates the use of InMobiNativeStrand to place a single ad.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class SingleStrandFragment extends Fragment
        implements InMobiNativeStrand.NativeStrandAdListener {

    private static final String TAG = SingleStrandFragment.class.getSimpleName();

    private ViewGroup mContainer;

    private View mAdView;

    private InMobiNativeStrand mInMobiNativeStrand;


    public static String getTitle() {
        return "Custom Placement";
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
        createStrands();
        Log.d(TAG, "Requesting ad");
        loadAd();
    }

    private void createStrands() {
        mInMobiNativeStrand = new InMobiNativeStrand(getActivity(), PlacementId.YOUR_PLACEMENT_ID_HERE, this);
    }

    @Override
    public void onAdLoadSucceeded(@NonNull InMobiNativeStrand nativeStrand) {
        clearAd();
        //Pass the old ad view as the first parameter to facilitate view reuse.
        mAdView = mInMobiNativeStrand.getStrandView(mAdView, mContainer);
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
        mInMobiNativeStrand.destroy();
        super.onDestroyView();
    }

    private void loadAd() {
        mInMobiNativeStrand.load();
    }

    private void clearAd() {
        mContainer.removeAllViews();
        mInMobiNativeStrand.destroy();
    }

    private void reloadAd() {
        clearAd();
        createStrands();
        loadAd();
    }

}
