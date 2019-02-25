package com.framgia.my_editor_02.screen.brightness;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.FragmentBrightnessBinding;
import com.framgia.my_editor_02.screen.edit.EditPhotoFragment;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.OnAttachEditFeatureListener;
import com.framgia.my_editor_02.utils.custom.BrightnessImageView;

public class BrightnessFragment extends Fragment
        implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    public static final float BRIGHTNESS = 125f;
    public static final float CONTRAST = 170f;
    private FragmentBrightnessBinding mBinding;
    private BrightnessViewModel mBrightnessViewModel;
    private Bitmap mBitmap;
    private OnAttachEditFeatureListener mListener;
    private Navigator mNavigator;

    public static BrightnessFragment newInstance(Bitmap bitmap) {
        BrightnessFragment brightnessFragment = new BrightnessFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_PHOTO, bitmap);
        brightnessFragment.setArguments(args);
        return brightnessFragment;
    }

    public void setOnAttachBrightnessListener(OnAttachEditFeatureListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_brightness, container, false);
        setUp();
        mBinding.setViewModel(mBrightnessViewModel);
        mBinding.seekBarBrightness.setOnSeekBarChangeListener(this);
        mBinding.seekBarContrast.setOnSeekBarChangeListener(this);
        mBinding.imageViewTick.setOnClickListener(this);
        mBinding.imageViewCancel.setOnClickListener(this);
        return mBinding.getRoot();
    }

    private void setUp() {
        mNavigator = new Navigator(this);
        if (getArguments() != null) {
            mBitmap = getArguments().getParcelable(Constants.EXTRA_PHOTO);
            mBrightnessViewModel =
                    new BrightnessViewModel(mBitmap.copy(Bitmap.Config.ARGB_8888, true));
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float brightness = mBinding.seekBarBrightness.getProgress() - BRIGHTNESS;
        float contrast = mBinding.seekBarContrast.getProgress() / CONTRAST;
        if (mBitmap != null) {
            mBinding.imageViewPoster.setImageBitmap(
                    BrightnessImageView.changeBitmapContrastBrightness(mBitmap, contrast,
                            brightness));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewTick:
                if (mListener != null) {
                    mListener.saveEditResult(
                            ((BitmapDrawable) mBinding.imageViewPoster.getDrawable()).getBitmap());
                }
            case R.id.imageViewCancel:
                mNavigator.removeFragment(getFragmentManager(), EditPhotoFragment.TAG);
                break;
        }
    }
}
