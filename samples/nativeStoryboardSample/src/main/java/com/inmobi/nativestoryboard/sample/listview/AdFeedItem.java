package com.inmobi.nativestoryboard.sample.listview;

import com.inmobi.ads.InMobiNativeStrand;

import static com.inmobi.nativestoryboard.utility.FeedData.FeedItem;

public class AdFeedItem extends FeedItem {
    InMobiNativeStrand mNativeStrand;

    public AdFeedItem(InMobiNativeStrand nativeStrand) {
        super("", "", "", "", "", "");
        mNativeStrand = nativeStrand;
    }
}