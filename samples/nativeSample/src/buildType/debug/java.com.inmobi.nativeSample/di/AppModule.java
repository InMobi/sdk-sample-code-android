package com.inmobi.nativeSample.di;

import android.content.Context;

import com.inmobi.nativeSample.InMobiAddApplication;
import com.inmobi.nativeSample.utility.AppExecutors;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class AppModule {

    @Provides
    Context provideContext(InMobiAddApplication application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    RefWatcher provideRefWatcher() {
        return InMobiAddApplication.getInstance().getRefWatcher();
    }

}
