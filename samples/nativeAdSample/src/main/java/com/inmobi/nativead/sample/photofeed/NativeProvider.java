package com.inmobi.nativead.sample.photofeed;

import com.inmobi.ads.InMobiNative;

import java.lang.ref.WeakReference;

public interface NativeProvider {
    WeakReference<InMobiNative> provideInmobiNative(PhotosFeedItem photosFeedItem);
}
