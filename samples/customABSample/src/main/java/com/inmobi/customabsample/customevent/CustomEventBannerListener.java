package com.inmobi.customabsample.customevent;

import android.view.View;

/**
 * CustomEventListener is a base class for banner custom events that supports banner.
 * Note: This is a sample interface and it varies from one PPM to another.
 */
public interface CustomEventBannerListener {
    void onAdLoadSuccess(View view);
    void onAdLoadFailed();
}
