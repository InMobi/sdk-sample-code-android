package com.inmobi.nativeSample.common.domain.interactors;

import android.arch.lifecycle.LiveData;

import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.utility.FeedData;

public interface LoadInMobiCommonUseCase<T extends Response, V extends FeedData.FeedItem> {
    LiveData<T> executeAd(int position);
    LiveData<V> executeFeedData();
    void destroy();
}
