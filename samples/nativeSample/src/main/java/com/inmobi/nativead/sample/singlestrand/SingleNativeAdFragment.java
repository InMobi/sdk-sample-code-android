package com.inmobi.nativead.sample.singlestrand;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.inmobi.ads.listeners.VideoEventListener;
import com.inmobi.media.ads.nativeAd.InMobiNativeViewData;
import com.inmobi.nativead.PlacementId;
import com.inmobi.nativead.sample.R;
import com.inmobi.nativead.utility.SwipeRefreshLayoutWrapper;
import com.squareup.picasso.Picasso;

/**
 * Demonstrates the use of InMobiNative to place a single ad.
 * <p/>
 * Note: Swipe to refresh ads.
 */
public class SingleNativeAdFragment extends Fragment {
    private static final String TAG = SingleNativeAdFragment.class.getSimpleName();

    private ViewGroup mContainer;
    private InMobiNative mInMobiNative;

    public static String getTitle() {
        return "Custom Placement";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_integration, container, false);
        mContainer = view.findViewById(R.id.container);

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
        mContainer.removeAllViews();
        mContainer.addView(view);
    }

    private void createStrands() {
        mInMobiNative = new InMobiNative(getActivity(), PlacementId.YOUR_PLACEMENT_ID_HERE, new StrandAdListener());
        mInMobiNative.setVideoEventListener(new VideoEventListener() {
            @Override
            public void onVideoCompleted(InMobiNative inMobiNative) {
                super.onVideoCompleted(inMobiNative);
                Log.d(TAG, "Video completed");
            }

            @Override
            public void onVideoSkipped(InMobiNative inMobiNative) {
                super.onVideoSkipped(inMobiNative);
                Log.d(TAG, "Video skipped");
            }

            @Override
            public void onAudioStateChanged(InMobiNative inMobiNative, boolean b) {
                super.onAudioStateChanged(inMobiNative, b);
                Log.d(TAG, "Audio state changed");
            }

            @Override
            public void onVideoStarted(InMobiNative inMobiNative) {
                super.onVideoStarted(inMobiNative);
                Log.d(TAG, "Video started");
            }

            @Override
            public void onVideoResumed(InMobiNative inMobiNative) {
                super.onVideoResumed(inMobiNative);
                Log.d(TAG, "Video resumed");
            }
        });
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
        TextView sponsored = (TextView) adView.findViewById(R.id.adSponsored);
        FrameLayout adChoice = (FrameLayout) adView.findViewById(R.id.adChoice);

        // Load icon image
        if (inMobiNative.getAdIcon() != null && inMobiNative.getAdIcon().getUrl() != null) {
            Picasso.get()
                    .load(inMobiNative.getAdIcon().getUrl())
                    .into(icon);
        }

        // Populate text views
        title.setText(inMobiNative.getAdTitle());
        description.setText(inMobiNative.getAdDescription());
        action.setText(inMobiNative.getCtaText());
        sponsored.setText(inMobiNative.getAdvertiserName());

        // Load media view with layout params
        content.removeAllViews();
        final InMobiNative nativeAd = inMobiNative;
        final FrameLayout mediaContainer = content;
        content.post(new Runnable() {
            @Override
            public void run() {
                View mediaView = nativeAd.getMediaView();
                if (mediaView != null) {
                    if (mediaView.getParent() != null) {
                        ((ViewGroup) mediaView.getParent()).removeView(mediaView);
                    }
                    int mediaHeight = getResources().getDimensionPixelSize(R.dimen.native_ad_media_height);
                    mediaView.setLayoutParams(new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            mediaHeight
                    ));
                    mediaContainer.addView(mediaView);
                } else {
                    Log.d(TAG, "Media view is null for native ad");
                }
            }
        });

        // Set rating if available
        float rating = inMobiNative.getAdRating();
        if (rating > 0) {
            ratingBar.setRating(rating);
            ratingBar.setVisibility(View.VISIBLE);
        } else {
            ratingBar.setVisibility(View.GONE);
        }

        // Add AdChoice icon
        adChoice.removeAllViews();
        View adChoiceIcon = inMobiNative.getAdChoiceIcon();
        if (adChoiceIcon != null) {
            if (adChoiceIcon.getParent() != null) {
                ((ViewGroup) adChoiceIcon.getParent()).removeAllViews();
            }
            adChoice.addView(adChoiceIcon);
        }

        // Build InMobiNativeViewData to register views for interaction
        InMobiNativeViewData viewData = new InMobiNativeViewData.Builder((ViewGroup) adView)
                .setIconView(icon)
                .setTitleView(title)
                .setDescriptionView(description)
                .setCTAView(action)
                .setRatingView(ratingBar)
                .setExtraViews(java.util.Arrays.asList(sponsored))
                .build();

        // Register all interactive views with InMobi SDK for proper tracking and video playback.
        // This is CRITICAL for video ads to function correctly - without this call,
        // video will not play and clicks will not be tracked.
        inMobiNative.registerViewForTracking(viewData);

        return adView;
    }

    private final class StrandAdListener extends NativeAdEventListener {


        public StrandAdListener() {
        }

        @Override
        public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative,
                                      @NonNull AdMetaInfo adMetaInfo) {
            Log.d(TAG, "Ad Load succeeded with bid " + adMetaInfo.getBid());
            View view = loadAdIntoView(inMobiNative);
            mContainer.addView(view);
        }

        @Override
        public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
            Log.d(TAG, "Ad Load failed (" + inMobiAdRequestStatus.getMessage() + ")");
        }

        @Override
        public void onAdFetchSuccessful(@NonNull InMobiNative inMobiNative, @NonNull AdMetaInfo adMetaInfo) {
            Log.d(TAG, "onAdFetchSuccessful with bid " + adMetaInfo.getBid());
        }

        @Override
        public void onAdFullScreenDismissed(InMobiNative inMobiNative) {
            Log.d(TAG, "Ad fullscreen dismissed.");
        }

        @Override
        public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {
            Log.d(TAG, "Ad is fullscreen.");
        }

        @Override
        public void onUserWillLeaveApplication(InMobiNative inMobiNative) {
            Log.d(TAG, "User will leave application.");
        }

        @Override
        public void onAdClicked(@NonNull InMobiNative inMobiNative) {
            Log.d(TAG, "Ad clicked");
        }

        @Override
        public void onAdImpression(@NonNull InMobiNative inMobiNative) {
            Log.d(TAG, "Ad impression logged");
        }

    }
}
