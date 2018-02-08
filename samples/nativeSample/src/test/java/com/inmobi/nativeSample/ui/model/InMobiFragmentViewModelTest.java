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

package com.inmobi.nativeSample.ui.model;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.test.mock.MockContext;

import com.inmobi.nativeSample.common.domain.interactors.LoadFragmentDataUseCase;
import com.inmobi.nativeSample.common.domain.repository.CommonInMobiAdDataRepository;
import com.inmobi.nativeSample.common.viewmodel.Response;
import com.inmobi.nativeSample.util.InstantAppExecutors;
import com.inmobi.nativeSample.util.InstantAppSchedulers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(JUnit4.class)
public class InMobiFragmentViewModelTest {

    public static final int POSITION = 0;
    @InjectMocks
    private LoadFragmentDataUseCase adDataRepository;
    private InMobiFragmentViewModel adViewModel;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        adDataRepository = mock(LoadFragmentDataUseCase.class);
        MockContext mockContext = new MockContext();
        adViewModel = new InMobiFragmentViewModel(adDataRepository);
    }


    @Test
    public void testNull() {
        assertThat(adViewModel.loadAd(), notNullValue());
        verify(adDataRepository, never()).executeAd(POSITION);
    }

    @Test
    public void dontFetchWithoutObservers() {
        adViewModel.setPosition(0);
        verify(adDataRepository, never()).executeAd(anyInt());
    }

    @Test
    public void fetchWhenObserved() {
        ArgumentCaptor<Integer> owner = ArgumentCaptor.forClass(Integer.class);

        adViewModel.setPosition(POSITION);
        adViewModel.loadAd().observeForever(mock(Observer.class));
        verify(adDataRepository, times(1)).executeAd(owner.capture());
        assertThat(owner.getValue(), is(0));
    }

    @Test
    public void changeWhileObserved() {
        ArgumentCaptor<Integer> owner = ArgumentCaptor.forClass(Integer.class);
        adViewModel.loadAd().observeForever(mock(Observer.class));

        adViewModel.setPosition(0);
        adViewModel.setPosition(1);

        verify(adDataRepository, times(2)).executeAd(owner.capture());
        assertThat(owner.getAllValues(), is(Arrays.asList(0, 1)));
    }

    @Test
    public void resetPosition() {
        Observer<Integer> observer = mock(Observer.class);
        adViewModel.postionData.observeForever(observer);
        verifyNoMoreInteractions(observer);
        adViewModel.setPosition(0);
        verify(observer).onChanged(0);
        reset(observer);
        verifyNoMoreInteractions(observer);
        adViewModel.setPosition(1);
        verify(observer).onChanged(1);
    }


    @Test
    public void retry() {
        adViewModel.reloadAd();

        adViewModel.setPosition(POSITION);
        verifyNoMoreInteractions(adDataRepository);

        Observer<Response> observer = mock(Observer.class);
        adViewModel.loadAd().observeForever(observer);
        verify(adDataRepository).executeAd(POSITION);
        reset(adDataRepository);
        adViewModel.reloadAd();
        verify(adDataRepository).executeAd(POSITION);
    }

    @Test
    public void nullRepoId() {
        adViewModel.setPosition(-1);
        Observer<Response> observer1 = mock(Observer.class);
        adViewModel.loadAd().observeForever(observer1);
        verify(observer1).onChanged(null);
    }
}