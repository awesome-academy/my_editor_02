package com.framgia.my_editor_02.screen.edit;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.databinding.FragmentEditPhotoBinding;
import com.framgia.my_editor_02.screen.draw.DrawFragment;
import com.framgia.my_editor_02.screen.filter.FilterFragment;
import com.framgia.my_editor_02.screen.photoDetail.PhotoDetailFragment;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.zoom.HandleZoomEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPhotoFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = EditPhotoFragment.class.getSimpleName();
    private EditPhotoViewModel mEditPhotoViewModel;
    private Navigator mNavigator;
    private FragmentEditPhotoBinding mBinding;

    public static EditPhotoFragment newInstance(Photo photo) {
        EditPhotoFragment editPhotoFragment = new EditPhotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_PHOTO, photo);
        editPhotoFragment.setArguments(args);
        return editPhotoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_edit_photo, container, false);
        setUp();
        mBinding.setViewModel(mEditPhotoViewModel);
        new HandleZoomEvent(mBinding.imageViewPhoto);
        mBinding.textViewDraw.setOnClickListener(this);
        mBinding.imageViewBack.setOnClickListener(this);
        mBinding.textViewFilter.setOnClickListener(this);
        return mBinding.getRoot();
    }

    private void setUp() {
        mNavigator = new Navigator(this);
        if (getArguments() != null) {
            Photo photo = getArguments().getParcelable(Constants.EXTRA_PHOTO);
            mEditPhotoViewModel = new EditPhotoViewModel(photo);
        }
    }

    @Override
    public void onClick(View view) {
        Drawable drawable = mBinding.imageViewPhoto.getDrawable();
        switch (view.getId()) {
            case R.id.textViewDraw:
                if (drawable != null) {
                    mNavigator.goNextChildFragment(getFragmentManager(), R.id.layoutContainer,
                            DrawFragment.newInstance(((BitmapDrawable) drawable).getBitmap()), true,
                            TAG);
                }
                break;
            case R.id.textViewFilter:
                if (drawable != null) {
                    mNavigator.goNextChildFragment(getFragmentManager(), R.id.layoutContainer,
                            FilterFragment.newInstance(((BitmapDrawable) drawable).getBitmap()),
                            true, TAG);
                }
                break;
            case R.id.imageViewBack:
                mNavigator.removeFragment(getFragmentManager(), PhotoDetailFragment.TAG);
                break;
        }
    }

    public void saveBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mBinding.imageViewPhoto.setImageBitmap(bitmap);
        }
    }
}
