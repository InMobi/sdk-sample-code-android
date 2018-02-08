package com.inmobi.nativeSample.di;

import com.inmobi.nativeSample.ui.ListFeedFragment;
import com.inmobi.nativeSample.ui.RecyclerFeedFragment;
import com.inmobi.nativeSample.ui.SingleNativeAdFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = InMobiAdModule.class)
    abstract SingleNativeAdFragment bindAdFragment();


    @ContributesAndroidInjector(modules = InMobiAdModule.class)
    abstract RecyclerFeedFragment bindAdRecylerFragment();

    @ContributesAndroidInjector(modules = InMobiAdModule.class)
    abstract ListFeedFragment bindAdListFragment();

}
