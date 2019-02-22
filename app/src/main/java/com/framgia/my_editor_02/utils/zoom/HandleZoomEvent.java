package com.framgia.my_editor_02.utils.zoom;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class HandleZoomEvent implements View.OnTouchListener, OnAnimationChangeListener,
        GestureDetector.OnDoubleTapListener {
    private static final float MIN_SCALE_VALUE = 1.0f;
    private static final float MAX_SCALE_VALUE = 3.0f;
    private static final float DEFAULT_SCALE_FACTOR = 1.0f;
    private static final int DEFAULT_ZERO_VALUE = 0;
    private static final int DEFAULT_DIVIDE_VALUE = 2;
    private float[] mMatrixValues = new float[9];
    private Matrix mMatrix = new Matrix();
    private RectF mDisplayRect = new RectF();
    private ImageView mImageView;
    private GestureDetector mGestureDetector;

    public HandleZoomEvent(ImageView imageView) {
        mImageView = imageView;
        mImageView.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(imageView.getContext(),
                new GestureDetector.SimpleOnGestureListener());
        mGestureDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        mImageView.callOnClick();
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent ev) {
        float scale = getScale();
        float x = ev.getX();
        float y = ev.getY();
        if (scale < MAX_SCALE_VALUE) {
            setScale(MAX_SCALE_VALUE, x, y);
        } else {
            setScale(MIN_SCALE_VALUE, x, y);
        }
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onScale(float scaleFactor, float focusX, float focusY) {
        if (getScale() < MAX_SCALE_VALUE || scaleFactor < DEFAULT_SCALE_FACTOR) {
            mMatrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            checkAndDisplayMatrix();
        }
    }

    @Override
    public float getScale() {
        mMatrix.getValues(mMatrixValues);
        return mMatrixValues[Matrix.MSCALE_X];
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        mImageView.setScaleType(ImageView.ScaleType.MATRIX);
        mGestureDetector.onTouchEvent(ev);
        return true;
    }

    private void setScale(float scale, float focalX, float focalY) {
        mImageView.post(new ZoomAnimation(mImageView, getScale(), scale, focalX, focalY, this));
    }

    private void checkAndDisplayMatrix() {
        if (checkMatrixBounds()) {
            mImageView.setImageMatrix(mMatrix);
        }
    }

    private boolean checkMatrixBounds() {
        RectF rect = getDisplayRect();
        if (rect == null) {
            return false;
        }
        float rectHeight = rect.height(), rectWidth = rect.width();
        float deltaX = DEFAULT_ZERO_VALUE, deltaY = DEFAULT_ZERO_VALUE;
        int viewHeight = mImageView.getHeight();
        if (rectHeight <= viewHeight) {
            deltaY = (viewHeight - rectHeight) / DEFAULT_DIVIDE_VALUE - rect.top;
        } else if (rect.top > DEFAULT_ZERO_VALUE) {
            deltaY = -rect.top;
        } else if (rect.bottom < viewHeight) {
            deltaY = viewHeight - rect.bottom;
        }
        int viewWidth = mImageView.getWidth();
        if (rectWidth <= viewWidth) {
            deltaX = (viewWidth - rectWidth) / DEFAULT_DIVIDE_VALUE - rect.left;
        } else if (rect.left > DEFAULT_ZERO_VALUE) {
            deltaX = -rect.left;
        } else if (rect.right < viewWidth) {
            deltaX = viewWidth - rect.right;
        }
        mMatrix.postTranslate(deltaX, deltaY);
        return true;
    }

    private RectF getDisplayRect() {
        Drawable d = mImageView.getDrawable();
        if (d != null) {
            mDisplayRect.set(DEFAULT_ZERO_VALUE, DEFAULT_ZERO_VALUE, d.getIntrinsicWidth(),
                    d.getIntrinsicHeight());
            mMatrix.mapRect(mDisplayRect);
            return mDisplayRect;
        }
        return null;
    }
}
