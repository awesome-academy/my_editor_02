package com.framgia.my_editor_02.utils;

public interface OnItemRecyclerViewClick<T> {
    void onItemClick(T item);

    interface OnSearchHistoryItemClick {
        void onHistoryItemClicked(String searchQuery);

        void onRemoveHistoryItemClick(int position);
    }
}
