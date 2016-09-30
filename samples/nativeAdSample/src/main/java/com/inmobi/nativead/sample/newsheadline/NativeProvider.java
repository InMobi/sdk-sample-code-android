package com.inmobi.nativead.sample.newsheadline;

import com.inmobi.ads.InMobiNative;

import java.lang.ref.WeakReference;

public interface NativeProvider {
    WeakReference<InMobiNative> provideInmobiNative(NewsSnippet newsSnippet);
}
