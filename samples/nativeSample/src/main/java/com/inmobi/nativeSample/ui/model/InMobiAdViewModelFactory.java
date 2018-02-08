package com.inmobi.nativeSample.ui.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.inmobi.nativeSample.common.domain.interactors.LoadFragmentDataUseCase;

public class InMobiAdViewModelFactory implements ViewModelProvider.Factory {

    private final LoadFragmentDataUseCase loadSingleFragmentUseCase;

    public InMobiAdViewModelFactory(LoadFragmentDataUseCase loadSingleFragmentUseCase) {
        this.loadSingleFragmentUseCase = loadSingleFragmentUseCase;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V extends ViewModel> V create(Class<V> modelClass) {
        if (modelClass.isAssignableFrom(InMobiFragmentViewModel.class)) {
            return (V) new InMobiFragmentViewModel(loadSingleFragmentUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
