package com.inmobi.banner.sample;

import com.inmobi.banner.utility.NewsSnippet;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

class NewsFeedAdapter extends ArrayAdapter<NewsSnippet> {

    private LayoutInflater mInflater;
    private List<NewsSnippet> mItems;

    NewsFeedAdapter(Context context, List<NewsSnippet> items) {
        super(context, R.layout.news_headline_view, items);
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if (null == rowView || null == convertView.getTag()) {
            rowView = mInflater.inflate(R.layout.news_headline_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.headline = (TextView) rowView.findViewById(R.id.caption);
            viewHolder.content = (TextView) rowView.findViewById(R.id.content);
            viewHolder.icon = (SimpleDraweeView) rowView.findViewById(R.id.photo);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        NewsSnippet newsSnippet = mItems.get(position);
        viewHolder.headline.setText(newsSnippet.title);
        viewHolder.content.setText(newsSnippet.content);
        viewHolder.icon.setImageURI(Uri.parse(newsSnippet.imageUrl));
        return rowView;
    }


    private static class ViewHolder {
        TextView headline;
        TextView content;
        SimpleDraweeView icon;
    }
}