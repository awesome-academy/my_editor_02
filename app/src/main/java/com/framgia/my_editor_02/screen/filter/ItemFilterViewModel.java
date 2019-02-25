package com.framgia.my_editor_02.screen.filter;

import android.databinding.ObservableField;
import android.view.View;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import net.alhazmy13.imagefilter.ImageFilter;

public class ItemFilterViewModel {
    public ObservableField<ImageFilter.Filter> mFilterObservableField = new ObservableField<>();
    private OnItemRecyclerViewClick<ImageFilter.Filter> mItemClickListener;

    public ItemFilterViewModel(OnItemRecyclerViewClick<ImageFilter.Filter> listener) {
        mItemClickListener = listener;
    }

    public void setData(ImageFilter.Filter filter) {
        mFilterObservableField.set(filter);
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null || mFilterObservableField.get() == null) {
            return;
        }
        mItemClickListener.onItemClick(mFilterObservableField.get());
    }
}
