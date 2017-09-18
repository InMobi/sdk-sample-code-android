package com.inmobi.nativead.sample.singlestrand;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.nativead.PlacementId;
import com.inmobi.nativead.sample.R;
import com.inmobi.nativead.utility.SwipeRefreshLayoutWrapper;
import com.squareup.picasso.Picasso;

/**
 * Demonstrates the use of InMobiNativeStrand to place a single ad.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class SingleStrandFragment extends Fragment
        implements InMobiNative.NativeAdListener {
    private static final String TAG = SingleStrandFragment.class.getSimpleName();

    private ViewGroup mContainer;

    private InMobiNative mInMobiNative;

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        final View view = loadAdIntoView(mInMobiNative);
        if (view != null) {
            mContainer.removeAllViews();
            mContainer.addView(view);
        }
    }

    private void createStrands() {
        mInMobiNative = new InMobiNative(getActivity(), PlacementId.YOUR_PLACEMENT_ID_HERE, this);
    }

    @Override
    public void onDestroyView() {
        mInMobiNative.destroy();
        super.onDestroyView();
    }

    private void loadAd() {
        mInMobiNative.load();
    }

    private void clearAd() {
        mContainer.removeAllViews();
        mInMobiNative.destroy();
    }

    private void reloadAd() {
        clearAd();
        createStrands();
        loadAd();
    }

    private View loadAdIntoView(@NonNull final InMobiNative inMobiNative) {
        View adView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_ad, null);

        ImageView icon = (ImageView) adView.findViewById(R.id.adIcon);
        TextView title = (TextView) adView.findViewById(R.id.adTitle);
        TextView description = (TextView) adView.findViewById(R.id.adDescription);
        Button action = (Button) adView.findViewById(R.id.adAction);
        FrameLayout content = (FrameLayout) adView.findViewById(R.id.adContent);
        RatingBar ratingBar = (RatingBar) adView.findViewById(R.id.adRating);

        Picasso.with(getActivity())
                .load(inMobiNative.getAdIconUrl())
                .into(icon);
        title.setText(inMobiNative.getAdTitle());
        description.setText(inMobiNative.getAdDescription());
        action.setText(inMobiNative.getAdCtaText());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        content.addView(inMobiNative.getPrimaryViewOfWidth(content, mContainer, displayMetrics.widthPixels));

        float rating  = inMobiNative.getAdRating();
        if (rating != 0) {
            ratingBar.setRating(rating);
        }
        ratingBar.setVisibility(rating != 0 ? View.VISIBLE : View.GONE);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInMobiNative.reportAdClickAndOpenLandingPage();
            }
        });

        return adView;
    }

    @Override
    public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative) {
        //Pass the old ad view as the first parameter to facilitate view reuse.
        View view = loadAdIntoView(inMobiNative);
        if (view == null) {
            Log.d(TAG, "Could not render Strand!");
        } else {
            mContainer.addView(view);
        }
    }

    @Override
    public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
        Log.d(TAG, "Ad Load failed (" + inMobiAdRequestStatus.getMessage() + ")");
    }

    @Override
    public void onAdFullScreenDismissed(InMobiNative inMobiNative) {}

    @Override
    public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {
        Log.d(TAG, "Ad going fullscreen.");
    }

    @Override
    public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {}

    @Override
    public void onUserWillLeaveApplication(InMobiNative inMobiNative) {}

    @Override
    public void onAdImpressed(@NonNull InMobiNative inMobiNative) {
        Log.d(TAG, "Impression recorded successfully");
    }

    @Override
    public void onAdClicked(@NonNull InMobiNative inMobiNative) {
        Log.d(TAG, "Ad clicked");
    }

    @Override
    public void onMediaPlaybackComplete(@NonNull InMobiNative inMobiNative) {}

    @Override
    public void onAdStatusChanged(@NonNull InMobiNative inMobiNative) {}
}
