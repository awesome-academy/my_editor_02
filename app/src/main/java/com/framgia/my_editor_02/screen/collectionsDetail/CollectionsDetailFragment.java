package com.framgia.my_editor_02.screen.collectionsDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentDetailCollectionsBinding;
import com.framgia.my_editor_02.screen.photoDetail.PhotoDetailFragment;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.EndlessScrollListener;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.Objects;

public class CollectionsDetailFragment extends Fragment implements OnItemRecyclerViewClick<Photo> {
    public static final String TAG = CollectionsDetailFragment.class.getSimpleName();
    public static final String COLLECTIONS = "COLLECTIONS";
    private CollectionsDetailViewModel mDetailViewModel;
    private Collection mCollection;
    private Navigator mNavigator;

    public static CollectionsDetailFragment newInstance(Collection collection) {
        CollectionsDetailFragment detailFragment = new CollectionsDetailFragment();
        Bundle arg = new Bundle();
        arg.putParcelable(COLLECTIONS, collection);
        detailFragment.setArguments(arg);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mNavigator = new Navigator(this);
        FragmentDetailCollectionsBinding collectionsBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_detail_collections, container,
                        false);
        ImageRepository imageRepository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance(new SharedPrefsApi(
                                Objects.requireNonNull(getActivity()).getApplicationContext())));
        mDetailViewModel = new CollectionsDetailViewModel(imageRepository, mCollection, this);
        collectionsBinding.setViewModel(mDetailViewModel);
        mDetailViewModel.getCollectionsDetail(mCollection.getId(), Constants.DEFAULT_PAGE);
        setLoadMore(collectionsBinding);
        return collectionsBinding.getRoot();
    }

    private void setLoadMore(FragmentDetailCollectionsBinding collectionsBinding) {
        collectionsBinding.recyclerViewDetail.addOnScrollListener(new EndlessScrollListener(
                (LinearLayoutManager) collectionsBinding.recyclerViewDetail.getLayoutManager()) {
            @Override
            public boolean setResetState() {
                return false;
            }

            @Override
            public void onLoadMore(int page, RecyclerView view) {
                mDetailViewModel.getCollectionsDetail(mCollection.getId(), page);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCollection = getArguments().getParcelable(COLLECTIONS);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mDetailViewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDetailViewModel.onStop();
    }

    @Override
    public void onItemClick(Photo photo) {
        mNavigator.goNextChildFragment(getFragmentManager(), R.id.layoutContainer,
                PhotoDetailFragment.getInstance(photo), true, TAG);
    }
}
