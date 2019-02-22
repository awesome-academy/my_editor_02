package com.framgia.my_editor_02.utils.zoom;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class DragScaleDetector implements ScaleGestureDetector.OnScaleGestureListener {
    private static final int FIRST_POINTER_INDEX = 0;
    private static final int SECOND_POINTER_INDEX = 1;
    private int mActivePointerId;
    private int mActivePointerIndex = 0;
    private float mLastTouchX;
    private float mLastTouchY;
    private ScaleGestureDetector mScaleGestureDetector;
    private OnAnimationChangeListener mOnAnimationChangeListener;

    DragScaleDetector(Context context, OnAnimationChangeListener listener) {
        mOnAnimationChangeListener = listener;
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mOnAnimationChangeListener.onScale(detector.getScaleFactor(), detector.getFocusX(),
                detector.getFocusY());
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    boolean onTouchEvent(MotionEvent ev) {
        mScaleGestureDetector.onTouchEvent(ev);
        return processTouchEvent(ev);
    }

    private boolean processTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(FIRST_POINTER_INDEX);
                mLastTouchX = getActiveX(ev);
                mLastTouchY = getActiveY(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float x = getActiveX(ev);
                float y = getActiveY(ev);
                float dx = x - mLastTouchX, dy = y - mLastTouchY;
                mOnAnimationChangeListener.onDrag(dx, dy);
                mLastTouchX = x;
                mLastTouchY = y;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointerIndex = ev.getActionIndex();
                int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    int newPointerIndex = pointerIndex == FIRST_POINTER_INDEX ? SECOND_POINTER_INDEX
                            : FIRST_POINTER_INDEX;
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                }
                break;
        }
        mActivePointerIndex = ev.findPointerIndex(mActivePointerId);
        return true;
    }

    private float getActiveX(MotionEvent ev) {
        if (mActivePointerIndex < ev.getPointerCount()) {
            return ev.getX(mActivePointerIndex);
        }
        return ev.getX();
    }

    private float getActiveY(MotionEvent ev) {
        if (mActivePointerIndex < ev.getPointerCount()) {
            return ev.getY(mActivePointerIndex);
        }
        return ev.getY();
    }
}
