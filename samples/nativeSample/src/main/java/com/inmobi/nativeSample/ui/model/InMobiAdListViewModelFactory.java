package com.inmobi.nativeSample.ui.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.inmobi.nativeSample.common.domain.interactors.LoadFragmentDataUseCase;

public class InMobiAdListViewModelFactory implements ViewModelProvider.Factory {

    private final LoadFragmentDataUseCase loadNativeData;

    public InMobiAdListViewModelFactory(LoadFragmentDataUseCase loadNativeData) {
        this.loadNativeData = loadNativeData;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InMobiListFragmentViewModel.class)) {
            return (T) new InMobiListFragmentViewModel(loadNativeData);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
