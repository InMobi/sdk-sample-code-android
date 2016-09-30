package com.inmobi.nativead.sample.newsheadline;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativead.sample.R;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;
import java.util.List;

public class FeedAdapter extends ArrayAdapter<NewsSnippet> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<NewsSnippet> mItems;
    private NativeProvider mNativeProvider;

    public FeedAdapter(Context context, List<NewsSnippet> items, NativeProvider nativeProvider) {
        super(context, R.layout.news_headline_view, items);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mItems = items;
        mNativeProvider = nativeProvider;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (null == convertView || null == convertView.getTag()) {
            convertView = mInflater.inflate(R.layout.news_headline_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.headline = (TextView) convertView.findViewById(R.id.caption);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.icon = (SimpleDraweeView) convertView.findViewById(R.id.photo);
            viewHolder.tag = (TextView) convertView.findViewById(R.id.sponsored);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsSnippet newsSnippet = mItems.get(position);
        viewHolder.headline.setText(newsSnippet.title);
        viewHolder.content.setText(newsSnippet.content);
        viewHolder.icon.setImageURI(Uri.parse(newsSnippet.imageUrl));

        WeakReference<InMobiNative> nativeAdRef = mNativeProvider.provideInmobiNative(newsSnippet);
        if (null == nativeAdRef) {
            viewHolder.tag.setVisibility(View.GONE);
            InMobiNative.unbind(convertView);
        } else {
            // we have an ad at this position
            InMobiNative nativeAd = nativeAdRef.get();
            if (nativeAd != null) {
                viewHolder.tag.setVisibility(View.VISIBLE);
                viewHolder.tag.setText("Sponsored");
                InMobiNative.bind(convertView, nativeAd);
            }
        }

        return convertView;
    }

    private class ViewHolder {
        TextView headline;
        TextView content;
        TextView tag;
        SimpleDraweeView icon;
    }
}