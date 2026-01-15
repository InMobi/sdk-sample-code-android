package com.inmobi.nativead.sample.listview;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inmobi.ads.InMobiNative;
import com.inmobi.media.ads.nativeAd.InMobiNativeViewData;
import com.inmobi.nativead.sample.R;
import com.inmobi.nativead.utility.FeedData.FeedItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class FeedItemAdapter extends ArrayAdapter<FeedItem> {
    private Context mContext;
    private ArrayList<FeedItem> mUsers;
    private LayoutInflater mLayoutInflater;
    //Number of types of Views that Content feed wishes to display
    private static final int NUM_CONTENT_FEED_VIEW_TYPES = 1;

    //InmobiStrand always uses the same type of view to display Ad.
    private static final int NUM_AD_FEED_VIEW_TYPES = 1;

    //View type for Content Feed.
    private static final int VIEW_TYPE_CONTENT_FEED = 0;

    //View type for Ad Feed - from InMobi (InMobi Native Strand)
    private static final int VIEW_TYPE_INMOBI_STRAND = 1;

    private static final String TAG = FeedItemAdapter.class.getSimpleName();

    FeedItemAdapter(Context mContext, ArrayList<FeedItem> mUsers) {
        super(mContext, R.layout.listitem, R.id.title, mUsers);
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getViewTypeCount() {
        return NUM_CONTENT_FEED_VIEW_TYPES + NUM_AD_FEED_VIEW_TYPES;
    }

    @Override
    public int getItemViewType(int position) {
        FeedItem feedItem = getItem(position);
        if (feedItem instanceof AdFeedItem) {
            return VIEW_TYPE_INMOBI_STRAND;
        }
        return VIEW_TYPE_CONTENT_FEED;
    }

    @Override
    public FeedItem getItem(int position) {
        return mUsers.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final int itemViewType = getItemViewType(position);
        final FeedItem feedItem = getItem(position);

        if (itemViewType == VIEW_TYPE_CONTENT_FEED) {
            ContentViewHolder viewHolder;
            if (null == convertView || null == convertView.getTag()) {
                convertView = mLayoutInflater.inflate(R.layout.listitem, parent, false);
                viewHolder = new ContentViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.subtitle = (TextView) convertView.findViewById(R.id.subtitle);
                viewHolder.time_tt = (TextView) convertView.findViewById(R.id.time_tt);
                viewHolder.description_tt = (TextView) convertView.findViewById(R.id.description_tt);
                viewHolder.thumb_image = (ImageView) convertView.findViewById(R.id.thumb_image);
                viewHolder.big_image = (ImageView) convertView.findViewById(R.id.big_image);
                viewHolder.bottom_img = (ImageView) convertView.findViewById(R.id.bottom_img);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ContentViewHolder) convertView.getTag();
            }

            FeedItem feed = getItem(position);
            viewHolder.title.setText(feed.getTitle());
            viewHolder.subtitle.setText(feed.getSubtitle());
            viewHolder.time_tt.setText(feed.getTimestamp());
            viewHolder.description_tt.setText(feed.getDescription());

            Picasso.get()
                    .load(mContext.getResources().getIdentifier(feed.getThumbImage(), "drawable", mContext.getPackageName()))
                    .into(viewHolder.thumb_image);

            Picasso.get()
                    .load(mContext.getResources().getIdentifier(feed.getBigImage(), "drawable", mContext.getPackageName()))
                    .into(viewHolder.big_image);

            Picasso.get()
                    .load(R.drawable.linkedin_bottom)
                    .into(viewHolder.bottom_img);

            return convertView;
        } else {
            //If ad feed, request InMobiStrand View. Note InMobiNativeStrand.getStrandView
            //returns null if ad is not loaded.
            //As we add AdFeed only onAdLoadSucceeded & clear the AdFeed onAdLoadFailed
            //ad, here we do not run into Null pointer Exception
            final InMobiNative inMobiNative = ((AdFeedItem) feedItem).mNativeStrand;

            AdViewHolder viewHolder;
            if (null == convertView || null == convertView.getTag()) {
                convertView = mLayoutInflater.inflate(R.layout.layout_ad, parent, false);
                viewHolder = new AdViewHolder();
                viewHolder.icon = (ImageView) convertView.findViewById(R.id.adIcon);
                viewHolder.title = (TextView) convertView.findViewById(R.id.adTitle);
                viewHolder.description = (TextView) convertView.findViewById(R.id.adDescription);
                viewHolder.action = (Button) convertView.findViewById(R.id.adAction);
                viewHolder.content = (FrameLayout) convertView.findViewById(R.id.adContent);
                viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.adRating);
                viewHolder.sponsored = (TextView) convertView.findViewById(R.id.adSponsored);
                viewHolder.adChoice = (FrameLayout) convertView.findViewById(R.id.adChoice);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (AdViewHolder) convertView.getTag();
            }

            // Load icon image
            if (inMobiNative.getAdIcon() != null && inMobiNative.getAdIcon().getUrl() != null) {
                Picasso.get()
                        .load(inMobiNative.getAdIcon().getUrl())
                        .into(viewHolder.icon);
            }

            // Populate text views
            viewHolder.title.setText(inMobiNative.getAdTitle());
            viewHolder.description.setText(inMobiNative.getAdDescription());
            viewHolder.action.setText(inMobiNative.getCtaText());
            viewHolder.sponsored.setText(inMobiNative.getAdvertiserName());

            // Load media view
            viewHolder.content.removeAllViews();
            final InMobiNative nativeAd = inMobiNative;
            final FrameLayout mediaContainer = viewHolder.content;
            viewHolder.content.post(new Runnable() {
                @Override
                public void run() {
                    View mediaView = nativeAd.getMediaView();
                    if (mediaView != null) {
                        if (mediaView.getParent() != null) {
                            ((ViewGroup) mediaView.getParent()).removeView(mediaView);
                        }
                        int mediaHeight = mContext.getResources().getDimensionPixelSize(R.dimen.native_ad_media_height);
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
                viewHolder.ratingBar.setRating(rating);
                viewHolder.ratingBar.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ratingBar.setVisibility(View.GONE);
            }

            // Add AdChoice icon
            viewHolder.adChoice.removeAllViews();
            View adChoiceIcon = inMobiNative.getAdChoiceIcon();
            if (adChoiceIcon != null) {
                if (adChoiceIcon.getParent() != null) {
                    ((ViewGroup) adChoiceIcon.getParent()).removeAllViews();
                }
                viewHolder.adChoice.addView(adChoiceIcon);
            }

            // Build InMobiNativeViewData to register views for interaction
            InMobiNativeViewData viewData = new InMobiNativeViewData.Builder((ViewGroup) convertView)
                    .setIconView(viewHolder.icon)
                    .setTitleView(viewHolder.title)
                    .setDescriptionView(viewHolder.description)
                    .setCTAView(viewHolder.action)
                    .setRatingView(viewHolder.ratingBar)
                    .setExtraViews(java.util.Arrays.asList(viewHolder.sponsored))
                    .build();

            // Register all interactive views with InMobi SDK for proper tracking and video playback.
            // This is CRITICAL for video ads to function correctly - without this call,
            // video will not play and clicks will not be tracked.
            inMobiNative.registerViewForTracking(viewData);

            return convertView;
        }
    }

    private static class ContentViewHolder {
        TextView title;
        TextView subtitle;
        TextView time_tt;
        TextView description_tt;
        ImageView thumb_image;
        ImageView big_image;
        ImageView bottom_img;
    }

    private static class AdViewHolder {
        TextView title, description, sponsored;
        ImageView icon;
        Button action;
        FrameLayout content, adChoice;
        RatingBar ratingBar;
    }
}