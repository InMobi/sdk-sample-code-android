package com.inmobi.nativeSample.di;

import com.inmobi.nativeSample.InMobiAddApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        /* Use AndroidInjectionModule.class if you're not using support library */
        AppModule.class,
        AndroidInjectionModule.class,
        MainActivityModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(InMobiAddApplication application);
        AppComponent build();
    }
    void inject(InMobiAddApplication app);
}
