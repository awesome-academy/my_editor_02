package com.framgia.my_editor_02.screen.photos;

import android.databinding.ObservableField;
import android.view.View;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.Objects;

public class ItemPhotoViewModel {
    public ObservableField<Photo> mPhotoObservableField = new ObservableField<>();
    private OnItemRecyclerViewClick<Photo> mItemClickListener;

    public ItemPhotoViewModel(OnItemRecyclerViewClick<Photo> listener) {
        mItemClickListener = listener;
    }

    public void setPhoto(Photo photo) {
        mPhotoObservableField.set(photo);
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null || mPhotoObservableField.get() == null) {
            return;
        }
        mItemClickListener.onItemClick(mPhotoObservableField.get());
    }

    public String getImageUrl() {
        return Objects.requireNonNull(mPhotoObservableField.get()).getUrlImage().getSmall();
    }
}
