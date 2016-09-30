package com.inmobi.nativead.sample.photopages;

import com.inmobi.ads.InMobiNative;

import java.lang.ref.WeakReference;

public interface NativeProvider {
    WeakReference<InMobiNative> provideInmobiNative(PageItem pageItem);
}
