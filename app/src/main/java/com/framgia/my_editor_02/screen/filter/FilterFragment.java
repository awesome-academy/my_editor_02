package com.framgia.my_editor_02.screen.filter;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.FragmentFilterBinding;
import com.framgia.my_editor_02.screen.edit.EditPhotoFragment;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.OnAttachEditFeatureListener;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import net.alhazmy13.imagefilter.ImageFilter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment
        implements View.OnClickListener, OnItemRecyclerViewClick<ImageFilter.Filter> {
    public static final String TAG = FilterFragment.class.getSimpleName();
    private FilterViewModel mFilterViewModel;
    private FragmentFilterBinding mBinding;
    private Navigator mNavigator;
    private OnAttachEditFeatureListener mListener;
    private Bitmap mBitmap;

    public static FilterFragment newInstance(Bitmap bitmap) {
        FilterFragment filterFragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_PHOTO, bitmap);
        filterFragment.setArguments(args);
        return filterFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        setUp();
        mBinding.setViewModel(mFilterViewModel);
        mBinding.imageViewCancel.setOnClickListener(this);
        mBinding.imageViewSave.setOnClickListener(this);
        return mBinding.getRoot();
    }

    private void setUp() {
        mNavigator = new Navigator(this);
        if (getArguments() != null) {
            Bitmap bitmap = getArguments().getParcelable(Constants.EXTRA_PHOTO);
            mBitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
            mFilterViewModel = new FilterViewModel(bitmap, this);
            mFilterViewModel.initData();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewSave:
                if (mListener != null) {
                    mListener.saveEditResult(
                            ((BitmapDrawable) mBinding.imageViewFilter.getDrawable()).getBitmap());
                }
            case R.id.imageViewCancel:
                mNavigator.removeFragment(getFragmentManager(), EditPhotoFragment.TAG);
                break;
        }
    }

    public void setOnAttachFilterFragment(OnAttachEditFeatureListener listener) {
        mListener = listener;
    }

    @Override
    public void onItemClick(ImageFilter.Filter filter) {
        Bitmap bitmap = ImageFilter.applyFilter(mBitmap.copy(Bitmap.Config.RGB_565, true), filter);
        mBinding.imageViewFilter.setImageBitmap(bitmap);
    }
}
