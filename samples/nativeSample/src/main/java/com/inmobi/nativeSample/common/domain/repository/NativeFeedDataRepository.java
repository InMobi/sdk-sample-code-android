package com.inmobi.nativeSample.common.domain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.common.viewmodel.Status;
import com.inmobi.nativeSample.utility.FeedData;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by anukalp.katyal on 29/01/18.
 */

public class NativeFeedDataRepository {

    MutableLiveData<Response> listMutableLiveData = new MutableLiveData<>();

    @Inject
    public NativeFeedDataRepository() {
    }

    public LiveData<Response> getFeedData() {
        Flowable.just(Response.successFeedResponse(FeedData.generateFeedItems(30), Status.FEEDS_SUCCESS)).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(response ->
                        listMutableLiveData.postValue(response),
                error -> Response.feedsError(error)
        );
        return listMutableLiveData;
    }
}
