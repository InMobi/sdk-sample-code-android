package com.inmobi.nativeSample.common.domain.interactors;

import android.arch.lifecycle.LiveData;

import com.inmobi.nativeSample.common.domain.repository.CommonInMobiAdDataRepository;
import com.inmobi.nativeSample.common.domain.repository.NativeFeedDataRepository;
import com.inmobi.nativeSample.common.viewmodel.Response;

import javax.inject.Inject;

public class LoadFragmentDataUseCase implements LoadInMobiCommonUseCase {
    private final CommonInMobiAdDataRepository adDataRepository;
    private final NativeFeedDataRepository nativeDataRepository;

    @Inject
    public LoadFragmentDataUseCase(CommonInMobiAdDataRepository adDataRepository, NativeFeedDataRepository nativeDataRepository) {
        this.adDataRepository = adDataRepository;
        this.nativeDataRepository = nativeDataRepository;
    }

    @Override
    public LiveData<Response> executeAd(int position) {
        return adDataRepository.getInMobiAdCallbacks(position);
    }

    @Override
    public LiveData<Response> executeFeedData() {
        return nativeDataRepository.getFeedData();
    }

    @Override
    public void destroy() {
        adDataRepository.destroy();
    }
}
