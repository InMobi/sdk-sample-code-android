package com.inmobi.nativestoryboard.sample.adplacer;

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
    private ArrayList<FeedItem> users;
    private LayoutInflater layoutInflater;
    private Context context;

    public FeedItemAdapter(Context context, ArrayList<FeedItem> users) {
        super(context, R.layout.listitem, R.id.title, users);
        this.users = users;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public FeedItem getItem(int position) {
        return users.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContentViewHolder viewHolder;
        if (null == convertView || null == convertView.getTag()) {
            convertView = layoutInflater.inflate(R.layout.listitem, parent, false);
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

        FeedItem user = getItem(position);
        viewHolder.title.setText(user.getTitle());
        viewHolder.subtitle.setText(user.getSubtitle());
        viewHolder.time_tt.setText(user.getTimestamp());
        viewHolder.description_tt.setText(user.getDescription());

        Picasso.with(context).load(context.getResources().getIdentifier(user.getThumbImage(), "drawable", context.getPackageName()))
                .into(viewHolder.thumb_image);

        Picasso.with(context).load(context.getResources().getIdentifier(user.getBigImage(), "drawable", context.getPackageName()))
                .into(viewHolder.big_image);

        Picasso.with(context).load(R.drawable.linkedin_bottom)
                .into(viewHolder.bottom_img);

        return convertView;
    }

    private class ContentViewHolder {
        TextView title;
        TextView subtitle;
        TextView time_tt;
        TextView description_tt;
        ImageView thumb_image;
        ImageView big_image;
        ImageView bottom_img;
    }
}