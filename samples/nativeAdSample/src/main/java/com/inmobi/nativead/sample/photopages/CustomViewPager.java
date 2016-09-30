/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.inmobi.nativead.sample.photopages;


import com.inmobi.nativead.sample.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;


public class CustomViewPager extends ViewPager {

    static final int ACTION_DISTANCE_AUTO = Integer.MAX_VALUE;
    static final float SCALEDOWN_GRAVITY_TOP = 0.0f;
    static final float SCALEDOWN_GRAVITY_CENTER = 0.5f;
    static final float SCALEDOWN_GRAVITY_BOTTOM = 1.0f;

    private float unselectedAlpha;
    private Camera transformationCamera;
    private int maxRotation = 75;
    private float unselectedScale;
    private float scaleDownGravity = SCALEDOWN_GRAVITY_CENTER;
    private int actionDistance;
    private float unselectedSaturation;

    public CustomViewPager(Context context) {
        super(context);
        this.initialize();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
        this.applyXmlAttributes(attrs);
    }

    private void initialize() {
        this.transformationCamera = new Camera();
        this.setOffscreenPageLimit(5);
        this.setPageMargin(16);
    }

    private void applyXmlAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomViewPager);

        this.actionDistance = a.getInteger(R.styleable.CustomViewPager_actionDistance, ACTION_DISTANCE_AUTO);
        this.scaleDownGravity = a.getFloat(R.styleable.CustomViewPager_scaleDownGravity, 1.0f);
        this.maxRotation = a.getInteger(R.styleable.CustomViewPager_maxRotation, 45);
        this.unselectedAlpha = a.getFloat(R.styleable.CustomViewPager_unselectedAlpha, 0.3f);
        this.unselectedSaturation = a.getFloat(R.styleable.CustomViewPager_unselectedSaturation, 0.0f);
        this.unselectedScale = a.getFloat(R.styleable.CustomViewPager_unselectedScale, 0.75f);

        a.recycle();
    }

    /**
     * Use this to provide a {@link CustomPagerAdapter} to the coverflow. This method will throw an
     * {@link ClassCastException} if the passed adapter does not subclass {@link CustomPagerAdapter}.
     *
     * @param adapter
     */
    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (!(adapter instanceof CustomPagerAdapter)) {
            throw new ClassCastException(CustomViewPager.class.getSimpleName() +
                    " only works in conjunction with a " +
                    CustomPagerAdapter.class.getSimpleName());
        }

        super.setAdapter(adapter);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        // We can cast here because CustomPagerAdapter only creates wrappers.
        CustomViewPagerItemWrapper item = (CustomViewPagerItemWrapper) child;

        // Since Jelly Bean children won't get invalidated automatically,
        // needs to be added for the smooth coverflow animation
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            item.invalidate();
        }

        final int coverFlowWidth = this.getWidth();
        final int coverFlowCenter = coverFlowWidth / 2;
        final int childWidth = item.getWidth();
        final int childHeight = item.getHeight();
        final int childCenter = item.getLeft() + childWidth / 2;

        // Use coverflow width when its defined as automatic.
        final int actionDistance = (this.actionDistance == ACTION_DISTANCE_AUTO) ? (int) ((coverFlowWidth + childWidth) / 2.0f) : this.actionDistance;

        // Calculate the abstract amount for all effects.
        final float effectsAmount = Math.min(1.0f, Math.max(-1.0f, (1.0f / actionDistance) * (childCenter - coverFlowCenter)));

        // Clear previous transformations and set transformation type (matrix + alpha).
        t.clear();
        t.setTransformationType(Transformation.TYPE_BOTH);

        // Alpha
        if (this.unselectedAlpha != 1) {
            final float alphaAmount = (this.unselectedAlpha - 1) * Math.abs(effectsAmount) + 1;
            t.setAlpha(alphaAmount);
        }

        // Saturation
        if (this.unselectedSaturation != 1) {
            // Pass over saturation to the wrapper.
            final float saturationAmount = (this.unselectedSaturation - 1) * Math.abs(effectsAmount) + 1;
            item.setSaturation(saturationAmount);
        }

        final Matrix imageMatrix = t.getMatrix();

        // Apply rotation.
        if (this.maxRotation != 0) {
            final int rotationAngle = (int) (-effectsAmount * this.maxRotation);
            this.transformationCamera.save();
            this.transformationCamera.rotateY(rotationAngle);
            this.transformationCamera.getMatrix(imageMatrix);
            this.transformationCamera.restore();
        }

        // Zoom.
        if (this.unselectedScale != 1) {
            final float zoomAmount = (this.unselectedScale - 1) * Math.abs(effectsAmount) + 1;
            // Calculate the scale anchor (y anchor can be altered)
            final float translateX = childWidth / 2.0f;
            final float translateY = childHeight * this.scaleDownGravity;
            imageMatrix.preTranslate(-translateX, -translateY);
            imageMatrix.postScale(zoomAmount, zoomAmount);
            imageMatrix.postTranslate(translateX, translateY);
        }

        return true;
    }

    public static class LayoutParams extends ViewPager.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
}
