package com.inmobi.customabsample;

import android.view.View;

import java.util.Map;

/**
    This interface is implemented to show how PPM will be giving callbacks to the publisher
    <b>Note:</b> It should not be implemented by publisher
 */
public interface CustomEventBannerListener {
    void onAdDisplayed();
    void onAdDismissed();
    void onUserLeftApplication();
    void onRewardsUnlocked(Map<Object, Object> rewards);
    void onAdLoadFailed();
    void onAdLoadSuccessful(View view);
    void onAdClicked();
}
