package com.inmobi.nativead.sample.newsfeed;

import com.inmobi.ads.InMobiNative;

import java.lang.ref.WeakReference;

public interface NativeProvider {
    WeakReference<InMobiNative> provideInmobiNative(NewsTileItem newsTileItem);
}
