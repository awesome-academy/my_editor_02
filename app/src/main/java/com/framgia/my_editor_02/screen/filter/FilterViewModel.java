package com.framgia.my_editor_02.screen.filter;

import android.databinding.ObservableField;
import android.graphics.Bitmap;
import com.framgia.my_editor_02.screen.BaseViewModel;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.Arrays;
import java.util.List;
import net.alhazmy13.imagefilter.ImageFilter;

public class FilterViewModel extends BaseViewModel {
    public ObservableField<Bitmap> mBitmapObservableField = new ObservableField<>();
    private FilterAdapter mFilterAdapter;

    FilterViewModel(Bitmap bitmap, OnItemRecyclerViewClick<ImageFilter.Filter> listener) {
        mBitmapObservableField.set(bitmap);
        mFilterAdapter = new FilterAdapter(listener);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
    }

    void initData() {
        ImageFilter.Filter[] filters = new ImageFilter.Filter[] {
                ImageFilter.Filter.GRAY, ImageFilter.Filter.NEON, ImageFilter.Filter.PIXELATE,
                ImageFilter.Filter.TV, ImageFilter.Filter.BLOCK, ImageFilter.Filter.OLD,
                ImageFilter.Filter.SHARPEN, ImageFilter.Filter.LIGHT, ImageFilter.Filter.LOMO,
                ImageFilter.Filter.SKETCH, ImageFilter.Filter.GOTHAM
        };
        List<ImageFilter.Filter> filterList = Arrays.asList(filters);
        mFilterAdapter.setData(filterList);
    }

    public FilterAdapter getFilterAdapter() {
        return mFilterAdapter;
    }
}
