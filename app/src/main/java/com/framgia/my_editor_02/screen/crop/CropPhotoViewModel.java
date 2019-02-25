package com.framgia.my_editor_02.screen.crop;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import com.framgia.my_editor_02.screen.BaseViewModel;

public class CropPhotoViewModel extends BaseViewModel {
    public ObservableField<Bitmap> mBitmapObservableField = new ObservableField<>();

    public CropPhotoViewModel(Bitmap bitmap) {
        mBitmapObservableField.set(bitmap);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }
}
