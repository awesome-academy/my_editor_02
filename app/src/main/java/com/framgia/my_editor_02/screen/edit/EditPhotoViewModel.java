package com.framgia.my_editor_02.screen.edit;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.screen.BaseViewModel;

public class EditPhotoViewModel extends BaseViewModel {
    public ObservableBoolean mIsShowEditAction = new ObservableBoolean();
    public ObservableField<Photo> mPhotoObservableField = new ObservableField<>();

    public EditPhotoViewModel(Photo photo) {
        mPhotoObservableField.set(photo);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }

    public void onImageClicked(View view) {
        mIsShowEditAction.set(!mIsShowEditAction.get());
    }
}
