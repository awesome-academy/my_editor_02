package com.framgia.my_editor_02.screen.search;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.ItemSearchHistoryBinding;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ItemHolder> {
    private List<String> mSearchQueries;
    private OnItemRecyclerViewClick.OnSearchHistoryItemClick mListener;

    public HistoryAdapter(OnItemRecyclerViewClick.OnSearchHistoryItemClick listener) {
        mSearchQueries = new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSearchHistoryBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_search_history, viewGroup, false);
        return new ItemHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        itemHolder.bindData(mSearchQueries.get(i));
    }

    @Override
    public int getItemCount() {
        return mSearchQueries != null ? mSearchQueries.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setData(List<String> mQueries) {
        if (mSearchQueries != null) {
            mSearchQueries.clear();
            mSearchQueries.addAll(mQueries);
            notifyDataSetChanged();
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private ItemSearchHistoryBinding mBinding;
        private ItemHistoryViewModel mItemHistoryViewModel;

        ItemHolder(ItemSearchHistoryBinding binding,
                OnItemRecyclerViewClick.OnSearchHistoryItemClick listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemHistoryViewModel = new ItemHistoryViewModel(listener);
            mBinding.setViewModel(mItemHistoryViewModel);
        }

        void bindData(String searchQuery) {
            mItemHistoryViewModel.setSearchQuery(searchQuery, getAdapterPosition());
            mBinding.executePendingBindings();
        }
    }
}
