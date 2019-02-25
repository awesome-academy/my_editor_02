package com.framgia.my_editor_02.screen.draw;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.FragmentDrawBinding;
import com.framgia.my_editor_02.screen.edit.EditPhotoFragment;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.OnAttachEditFeatureListener;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawFragment extends Fragment
        implements RadioGroup.OnCheckedChangeListener, View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {
    private static final float SEEK_BAR_RATIO = 5f;
    private FragmentDrawBinding mBinding;
    private DrawViewModel mDrawViewModel;
    private Navigator mNavigator;
    private OnAttachEditFeatureListener mListener;

    public static DrawFragment newInstance(Bitmap bitmap) {
        DrawFragment drawFragment = new DrawFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_PHOTO, bitmap);
        drawFragment.setArguments(args);
        return drawFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_draw, container, false);
        setUp();
        mBinding.setViewModel(mDrawViewModel);
        mBinding.radioGroupDraw.setOnCheckedChangeListener(this);
        mBinding.imageViewCancel.setOnClickListener(this);
        mBinding.imageViewSave.setOnClickListener(this);
        mBinding.seekBarDraw.setOnSeekBarChangeListener(this);
        return mBinding.getRoot();
    }

    private void setUp() {
        mNavigator = new Navigator(this);
        if (getArguments() != null) {
            Bitmap bitmap = getArguments().getParcelable(Constants.EXTRA_PHOTO);
            mDrawViewModel = new DrawViewModel(bitmap.copy(Bitmap.Config.RGB_565, true));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewSave:
                if (mListener != null) {
                    mListener.saveEditResult(mBinding.imageViewPhotoDraw.getBitmap());
                }
            case R.id.imageViewCancel:
                mNavigator.removeFragment(getFragmentManager(), EditPhotoFragment.TAG);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonBlack:
                setPaintColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),
                        android.R.color.black));
                break;
            case R.id.radioButtonWhite:
                setPaintColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),
                        android.R.color.white));
                break;
            case R.id.radioButtonBlue:
                setPaintColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),
                        R.color.colorBlueRadioButton));
                break;
            case R.id.radioButtonGreen:
                setPaintColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),
                        R.color.colorGreenRadioButton));
                break;
            case R.id.radioButtonYellow:
                setPaintColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),
                        R.color.colorYellowRadioButton));
                break;
            case R.id.radioButtonRed:
                setPaintColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),
                        R.color.colorRedRadioButton));
                break;
        }
    }

    public void setOnAttachDrawFragmentListener(OnAttachEditFeatureListener listener) {
        mListener = listener;
    }

    private void setPaintColor(int color) {
        mBinding.imageViewPhotoDraw.setPaintColor(color);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mBinding.imageViewPhotoDraw.setStrokeWidth(seekBar.getProgress() / SEEK_BAR_RATIO);
    }
}
