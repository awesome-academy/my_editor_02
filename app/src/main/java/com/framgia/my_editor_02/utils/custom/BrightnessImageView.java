package com.framgia.my_editor_02.utils.custom;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class BrightnessImageView {
    public static final int DEFAULT_VALUE = 0;
    public static final int ONE_VALUE = 1;

    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast,
            float brightness) {
        ColorMatrix cm = new ColorMatrix(new float[] {
                contrast, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, brightness, DEFAULT_VALUE,
                contrast, DEFAULT_VALUE, DEFAULT_VALUE, brightness, DEFAULT_VALUE, DEFAULT_VALUE,
                contrast, DEFAULT_VALUE, brightness, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, ONE_VALUE,
                DEFAULT_VALUE
        });
        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        Canvas canvas = new Canvas(ret);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, DEFAULT_VALUE, DEFAULT_VALUE, paint);
        return ret;
    }
}
