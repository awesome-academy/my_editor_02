package com.framgia.my_editor_02.utils.zoom;

public interface OnAnimationChangeListener {
    void onScale(float scaleFactor, float focalX, float focalY);

    float getScale();
}
