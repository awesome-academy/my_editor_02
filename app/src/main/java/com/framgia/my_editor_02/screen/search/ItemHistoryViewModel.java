package com.framgia.my_editor_02.screen.search;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.view.View;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;

public class ItemHistoryViewModel extends BaseObservable {
    public ObservableField<String> mSearchQuery = new ObservableField<>();
    private OnItemRecyclerViewClick.OnSearchHistoryItemClick mListener;
    private int mPosition;

    public ItemHistoryViewModel(OnItemRecyclerViewClick.OnSearchHistoryItemClick listener) {
        mListener = listener;
    }

    public void setSearchQuery(String searchQuery, int position) {
        mSearchQuery.set(searchQuery);
        mPosition = position;
    }

    public void onItemClicked(View view) {
        if (mListener == null || mSearchQuery.get() == null) {
            return;
        }
        mListener.onHistoryItemClicked(mSearchQuery.get());
    }

    public void onRemoveItemClicked(View view) {
        if (mListener != null) {
            mListener.onRemoveHistoryItemClick(mPosition);
        }
    }
}
