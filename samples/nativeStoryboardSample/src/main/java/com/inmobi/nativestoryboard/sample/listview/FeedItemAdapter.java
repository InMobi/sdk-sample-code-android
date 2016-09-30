package com.inmobi.nativestoryboard.sample.listview;

import com.inmobi.ads.InMobiNativeStrand;
import com.inmobi.nativestoryboard.sample.R;
import com.inmobi.nativestoryboard.utility.FeedData.FeedItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedItemAdapter extends ArrayAdapter<FeedItem> {
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

    public FeedItemAdapter(Context mContext, ArrayList<FeedItem> mUsers) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

            Picasso.with(mContext)
                    .load(mContext.getResources().getIdentifier(feed.getThumbImage(), "drawable", mContext.getPackageName()))
                    .into(viewHolder.thumb_image);

            Picasso.with(mContext)
                    .load(mContext.getResources().getIdentifier(feed.getBigImage(), "drawable", mContext.getPackageName()))
                    .into(viewHolder.big_image);

            Picasso.with(mContext)
                    .load(R.drawable.linkedin_bottom)
                    .into(viewHolder.bottom_img);

            return convertView;
        } else {
            //If ad feed, request InMobiStrand View. Note InMobiNativeStrand.getStrandView
            //returns null if ad is not loaded.
            //As we add AdFeed only onAdLoadSucceeded & clear the AdFeed onAdLoadFailed
            //ad, here we do not run into Null pointer Exception
            final InMobiNativeStrand nativeStrand = ((AdFeedItem) feedItem).mNativeStrand;
            convertView = nativeStrand.getStrandView(convertView, parent);
            return convertView;
        }
    }

    class ContentViewHolder {
        TextView title;
        TextView subtitle;
        TextView time_tt;
        TextView description_tt;
        ImageView thumb_image;
        ImageView big_image;
        ImageView bottom_img;
    }
}