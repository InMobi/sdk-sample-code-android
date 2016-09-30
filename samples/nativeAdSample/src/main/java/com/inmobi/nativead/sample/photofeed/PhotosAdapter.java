package com.inmobi.nativead.sample.photofeed;

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

public class PhotosAdapter extends ArrayAdapter<PhotosFeedItem> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<PhotosFeedItem> mItems;
    private NativeProvider mNativeProvider;

    public PhotosAdapter(Context context, List<PhotosFeedItem> items, NativeProvider nativeProvider) {
        super(context, R.layout.photos_item_view, items);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mItems = items;
        mNativeProvider = nativeProvider;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView || null == convertView.getTag()) {
            convertView = mInflater.inflate(R.layout.photos_item_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.caption);
            viewHolder.image = (SimpleDraweeView) convertView.findViewById(R.id.photo);
            viewHolder.tag = (TextView) convertView.findViewById(R.id.sponsored);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhotosFeedItem photosFeedItem = mItems.get(position);
        viewHolder.title.setText(photosFeedItem.title);
        viewHolder.image.setImageURI(Uri.parse(photosFeedItem.imageUrl));

        WeakReference<InMobiNative> nativeAdRef = mNativeProvider.provideInmobiNative(photosFeedItem);
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
        TextView title;
        SimpleDraweeView image;
        TextView tag;
    }
}