package com.inmobi.nativeSample.ui.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativeSample.R;
import com.inmobi.nativeSample.databinding.ListitemBinding;
import com.inmobi.nativeSample.databinding.RecyclerCardLayoutBinding;
import com.inmobi.nativeSample.utility.FeedData;
import com.inmobi.nativeSample.utility.FeedData.FeedItem;

import java.util.List;

public class FeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //View type for Content Feed.
    private static final int VIEW_TYPE_CONTENT_FEED = 0;

    //View type for Ad Feed - from InMobi (InMobi Native Strand)
    private static final int VIEW_TYPE_INMOBI_STRAND = 1;
    private final LayoutInflater layoutInflater;

    private final List<FeedItem> mFeedItems;
    private DisplayMetrics displayMetrics;


    public FeedsAdapter(List<FeedItem> mFeedItems, Context context) {
        this.mFeedItems = mFeedItems;
        this.layoutInflater = LayoutInflater.from(context);
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    public int getItemCount() {
        return null != mFeedItems ? mFeedItems.size() : 0;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        super.onViewRecycled(viewHolder);
        if (viewHolder instanceof AdViewHolder) {
            final AdViewHolder adViewHolder = (AdViewHolder) viewHolder;
            adViewHolder.onPause();
        }
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
        if (viewType == VIEW_TYPE_CONTENT_FEED) {
            return new FeedViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.listitem, viewGroup, false));
        } else {
            return new AdViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.recycler_card_layout, viewGroup, false), displayMetrics.widthPixels);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        FeedItem feedItem = mFeedItems.get(position);
        if (viewHolder instanceof FeedViewHolder) {
            FeedViewHolder feedViewHolder = (FeedViewHolder) viewHolder;
            feedViewHolder.bindData(feedItem);
        } else {
            final AdViewHolder adViewHolder = (AdViewHolder) viewHolder;
            final InMobiNative inMobiNative = ((AdFeedItem) feedItem).mNativeStrand;
            adViewHolder.bindData(inMobiNative);
        }
    }

    private static class AdViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerCardLayoutBinding itemBinding;
        private final FeedData.AdNativeData adFeedItem;

        AdViewHolder(RecyclerCardLayoutBinding recyclerCardLayoutBinding, int widthPixels) {
            super(recyclerCardLayoutBinding.getRoot());
            this.itemBinding = recyclerCardLayoutBinding;
            this.adFeedItem = new FeedData.AdNativeData();
            adFeedItem.setWidth(widthPixels);

        }

        public void bindData(InMobiNative adFeedData) {
            this.adFeedItem.bindData(adFeedData);
            itemBinding.setModel(adFeedItem);
            itemBinding.executePendingBindings();
            itemBinding.notifyChange();
        }


        public void onPause() {
            this.adFeedItem.onPause();
        }
    }

    private static class FeedViewHolder extends RecyclerView.ViewHolder {
        private final ListitemBinding itemBinding;

        FeedViewHolder(ListitemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.itemBinding = listItemBinding;
        }

        public void bindData(FeedItem feedData) {
            FeedItem oldFeedData = itemBinding.getFeedData();
            itemBinding.setFeedData(feedData);
            if (null == oldFeedData) {
                itemBinding.executePendingBindings();
            } else {
                itemBinding.notifyChange();
            }
        }
    }
}