package com.framgia.my_editor_02.utils.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DrawImageView extends android.support.v7.widget.AppCompatImageView {
    private static final float STROKE_WIDTH_DEFAULT = 4f;
    private static final float VALUE_ONE = 1f;
    private static final float VALUE_TWO = 2f;
    private Paint mPaint;
    private Path mPath;
    private float mCurrentX, mStartX;
    private float mCurrentY, mStartY;
    private RectF mRectF = new RectF();
    private boolean mIsOutRange = true;
    private float mCurrentStrokeWidth = STROKE_WIDTH_DEFAULT;
    private int mCurrentColor = Color.WHITE;

    public DrawImageView(Context context) {
        super(context);
        init();
    }

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(mCurrentColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(mCurrentStrokeWidth);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (!checkBoundView(x, y)) {
            mIsOutRange = true;
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = x;
                mStartY = y;
                actionDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsOutRange) {
                    actionDown(x, y);
                    mIsOutRange = false;
                } else {
                    actionMove(x, y);
                }
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
        }
        invalidate();
        return true;
    }

    private void actionDown(float x, float y) {
        mPath.moveTo(x, y);
        mCurrentX = x;
        mCurrentY = y;
    }

    private void actionMove(float x, float y) {
        mPath.quadTo(mCurrentX, mCurrentY, (x + mCurrentX) / VALUE_TWO,
                (y + mCurrentY) / VALUE_TWO);
        mCurrentX = x;
        mCurrentY = y;
    }

    private void actionUp() {
        mPath.lineTo(mCurrentX, mCurrentY);
        if (mStartX == mCurrentX && mStartY == mCurrentY) {
            mPath.lineTo(mCurrentX, mCurrentY + VALUE_TWO);
            mPath.lineTo(mCurrentX + VALUE_ONE, mCurrentY + VALUE_TWO);
            mPath.lineTo(mCurrentX + VALUE_ONE, mCurrentY);
        }
    }

    public Bitmap getBitmap() {
        if (getDrawable() != null) {
            Bitmap b = ((BitmapDrawable) getDrawable()).getBitmap();
            Canvas c = new Canvas(b);
            c.drawPath(mPath, mPaint);
            return b;
        }
        return null;
    }

    public void setPaintColor(int color) {
        mCurrentColor = color;
        savePaint();
        init();
        mPaint.setColor(color);
    }

    public void setStrokeWidth(float width) {
        mCurrentStrokeWidth = width;
        savePaint();
        init();
        mPaint.setStrokeWidth(width);
    }

    private boolean checkBoundView(float x, float y) {
        if (getDrawable() != null) {
            Drawable drawable = getDrawable();
            float top = VALUE_ONE * (getHeight() - drawable.getIntrinsicHeight()) / VALUE_TWO;
            float left = VALUE_ONE * (getWidth() - drawable.getIntrinsicWidth()) / VALUE_TWO;
            mRectF.set(left, top, drawable.getIntrinsicWidth() + left,
                    drawable.getIntrinsicHeight() + top);
            return mRectF.contains(x, y);
        }
        return false;
    }

    private void savePaint() {
        if (getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
            Canvas canvas = new Canvas(bitmap);
            canvas.drawPath(mPath, mPaint);
            setImageBitmap(bitmap);
        }
    }
}
