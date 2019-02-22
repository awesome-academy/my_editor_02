package com.framgia.my_editor_02.screen.edit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.databinding.FragmentEditPhotoBinding;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.zoom.HandleZoomEvent;
import com.framgia.my_editor_02.utils.zoom.ZoomAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPhotoFragment extends Fragment {

    private EditPhotoViewModel mEditPhotoViewModel;

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
        FragmentEditPhotoBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_edit_photo, container, false);
        setUp();
        binding.setViewModel(mEditPhotoViewModel);
        new HandleZoomEvent(binding.imageViewPhoto);
        return binding.getRoot();
    }

    private void setUp() {
        if (getArguments() != null) {
            Photo photo = getArguments().getParcelable(Constants.EXTRA_PHOTO);
            mEditPhotoViewModel = new EditPhotoViewModel(photo);
        }
    }
}
