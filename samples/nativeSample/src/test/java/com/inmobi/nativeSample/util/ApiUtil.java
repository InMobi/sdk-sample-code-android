/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inmobi.nativeSample.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.inmobi.ads.InMobiNative;
import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.common.viewmodel.Status;

public class ApiUtil {
    public static LiveData<Response> successCall(InMobiNative data, int position) {
        return createCall(Response.success(data, Status.SUCCESS, position));
    }

    public static <T> LiveData<Response> createCall(Response response) {
        MutableLiveData<Response> data = new MutableLiveData<>();
        data.setValue(response);
        return data;
    }
}
