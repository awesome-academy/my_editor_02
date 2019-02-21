package com.framgia.my_editor_02.utils.zoom;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

public class ZoomAnimation implements Runnable {
    private static final int ZOOM_DURATION = 200;
    private static final float FLOAT_DEFAULT_VALUE = 1.0f;
    private Interpolator mInterpolator;
    private float mFocalX, mFocalY;
    private long mStartTime;
    private float mZoomStart, mZoomEnd;
    private OnAnimationChangeListener mChangeListener;
    private View mView;

    public ZoomAnimation(View view, float currentZoom, float targetZoom, float focalX, float focalY,
            OnAnimationChangeListener listener) {
        mView = view;
        mFocalX = focalX;
        mFocalY = focalY;
        mStartTime = System.currentTimeMillis();
        mZoomStart = currentZoom;
        mZoomEnd = targetZoom;
        mInterpolator = new AccelerateDecelerateInterpolator();
        mChangeListener = listener;
    }

    @Override
    public void run() {
        float animationRatio = interpolate();
        float scale = mZoomStart + animationRatio * (mZoomEnd - mZoomStart);
        float deltaScale = scale / mChangeListener.getScale();
        mChangeListener.onScale(deltaScale, mFocalX, mFocalY);
        if (animationRatio < FLOAT_DEFAULT_VALUE) {
            mView.postOnAnimation(this);
        }
    }

    private float interpolate() {
        float timeRatio =
                FLOAT_DEFAULT_VALUE * (System.currentTimeMillis() - mStartTime) / ZOOM_DURATION;
        timeRatio = Math.min(FLOAT_DEFAULT_VALUE, timeRatio);
        return mInterpolator.getInterpolation(timeRatio);
    }
}
