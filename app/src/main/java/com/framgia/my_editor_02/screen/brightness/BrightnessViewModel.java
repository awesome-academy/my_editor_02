package com.framgia.my_editor_02.screen.brightness;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import com.framgia.my_editor_02.screen.BaseViewModel;

public class BrightnessViewModel extends BaseViewModel {
    private ObservableField<Bitmap> mBitmapObservableField = new ObservableField<>();

    BrightnessViewModel(Bitmap bitmap) {
        mBitmapObservableField.set(bitmap);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }

    public ObservableField<Bitmap> getBitmapObservableField() {
        return mBitmapObservableField;
    }
}
