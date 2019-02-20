package com.framgia.my_editor_02.screen.photos;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentPhotosBinding;
import com.framgia.my_editor_02.screen.photoDetail.PhotoDetailFragment;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.EndlessScrollListener;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment implements OnItemRecyclerViewClick<Photo> {
    public static final String TAG = PhotosFragment.class.getSimpleName();
    private PhotosViewModel mPhotosViewModel;
    private FragmentPhotosBinding mBinding;
    private Navigator mNavigator;

    public static PhotosFragment newInstance() {
        PhotosFragment photosFragment = new PhotosFragment();
        Bundle args = new Bundle();
        photosFragment.setArguments(args);
        return photosFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_photos, container, false);
        setUp();
        mBinding.setViewModel(mPhotosViewModel);
        return mBinding.getRoot();
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
        mNavigator = new Navigator(this);
        ImageRepository repository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance(new SharedPrefsApi(Objects.requireNonNull(
                                Objects.requireNonNull(getActivity()).getApplicationContext()))));
        mPhotosViewModel = new PhotosViewModel(repository, this);
        mPhotosViewModel.getListPhotos(Constants.DEFAULT_PAGE);
        mBinding.recyclerViewListPhoto.addOnScrollListener(new EndlessScrollListener(
                (GridLayoutManager) Objects.requireNonNull(
                        mBinding.recyclerViewListPhoto.getLayoutManager())) {
            @Override
            public void onLoadMore(int page, RecyclerView view) {
                mPhotosViewModel.getListPhotos(page);
            }
        });
    }

    @Override
    public void onItemClick(Photo photo) {
        mNavigator.goNextChildFragment(getFragmentManager(), R.id.layoutContainer,
                PhotoDetailFragment.getInstance(photo), true, TAG);
    }
}
