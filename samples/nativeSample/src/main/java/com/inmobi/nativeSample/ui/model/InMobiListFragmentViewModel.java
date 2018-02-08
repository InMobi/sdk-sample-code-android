package com.inmobi.nativeSample.ui.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativeSample.common.domain.interactors.LoadFragmentDataUseCase;
import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.common.viewmodel.Status;

import io.reactivex.disposables.CompositeDisposable;

public class InMobiListFragmentViewModel extends ViewModel {

    private final LoadFragmentDataUseCase loadNativeData;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    InMobiListFragmentViewModel(LoadFragmentDataUseCase loadNativeData) {
        this.loadNativeData = loadNativeData;
    }

    @Override
    public void onCleared() {
        loadNativeData.destroy();
        disposables.clear();
    }

    public void destroyAds() {
        loadNativeData.destroy();
    }

    public LiveData<Response> loadAdData(int position) {
        return loadInMobiAd(loadNativeData, position);
    }

    public LiveData<Response> response() {
        return loadNativeData.executeFeedData();
    }

    private LiveData<Response> loadInMobiAd(LoadFragmentDataUseCase loadInMobiUseCase, int position) {
        return loadInMobiUseCase.executeAd(position);
    }

    @VisibleForTesting
    public void setAdData(InMobiNative inMobiNative) {
        Response responseData = Response.success(inMobiNative, Status.SUCCESS, 0);
        response.setValue(responseData);
    }

}
