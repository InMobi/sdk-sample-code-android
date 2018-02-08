package com.inmobi.nativeSample.di;

import com.inmobi.nativeSample.common.domain.interactors.LoadFragmentDataUseCase;
import com.inmobi.nativeSample.ui.model.InMobiAdListViewModelFactory;
import com.inmobi.nativeSample.ui.model.InMobiAdViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define LobbyActivity-specific dependencies here.
 */
@Module
public class InMobiAdModule {

    @Provides
    InMobiAdViewModelFactory provideAdViewModelFactory(LoadFragmentDataUseCase loadNativeData) {
        return new InMobiAdViewModelFactory(loadNativeData);
    }

    @Provides
    InMobiAdListViewModelFactory provideAdListViewModelFactory(
            LoadFragmentDataUseCase loadInMobiAdUseCase) {
        return new InMobiAdListViewModelFactory(loadInMobiAdUseCase);
    }

}
