package com.framgia.my_editor_02.screen.crop;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.FragmentCropBinding;
import com.framgia.my_editor_02.utils.Constants;

public class CropPhotoFragment extends Fragment implements View.OnClickListener {
    private CropPhotoViewModel mViewModel;
    private FragmentCropBinding mBinding;

    public static CropPhotoFragment newInstance(Bitmap bitmap) {
        CropPhotoFragment cropPhotoFragment = new CropPhotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_PHOTO, bitmap);
        cropPhotoFragment.setArguments(args);
        return cropPhotoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_crop, container, false);
        setUp();
        mBinding.imageViewTick.setOnClickListener(this);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    private void setUp() {
        if (getArguments() != null) {
            Bitmap bitmap = getArguments().getParcelable(Constants.EXTRA_PHOTO);
            mViewModel = new CropPhotoViewModel(bitmap.copy(Bitmap.Config.RGB_565, true));
            mBinding.cropView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewTick:
                Bitmap bitmap = mBinding.cropView.crop();
                mBinding.cropView.setImageBitmap(bitmap);
                break;
        }
    }
}
