package com.inmobi.nativestoryboard.sample.recyclerview;

import com.inmobi.ads.InMobiNativeStrand;
import com.inmobi.nativestoryboard.utility.FeedData.FeedItem;

public class AdFeedItem extends FeedItem {
    InMobiNativeStrand mNativeStrand;

    public AdFeedItem(InMobiNativeStrand nativeStrand) {
        super("", "", "", "", "", "");
        mNativeStrand = nativeStrand;
    }
}