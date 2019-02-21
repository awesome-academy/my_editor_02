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
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentPhotoDetailBinding;
import java.util.Objects;

public class PhotoDetailFragment extends Fragment {
    private static final String EXTRA_PHOTO = "EXTRA_PHOTO";
    private static final int DEFAULT_PAGE = 1;
    private Photo mPhoto;
    private PhotoDetailViewModel mPhotoDetailViewModel;
    private FragmentPhotoDetailBinding mBinding;

    public static PhotoDetailFragment getInstance(Photo photo) {
        PhotoDetailFragment photoDetailFragment = new PhotoDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PHOTO, photo);
        photoDetailFragment.setArguments(args);
        return photoDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_photo_detail, container, false);
        initData();
        setUp();
        mBinding.setViewModel(mPhotoDetailViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPhotoDetailViewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPhotoDetailViewModel.onStop();
    }

    void setUp() {
        ImageRepository repository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance(new SharedPrefsApi(
                                Objects.requireNonNull(getActivity()).getApplicationContext())));
        mPhotoDetailViewModel = new PhotoDetailViewModel(repository);
        mPhotoDetailViewModel.getPhoto(mPhoto.getId(), DEFAULT_PAGE);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            mPhoto = bundle.getParcelable(EXTRA_PHOTO);
        }
    }
}
