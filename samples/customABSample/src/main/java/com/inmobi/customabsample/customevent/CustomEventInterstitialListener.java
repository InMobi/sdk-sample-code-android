package com.inmobi.customabsample.customevent;

import android.view.View;

/**
 * CustomEventInterstitialListener is a base class for interstitial custom events that supports interstitial.
 * Note: This is a sample interface and it varies from one PPM to another.
 */
public interface CustomEventInterstitialListener {
    void onAdLoadSuccess();
    void onAdLoadFailed();
    void onAdShowSuccess();
    void onAdShowFailed();
}
