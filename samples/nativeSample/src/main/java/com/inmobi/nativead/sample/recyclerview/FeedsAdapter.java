package com.inmobi.nativead.sample.recyclerview;

import android.app.Activity;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

class FeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //View type for Content Feed.
    private static final int VIEW_TYPE_CONTENT_FEED = 0;

    //View type for Ad Feed - from InMobi (InMobi Native Strand)
    private static final int VIEW_TYPE_INMOBI_STRAND = 1;

    private ArrayList<FeedItem> mFeedItems;
    private Context mContext;

    FeedsAdapter(ArrayList<FeedItem> mFeedItems, Context mContext) {
        this.mFeedItems = mFeedItems;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return mFeedItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        final FeedItem feedItem = mFeedItems.get(position);
        if (feedItem instanceof AdFeedItem) {
            return VIEW_TYPE_INMOBI_STRAND;
        }
        return VIEW_TYPE_CONTENT_FEED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View card = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_card_layout,
                viewGroup,
                false);
        if (viewType == VIEW_TYPE_CONTENT_FEED) {
            LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem, (ViewGroup) card, true);
            return new FeedViewHolder(card);
        } else {
            return new AdViewHolder(mContext, card);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FeedItem feedItem = mFeedItems.get(position);
        if (viewHolder instanceof FeedViewHolder) {
            FeedViewHolder feedViewHolder = (FeedViewHolder) viewHolder;
            feedViewHolder.title.setText(feedItem.getTitle());
            feedViewHolder.subTitle.setText(feedItem.getSubtitle());
            feedViewHolder.timeStamp.setText(feedItem.getTimestamp());
            feedViewHolder.description.setText(feedItem.getDescription());

            Picasso.get()
                    .load(mContext.getResources().getIdentifier(feedItem.getThumbImage(), "drawable", mContext.getPackageName()))
                    .into(feedViewHolder.thumbImage);

            Picasso.get()
                    .load(mContext.getResources().getIdentifier(feedItem.getBigImage(), "drawable", mContext.getPackageName()))
                    .into(feedViewHolder.image);

            Picasso.get()
                    .load(R.drawable.linkedin_bottom)
                    .into(feedViewHolder.bottom);
        } else {
            final AdViewHolder adViewHolder = (AdViewHolder) viewHolder;
            final InMobiNative inMobiNative = ((AdFeedItem) feedItem).mNativeStrand;

            // Load icon image
            if (inMobiNative.getAdIcon() != null && inMobiNative.getAdIcon().getUrl() != null) {
                Picasso.get()
                        .load(inMobiNative.getAdIcon().getUrl())
                        .into(adViewHolder.icon);
            }

            // Populate text views
            adViewHolder.title.setText(inMobiNative.getAdTitle());
            adViewHolder.description.setText(inMobiNative.getAdDescription());
            adViewHolder.action.setText(inMobiNative.getCtaText());
            adViewHolder.sponsored.setText(inMobiNative.getAdvertiserName());

            // Load media view
            adViewHolder.adContent.removeAllViews();
            final InMobiNative nativeAd = inMobiNative;
            final FrameLayout mediaContainer = adViewHolder.adContent;
            adViewHolder.adContent.post(new Runnable() {
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
                        android.util.Log.d("FeedsAdapter", "Media view is null for native ad");
                    }
                }
            });

            // Set rating if available
            float rating = inMobiNative.getAdRating();
            if (rating > 0) {
                adViewHolder.ratingBar.setRating(rating);
                adViewHolder.ratingBar.setVisibility(View.VISIBLE);
            } else {
                adViewHolder.ratingBar.setVisibility(View.GONE);
            }

            // Add AdChoice icon
            adViewHolder.adChoice.removeAllViews();
            View adChoiceIcon = inMobiNative.getAdChoiceIcon();
            if (adChoiceIcon != null) {
                if (adChoiceIcon.getParent() != null) {
                    ((ViewGroup) adChoiceIcon.getParent()).removeAllViews();
                }
                adViewHolder.adChoice.addView(adChoiceIcon);
            }

            // Build InMobiNativeViewData to register views for interaction
            InMobiNativeViewData viewData = new InMobiNativeViewData.Builder((ViewGroup) adViewHolder.adView)
                    .setIconView(adViewHolder.icon)
                    .setTitleView(adViewHolder.title)
                    .setDescriptionView(adViewHolder.description)
                    .setCTAView(adViewHolder.action)
                    .setRatingView(adViewHolder.ratingBar)
                    .setExtraViews(java.util.Arrays.asList(adViewHolder.sponsored))
                    .build();

            // Register all interactive views with InMobi SDK for proper tracking and video playback.
            // This is CRITICAL for video ads to function correctly - without this call,
            // video will not play and clicks will not be tracked.
            inMobiNative.registerViewForTracking(viewData);
        }
    }

    private static class AdViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        View adView;

        ImageView icon;
        TextView title, description, sponsored;
        Button action;
        FrameLayout adContent, adChoice;
        RatingBar ratingBar;

        AdViewHolder(Context context, View adCardView) {
            super(adCardView);
            cardView = (CardView) adCardView;
            adView = LayoutInflater.from(context).inflate(R.layout.layout_ad, null);

            icon = (ImageView) adView.findViewById(R.id.adIcon);
            title = (TextView) adView.findViewById(R.id.adTitle);
            description = (TextView) adView.findViewById(R.id.adDescription);
            action = (Button) adView.findViewById(R.id.adAction);
            adContent = (FrameLayout) adView.findViewById(R.id.adContent);
            ratingBar = (RatingBar) adView.findViewById(R.id.adRating);
            sponsored = (TextView) adView.findViewById(R.id.adSponsored);
            adChoice = (FrameLayout) adView.findViewById(R.id.adChoice);
            cardView.addView(adView);
        }
    }

    private static class FeedViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView thumbImage;
        TextView title;
        TextView subTitle;
        TextView timeStamp;
        TextView description;
        ImageView image;
        ImageView bottom;

        FeedViewHolder(View feedCardView) {
            super(feedCardView);
            cardView = (CardView) feedCardView;
            thumbImage = (ImageView) feedCardView.findViewById(R.id.thumb_image);
            title = (TextView) feedCardView.findViewById(R.id.title);
            subTitle = (TextView) feedCardView.findViewById(R.id.subtitle);
            timeStamp = (TextView) feedCardView.findViewById(R.id.time_tt);
            description = (TextView) feedCardView.findViewById(R.id.description_tt);
            image = (ImageView) feedCardView.findViewById(R.id.big_image);
            bottom = (ImageView) feedCardView.findViewById(R.id.bottom_img);
        }
    }
}