package com.framgia.my_editor_02.screen.draw;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import com.framgia.my_editor_02.screen.BaseViewModel;

public class DrawViewModel extends BaseViewModel {
    public ObservableField<Bitmap> mBitmapObservableField = new ObservableField<>();

    DrawViewModel(Bitmap bitmap) {
        mBitmapObservableField.set(bitmap);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }
}
