package com.framgia.my_editor_02.screen.photoDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.databinding.FragmentPhotoDetailBinding;

public class PhotoDetailFragment extends Fragment {

    public static PhotoDetailFragment getInstance() {
        PhotoDetailFragment photoDetailFragment = new PhotoDetailFragment();
        Bundle args = new Bundle();
        photoDetailFragment.setArguments(args);
        return photoDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        PhotoDetailViewModel photoDetailViewModel = new PhotoDetailViewModel();
        FragmentPhotoDetailBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_photo_detail, container, false);
        binding.setViewModel(photoDetailViewModel);
        return binding.getRoot();
    }
}
