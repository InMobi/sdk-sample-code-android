package com.inmobi.nativeSample;

import android.app.Activity;
import android.app.Application;

import com.inmobi.nativeSample.di.AppInjector;
import com.inmobi.sdk.InMobiSdk;

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

    public static InMobiAddApplication getInstance() {
        return sInstance;
    }


    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiSdk.init(this, "12345678901234567890123456789012");

        AppInjector.init(this);

    }
}
