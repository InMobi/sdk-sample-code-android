package com.inmobi.nativeSample;

import android.app.Activity;
import android.app.Application;

import com.inmobi.nativeSample.di.AppInjector;
import com.inmobi.sdk.InMobiSdk;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by anukalp.katyal on 08/01/18.
 */

public class InMobiAddApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private static InMobiAddApplication sInstance;
    private RefWatcher refWatcher;

    public static InMobiAddApplication getInstance() {
        return sInstance;
    }

    public RefWatcher getRefWatcher() {
        return refWatcher;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            refWatcher = LeakCanary.install(this);
        }

        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiSdk.init(this, "12345678901234567890123456789012");

        AppInjector.init(this);

    }
}
