package com.framgia.my_editor_02.screen.photoDetail;

import android.app.DownloadManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentPhotoDetailBinding;
import com.framgia.my_editor_02.screen.edit.EditPhotoFragment;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.Navigator;
import java.util.Objects;

public class PhotoDetailFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = PhotoDetailFragment.class.getSimpleName();
    private static final String EXTRA_PHOTO = "EXTRA_PHOTO";
    private static final int DEFAULT_PAGE = 1;
    private Photo mPhoto;
    private PhotoDetailViewModel mPhotoDetailViewModel;
    private FragmentPhotoDetailBinding mBinding;
    private Navigator mNavigator;

    public static PhotoDetailFragment getInstance(Photo photo) {
        PhotoDetailFragment photoDetailFragment = new PhotoDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_PHOTO, photo);
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
        mBinding.buttonDownload.setOnClickListener(this);
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
        mNavigator = new Navigator(this);
        ImageRepository repository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance(new SharedPrefsApi(
                                Objects.requireNonNull(getActivity()).getApplicationContext())));
        mPhotoDetailViewModel = new PhotoDetailViewModel(repository);
        mPhotoDetailViewModel.getPhoto(mPhoto.getId(), DEFAULT_PAGE);
        mBinding.imageViewDetail.setOnClickListener(this);
        mBinding.buttonEdit.setOnClickListener(this);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            mPhoto = bundle.getParcelable(Constants.EXTRA_PHOTO);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewDetail:
            case R.id.buttonEdit:
                mNavigator.goNextChildFragment(getFragmentManager(), R.id.layoutContainer,
                        EditPhotoFragment.newInstance(mPhoto), true, PhotoDetailFragment.TAG);
                break;
            case R.id.buttonDownload:
                downLoad();
                break;
        }
    }

    public void downLoad() {
        DownloadManager downloadManager = (DownloadManager) getActivity().getApplicationContext()
                .getSystemService(Context.DOWNLOAD_SERVICE);
        String linkDownLoad = mPhoto.getUrlImage().getSmall();
        if (linkDownLoad != null) {
            Uri uri = Uri.parse(linkDownLoad);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(mPhoto.getUser().getName());
            request.setDescription(R.string.down_load + "");
            assert downloadManager != null;
            downloadManager.enqueue(request);
        } else {
            Toast.makeText(getActivity(), R.string.link_error, Toast.LENGTH_SHORT).show();
        }
    }
}
