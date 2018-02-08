package com.inmobi.nativeSample.ui.recyclerview;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativeSample.utility.FeedData;

public class AdFeedItem extends FeedData.FeedItem {
    InMobiNative mNativeStrand;

    @Override
    public String toString() {
        return "AdFeedItem{" +
                "mNativeStrand=" + mNativeStrand +
                ", position=" + position +
                '}';
    }

    int position;

    public AdFeedItem(InMobiNative nativeStrand) {
        super("", "", "", "", "", "");
        mNativeStrand = nativeStrand;
    }

    public AdFeedItem(InMobiNative nativeStrand, int position) {
        this(nativeStrand);
        this.position = position;
    }

    public InMobiNative getmNativeStrand() {
        return mNativeStrand;
    }

    public int getPosition() {
        return position;
    }
}