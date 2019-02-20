package com.framgia.my_editor_02.screen.photos;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.databinding.ItemTabPhotosBinding;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ItemHolder> {
    private List<Photo> mPhotos;
    private OnItemRecyclerViewClick<Photo> mOnItemClickListener;

    public PhotoAdapter(OnItemRecyclerViewClick<Photo> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mPhotos = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTabPhotosBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_tab_photos, viewGroup, false);
        return new ItemHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.bindData(mPhotos.get(i));
    }

    @Override
    public int getItemCount() {
        return mPhotos != null ? mPhotos.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setData(List<Photo> photos) {
        if (mPhotos != null) {
            mPhotos.addAll(photos);
            notifyItemRangeInserted(mPhotos.size() - photos.size(), mPhotos.size());
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private ItemTabPhotosBinding mBinding;
        private ItemPhotoViewModel mItemPhotoViewModel;

        ItemHolder(ItemTabPhotosBinding binding, OnItemRecyclerViewClick<Photo> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemPhotoViewModel = new ItemPhotoViewModel(listener);
            mBinding.setViewModel(mItemPhotoViewModel);
        }

        void bindData(Photo photo) {
            mItemPhotoViewModel.setPhoto(photo);
            mBinding.executePendingBindings();
        }
    }
}
