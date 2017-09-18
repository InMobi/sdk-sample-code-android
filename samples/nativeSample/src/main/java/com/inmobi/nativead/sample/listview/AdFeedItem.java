package com.inmobi.nativead.sample.listview;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativead.utility.FeedData.FeedItem;

public class AdFeedItem extends FeedItem {
    InMobiNative mNativeStrand;

    AdFeedItem(InMobiNative nativeStrand) {
        super("", "", "", "", "", "");
        mNativeStrand = nativeStrand;
    }
}