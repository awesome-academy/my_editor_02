package com.framgia.my_editor_02.screen.collections;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;
import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.Objects;

public class ItemCollectionsViewModel extends BaseObservable {
    private static final String PHOTOS = " Photos";
    private OnItemRecyclerViewClick<Collection> mClickListener;
    private ObservableField<Collection> mCollectionObservableField;

    ItemCollectionsViewModel(OnItemRecyclerViewClick<Collection> itemClickListener) {
        mCollectionObservableField = new ObservableField<>();
        mClickListener = itemClickListener;
    }

    public String getTotalPhoto() {
        return (String.valueOf(
                Objects.requireNonNull(mCollectionObservableField.get()).getTotalPhotos())
                + PHOTOS);
    }

    public Collection getCollection() {
        return mCollectionObservableField.get();
    }

    public void setCollection(Collection collection) {
        mCollectionObservableField.set(collection);
    }

    public String getImageUrl() {
        return Objects.requireNonNull(mCollectionObservableField.get())
                .getConvertPhoto()
                .getUrlImage()
                .getSmall();
    }

    public void onClickItem(View view) {
        if (mClickListener == null || mCollectionObservableField == null) {
            return;
        }
        mClickListener.onItemClick(mCollectionObservableField.get());
    }
}
