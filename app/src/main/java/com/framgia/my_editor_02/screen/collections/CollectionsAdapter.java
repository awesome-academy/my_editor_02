package com.framgia.my_editor_02.screen.collections;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.databinding.ItemCollectionsBinding;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.ArrayList;
import java.util.List;

public class CollectionsAdapter
        extends RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder> {
    private List<Collection> mCollections;
    private OnItemRecyclerViewClick<Collection> mClickListener;

    CollectionsAdapter(OnItemRecyclerViewClick<Collection> clickListener) {
        mCollections = new ArrayList<>();
        mClickListener = clickListener;
    }

    void updateData(List<Collection> collections) {
        if (mCollections != null) {
            mCollections.addAll(collections);
            notifyItemRangeInserted(mCollections.size() - collections.size(), mCollections.size());
        }
    }

    @NonNull
    @Override
    public CollectionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemCollectionsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_collections, viewGroup, false);
        return new CollectionsViewHolder(binding, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionsViewHolder collectionsViewHolder, int i) {
        collectionsViewHolder.bindData(mCollections.get(i));
    }

    @Override
    public int getItemCount() {
        return mCollections != null ? mCollections.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void resetData() {
        if (mCollections != null) {
            int size = mCollections.size();
            mCollections.clear();
            notifyItemRangeRemoved(mCollections.size(), size);
        }
    }

    public static class CollectionsViewHolder extends RecyclerView.ViewHolder {
        private ItemCollectionsBinding mItemCollectionsBinding;
        private ItemCollectionsViewModel mViewModel;

        public CollectionsViewHolder(ItemCollectionsBinding binding,
                OnItemRecyclerViewClick<Collection> itemClickListener) {
            super(binding.getRoot());
            mItemCollectionsBinding = binding;
            mViewModel = new ItemCollectionsViewModel(itemClickListener);
            mItemCollectionsBinding.setViewModel(mViewModel);
        }

        public void bindData(Collection collection) {
            mItemCollectionsBinding.invalidateAll();
            mViewModel.setCollection(collection);
            mItemCollectionsBinding.executePendingBindings();
        }
    }
}
