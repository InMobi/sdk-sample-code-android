package com.inmobi.nativeSample.common.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativeSample.ui.recyclerview.AdFeedItem;
import com.inmobi.nativeSample.utility.FeedData;

import java.util.List;


/**
 * Response holder provided to the UI
 */
public class Response {

    public final Status status;

    @Nullable
    public FeedData.FeedItem data;

    @Nullable
    public List<FeedData.FeedItem> listData;

    @Nullable
    public final Throwable error;

    private Response(Status status, @Nullable FeedData.FeedItem data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    private Response(@Nullable List<FeedData.FeedItem> data, Status status, @Nullable Throwable error) {
        this.status = status;
        this.listData = data;
        this.error = error;
    }

    public static Response loading() {
        return new Response(Status.LOADING, null, null);
    }

    public static Response success(@NonNull InMobiNative data, Status status, int position) {
        if (position != 0) {
            return new Response(status, new AdFeedItem(data, position), null);
        } else {
            return new Response(status, new AdFeedItem(data), null);
        }

    }

    public static Response successFeedResponse(@NonNull List<FeedData.FeedItem> listData, Status status) {
        return new Response(listData, status, null);
    }

    public static Response error(@NonNull Throwable error) {
        return new Response(Status.ERROR, null, error);
    }

    public static Response feedsError(@NonNull Throwable error) {
        return new Response(Status.FEEDS_ERROR, null, error);
    }
}
