package com.inmobi.nativead.sample.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import com.inmobi.ads.InMobiNative;
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
    private Context context;

    FeedsAdapter(ArrayList<FeedItem> mFeedItems, Context context) {
        this.mFeedItems = mFeedItems;
        this.context = context;
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
            return new AdViewHolder(context, card);
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

            Picasso.with(context)
                    .load(context.getResources().getIdentifier(feedItem.getThumbImage(), "drawable", context.getPackageName()))
                    .into(feedViewHolder.thumbImage);

            Picasso.with(context)
                    .load(context.getResources().getIdentifier(feedItem.getBigImage(), "drawable", context.getPackageName()))
                    .into(feedViewHolder.image);

            Picasso.with(context)
                    .load(R.drawable.linkedin_bottom)
                    .into(feedViewHolder.bottom);
        } else {
            final AdViewHolder adViewHolder = (AdViewHolder) viewHolder;
            final InMobiNative inMobiNative = ((AdFeedItem) feedItem).mNativeStrand;
            adViewHolder.cardView.removeAllViews();
            adViewHolder.adContent.removeAllViews();

            Picasso.with(context)
                    .load(inMobiNative.getAdIconUrl())
                    .into(adViewHolder.icon);
            adViewHolder.title.setText(inMobiNative.getAdTitle());
            adViewHolder.description.setText(inMobiNative.getAdDescription());
            adViewHolder.action.setText(inMobiNative.getAdCtaText());

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            adViewHolder.adContent.addView(inMobiNative.getPrimaryViewOfWidth(adViewHolder.adView,
                    adViewHolder.cardView, displayMetrics.widthPixels));

            float rating  = inMobiNative.getAdRating();
            if (rating != 0) {
                adViewHolder.ratingBar.setRating(rating);
            }
            adViewHolder.ratingBar.setVisibility(rating != 0 ? View.VISIBLE : View.GONE);

            adViewHolder.action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inMobiNative.reportAdClickAndOpenLandingPage();
                }
            });

            if (adViewHolder.adView == null) {
                Log.e("STRANDS TEST", "getStrandView returned null" + inMobiNative);
            }
            adViewHolder.cardView.addView(adViewHolder.adView);
        }
    }

    private static class AdViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        View adView;

        ImageView icon;
        TextView title, description;
        Button action;
        FrameLayout adContent;
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