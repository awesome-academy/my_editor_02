package com.framgia.my_editor_02.screen.collectionsDetail;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.Objects;

public class ItemCollectionDetailViewModel extends BaseObservable {
    private ObservableField<Photo> mPhotoObservableField;
    private OnItemRecyclerViewClick<Photo> mItemRecyclerViewClick;

    ItemCollectionDetailViewModel(OnItemRecyclerViewClick<Photo> itemRecyclerViewClick) {
        mItemRecyclerViewClick = itemRecyclerViewClick;
        mPhotoObservableField = new ObservableField<>();
    }

    public String getImage(){
        return Objects.requireNonNull(mPhotoObservableField.get()).getUrlImage().getSmall();
    }

    public void setCollectionDetail(Photo photo){
        mPhotoObservableField.set(photo);
    }

    public void getItemClick(View view){
        if (mItemRecyclerViewClick == null || mPhotoObservableField == null){
            return;
        }
        mItemRecyclerViewClick.onItemClick(mPhotoObservableField.get());
    }

}
