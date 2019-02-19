package com.framgia.my_editor_02.screen.photos;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentPhotosBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {
    public static final String TAG = PhotosFragment.class.getSimpleName();
    private PhotosViewModel mPhotosViewModel;

    public static PhotosFragment newInstance() {
        PhotosFragment photosFragment = new PhotosFragment();
        Bundle args = new Bundle();
        photosFragment.setArguments(args);
        return photosFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FragmentPhotosBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false);
        setUp();
        binding.setViewModel(mPhotosViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPhotosViewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPhotosViewModel.onStop();
    }

    private void setUp() {
        ImageRepository repository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance());
        mPhotosViewModel = new PhotosViewModel(repository);
    }
}
