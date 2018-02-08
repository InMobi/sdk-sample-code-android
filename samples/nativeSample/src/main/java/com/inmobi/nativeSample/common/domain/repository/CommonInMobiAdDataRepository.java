package com.inmobi.nativeSample.common.domain.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.nativeSample.common.PlacementId;
import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.common.viewmodel.Status;
import com.inmobi.nativeSample.utility.AppExecutors;
import com.inmobi.nativeSample.utility.AppSchedulers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class CommonInMobiAdDataRepository {

    private final AppExecutors appExecutors;
    private final AppSchedulers appSchedulers;
    protected Context appContext;
    private Map<Integer, InMobiNative> nativeStrands = new HashMap<>();
    private Map<Integer, ApiLiveData> apiLiveDataMap = new HashMap<>();

    @Inject
    public CommonInMobiAdDataRepository(Context appContext, AppExecutors appExecutors, AppSchedulers appSchedulers) {
        this.appContext = appContext;
        this.appExecutors = appExecutors;
        this.appSchedulers = appSchedulers;
    }

    public void destroy(int position) {
        InMobiNative inMobiNative = nativeStrands.get(position);
        if (null != inMobiNative) {
            inMobiNative.destroy();
        }
        ApiLiveData apiLiveData = apiLiveDataMap.get(position);
        if (null != apiLiveData) {
            apiLiveData.onInactive();
        }
    }

    public void destroy() {
        for (Integer position :
                nativeStrands.keySet()) {
            destroy(position);
        }
        nativeStrands.clear();
        apiLiveDataMap.clear();
    }

    public class ApiLiveData extends LiveData<Response> {
        Disposable disposable;

        @SuppressWarnings("unchecked")
        public void getData(Flowable<Response> flowableData) {
            disposable = flowableData
                    .observeOn(appSchedulers.mainThread())
                    .subscribeOn(appSchedulers.networkIO())
                    .subscribe(this::postValue,
                            throwable -> postValue(Response.error(throwable))
                    );
        }

        @Override
        protected void onInactive() {
            if (disposable != null)
                disposable.dispose();
        }
    }

    public static class InMobiError extends Throwable {
        final InMobiNative inMobiNative;
        final InMobiAdRequestStatus inMobiAdRequestStatus;

        public InMobiError(InMobiNative inMobiNative, InMobiAdRequestStatus inMobiAdRequestStatus) {
            this.inMobiNative = inMobiNative;
            this.inMobiAdRequestStatus = inMobiAdRequestStatus;
        }
    }

    public LiveData<Response> getInMobiAdCallbacks(int position) {
        ApiLiveData responseApiLiveData = apiLiveDataMap.get(position);
        if (null == responseApiLiveData) {
            responseApiLiveData = new ApiLiveData();
        }
        destroy(position);
        responseApiLiveData.getData(getAdCallbacks(position));
        apiLiveDataMap.put(position, responseApiLiveData);
        return responseApiLiveData;
    }

    private Flowable<Response> getAdCallbacks(int position) {
        return Flowable.create(emitter -> {
            InMobiNative.NativeAdListener nativeAdListener = new InMobiNative.NativeAdListener() {
                @Override
                public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.SUCCESS, position));
                }

                @Override
                public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                    emitter.onError(new InMobiError(inMobiNative, inMobiAdRequestStatus));
                }

                @Override
                public void onAdFullScreenDismissed(InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.AD_DISMISSED, position));
                }

                @Override
                public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.AD_WILL_DISPLAY, position));
                }

                @Override
                public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.AD_DISPLAYED, position));
                }

                @Override
                public void onUserWillLeaveApplication(InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.AD_USER_LEFT, position));
                }

                @Override
                public void onAdImpressed(@NonNull InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.AD_IMPRESSED, position));
                }

                @Override
                public void onAdClicked(@NonNull InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.AD_CLICKED, position));
                }

                @Override
                public void onMediaPlaybackComplete(@NonNull InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.AD_MEDIA_PLAYED, position));
                }

                @Override
                public void onAdStatusChanged(@NonNull InMobiNative inMobiNative) {
                    emitter.onNext(Response.success(inMobiNative, Status.STATUS_CHANGED, position));
                }
            };
            InMobiNative inMobiNative = new InMobiNative(appContext,
                    PlacementId.YOUR_PLACEMENT_ID_HERE, nativeAdListener);
            appExecutors.mainThread().execute(() -> inMobiNative.load());
            this.nativeStrands.put(position, inMobiNative);
        }, BackpressureStrategy.BUFFER);
    }

}
