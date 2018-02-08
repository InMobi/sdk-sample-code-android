package com.inmobi.nativeSample.ui.list;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativeSample.R;
import com.inmobi.nativeSample.databinding.ListitemBinding;
import com.inmobi.nativeSample.databinding.RecyclerCardLayoutBinding;
import com.inmobi.nativeSample.ui.recyclerview.AdFeedItem;
import com.inmobi.nativeSample.utility.FeedData;
import com.inmobi.nativeSample.utility.FeedData.FeedItem;

import java.util.List;

public class FeedsListAdapter extends ArrayAdapter<FeedItem> {

    //View type for Content Feed.
    private static final int VIEW_TYPE_CONTENT_FEED = 0;

    //View type for Ad Feed - from InMobi (InMobi Native Strand)
    private static final int VIEW_TYPE_INMOBI_STRAND = 1;
    private final LayoutInflater layoutInflater;

    private final List<FeedItem> mFeedItems;
    private DisplayMetrics displayMetrics;


    public FeedsListAdapter(List<FeedItem> mFeedItems, Context context) {
        super(context, R.layout.listitem, mFeedItems);
        this.mFeedItems = mFeedItems;
        this.layoutInflater = LayoutInflater.from(context);
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    public int getCount() {
        return null != mFeedItems ? mFeedItems.size() : 0;
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
    public FeedItem getItem(int position) {
        return mFeedItems.get(position);
    }


    private boolean invalidateView(View convertView, int itemType) {
        if (null == convertView) {
            return true;
        }
        BaseListHolder listHolder = (BaseListHolder) convertView.getTag();
        if ((itemType == VIEW_TYPE_INMOBI_STRAND && listHolder instanceof FeedViewHolder) || (itemType == VIEW_TYPE_CONTENT_FEED && listHolder instanceof AdViewHolder)) {
            return true;
        }

        return false;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup viewGroup) {
        final FeedItem feedItem = getItem(position);
        int itemType = getItemViewType(position);
        BaseListHolder listHolder;
        if (invalidateView(convertView, itemType)) {
            if (itemType == VIEW_TYPE_INMOBI_STRAND) {
                listHolder = new AdViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.recycler_card_layout, viewGroup, false), displayMetrics.widthPixels);
            } else {
                listHolder = new FeedViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.listitem, viewGroup, false));
            }
            convertView = listHolder.getConvertView();
            convertView.setTag(listHolder);
        } else {
            listHolder = (BaseListHolder) convertView.getTag();
        }
        bindView(listHolder, feedItem);
        return convertView;
    }

    private void bindView(BaseListHolder viewHolder, FeedItem feedItem) {
        if (viewHolder instanceof FeedViewHolder) {
            FeedViewHolder feedViewHolder = (FeedViewHolder) viewHolder;
            feedViewHolder.bindData(feedItem);
        } else {
            AdViewHolder adViewHolder = (AdViewHolder) viewHolder;
            final InMobiNative inMobiNative = ((AdFeedItem) feedItem).getmNativeStrand();
            adViewHolder.bindData(inMobiNative);
        }
    }

    private static class AdViewHolder extends BaseListHolder {
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

    }

    private static class FeedViewHolder extends BaseListHolder {
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

    private static abstract class BaseListHolder {

        private final View convertView;

        protected BaseListHolder(View convertView) {
            this.convertView = convertView;
        }

        public View getConvertView() {
            return convertView;
        }

    }
}