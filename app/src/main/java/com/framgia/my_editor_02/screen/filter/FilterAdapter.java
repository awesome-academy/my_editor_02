package com.framgia.my_editor_02.screen.filter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.ItemFilterBinding;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.ArrayList;
import java.util.List;
import net.alhazmy13.imagefilter.ImageFilter;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ItemHolder> {
    private List<ImageFilter.Filter> mFilters;
    private OnItemRecyclerViewClick<ImageFilter.Filter> mListener;

    public FilterAdapter(OnItemRecyclerViewClick<ImageFilter.Filter> listener) {
        mFilters = new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemFilterBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_filter, viewGroup, false);
        return new ItemHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.bindData(mFilters.get(i));
    }

    @Override
    public int getItemCount() {
        return mFilters != null ? mFilters.size() : 0;
    }

    public void setData(List<ImageFilter.Filter> filters) {
        if (mFilters != null) {
            mFilters.clear();
            mFilters.addAll(filters);
            notifyDataSetChanged();
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private ItemFilterBinding mBinding;
        private ItemFilterViewModel mItemFilterViewModel;

        ItemHolder(ItemFilterBinding binding,
                OnItemRecyclerViewClick<ImageFilter.Filter> listener) {
            super(binding.getRoot());
            mItemFilterViewModel = new ItemFilterViewModel(listener);
            mBinding = binding;
            mBinding.setViewModel(mItemFilterViewModel);
        }

        void bindData(ImageFilter.Filter filter) {
            mItemFilterViewModel.setData(filter);
            mBinding.executePendingBindings();
        }
    }
}
