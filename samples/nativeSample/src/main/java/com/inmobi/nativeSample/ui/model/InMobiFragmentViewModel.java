package com.inmobi.nativeSample.ui.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.inmobi.nativeSample.common.domain.interactors.LoadFragmentDataUseCase;
import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.utility.AbsentLiveData;

public class InMobiFragmentViewModel extends ViewModel {

    private final LoadFragmentDataUseCase loadInMobiAdUseCase;
    private final LiveData<Response> adResponseData;
    @VisibleForTesting
    final MutableLiveData<Integer> postionData;

    public InMobiFragmentViewModel(LoadFragmentDataUseCase loadInMobiAdUseCase) {
        postionData = new MutableLiveData<>();
        this.loadInMobiAdUseCase = loadInMobiAdUseCase;
        adResponseData = Transformations.switchMap(postionData, input -> {
            if (input < 0) {
                return AbsentLiveData.create();
            }
            return loadInMobiAdUseCase.executeAd(input);
        });
    }

    @Override
    public void onCleared() {
        loadInMobiAdUseCase.destroy();
    }


    @VisibleForTesting
    public void setPosition(int position) {
        postionData.setValue(position);
    }

    public LiveData<Response> loadAd() {
        return adResponseData;
    }

    public void reloadAd() {
        setPosition(0);
    }
}
