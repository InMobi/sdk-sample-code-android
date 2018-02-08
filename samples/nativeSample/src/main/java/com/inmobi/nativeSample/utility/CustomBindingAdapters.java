package com.inmobi.nativeSample.utility;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inmobi.ads.InMobiNative;
import com.squareup.picasso.Picasso;

/**
 * Created by anukalp.katyal on 09/01/18.
 */

public class CustomBindingAdapters {

    @BindingAdapter(value = "imageSrc")
    public static void setImageSrc(ImageView imageView, String imageSrc) {
        if (TextUtils.isEmpty(imageSrc)) {
            return;
        }
        Context context = imageView.getContext();

        Picasso.with(context)
                .load(context.getResources().getIdentifier(imageSrc, "drawable", context.getPackageName()))
                .into(imageView);
    }

    @BindingAdapter(value = "imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        Context context = imageView.getContext();
        Picasso.with(context)
                .load(imageUrl)
                .fit()
                .into(imageView);
    }

    @BindingAdapter(value = "imageRes")
    public static void setImageResource(ImageView imageView, int imageResId) {
        Context context = imageView.getContext();
        if (imageResId == 0) {
            return;
        }
        Picasso.with(context)
                .load(imageResId)
                .into(imageView);
    }

    @BindingAdapter(value = "inmobiAdView")
    public static void setAdView(ViewGroup viewGroup, FeedData.AdNativeData adNativeData) {
        if (null == adNativeData) {
            return;
        }
        InMobiNative inMobiNative = adNativeData.getAdNative();
        if (viewGroup.getChildCount() > 0) {
            viewGroup.removeAllViews();
        }
        if (null != inMobiNative.getPrimaryViewOfWidth(viewGroup.getContext(), viewGroup, (ViewGroup) viewGroup.getParent(), adNativeData.getWidth())) {
            viewGroup.addView(inMobiNative.getPrimaryViewOfWidth(viewGroup.getContext(), viewGroup, (ViewGroup) viewGroup.getParent(), adNativeData.getWidth()));
        }
    }

    @BindingAdapter(value = "recyclerItemGone")
    public static void setItemVisibility(ViewGroup viewGroup, FeedData.AdNativeData adNativeData) {
        if (null == adNativeData) {
            return;
        }
        InMobiNative inMobiNative = adNativeData.getAdNative();
        if (null == inMobiNative.getPrimaryViewOfWidth(viewGroup.getContext(), viewGroup, (ViewGroup) viewGroup.getParent(), adNativeData.getWidth())) {
            ViewGroup.LayoutParams layoutParams = ((ViewGroup) viewGroup.getParent()).getLayoutParams();
            layoutParams.height = 0;
            viewGroup.requestLayout();
        } else {
            ViewGroup.LayoutParams layoutParams = ((ViewGroup) viewGroup.getParent()).getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            viewGroup.requestLayout();
        }
    }
}
