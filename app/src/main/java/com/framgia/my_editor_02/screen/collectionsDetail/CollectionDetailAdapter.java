package com.framgia.my_editor_02.screen.collectionsDetail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.databinding.ItemDetailCollectionsBinding;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.ArrayList;
import java.util.List;

public class CollectionDetailAdapter
        extends RecyclerView.Adapter<CollectionDetailAdapter.CollectionDetailViewHolder> {
    private List<Photo> mPhotos;
    private OnItemRecyclerViewClick<Photo> mOnItemRecyclerViewClick;

    CollectionDetailAdapter(OnItemRecyclerViewClick<Photo> onItemRecyclerViewClick) {
        mPhotos = new ArrayList<>();
        mOnItemRecyclerViewClick = onItemRecyclerViewClick;
    }

    void updateData(List<Photo> photos) {
        if (mPhotos != null) {
            mPhotos.addAll(photos);
            notifyItemRangeChanged(mPhotos.size() - photos.size(), mPhotos.size());
        }
    }

    @NonNull
    @Override
    public CollectionDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemDetailCollectionsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_detail_collections, viewGroup,
                        false);
        return new CollectionDetailViewHolder(binding, mOnItemRecyclerViewClick);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionDetailViewHolder collectionDetailViewHolder,
            int i) {
        collectionDetailViewHolder.bindData(mPhotos.get(i));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mPhotos != null ? mPhotos.size() : 0;
    }

    static class CollectionDetailViewHolder extends RecyclerView.ViewHolder {
        private ItemCollectionDetailViewModel mViewModel;
        private ItemDetailCollectionsBinding mBinding;

        CollectionDetailViewHolder(ItemDetailCollectionsBinding binding,
                OnItemRecyclerViewClick<Photo> itemRecyclerViewClick) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = new ItemCollectionDetailViewModel(itemRecyclerViewClick);
            mBinding.setViewModel(mViewModel);
        }

        void bindData(Photo photo) {
            mViewModel.setCollectionDetail(photo);
            mBinding.executePendingBindings();
        }
    }
}
