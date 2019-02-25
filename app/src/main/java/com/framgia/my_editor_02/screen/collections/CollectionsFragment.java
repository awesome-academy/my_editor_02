package com.framgia.my_editor_02.screen.collections;

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
import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentCollectionsBinding;
import com.framgia.my_editor_02.screen.collectionsDetail.CollectionsDetailFragment;
import com.framgia.my_editor_02.utils.ActionType;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.EndlessScrollListener;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionsFragment extends Fragment implements OnItemRecyclerViewClick<Collection> {
    public static final String TAG = CollectionsDetailFragment.class.getSimpleName();
    private CollectionsViewModel mCollectionsViewModel;
    private Navigator mNavigator;
    private int mActionType;
    private String mSearchQuery;
    private boolean mIsResetState;

    public static CollectionsFragment newInstance(int action) {
        CollectionsFragment collectionsFragment = new CollectionsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ACTION_TYPE, action);
        collectionsFragment.setArguments(args);
        return collectionsFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCollectionsViewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCollectionsViewModel.onStop();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FragmentCollectionsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_collections, container, false);
        ImageRepository repository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance(new SharedPrefsApi(
                                Objects.requireNonNull(getActivity()).getApplicationContext())));
        mNavigator = new Navigator(this);
        mCollectionsViewModel = new CollectionsViewModel(repository, this);
        if (getArguments() != null) {
            mActionType = getArguments().getInt(Constants.ACTION_TYPE, ActionType.ACTION_GET_LIST);
        }
        if (mActionType == ActionType.ACTION_GET_LIST) {
            mCollectionsViewModel.getCollections(Constants.DEFAULT_PAGE);
        }
        binding.setViewModel(mCollectionsViewModel);
        setLoadMore(binding);
        return binding.getRoot();
    }

    private void setLoadMore(FragmentCollectionsBinding binding) {
        binding.RecyclerViewCollections.addOnScrollListener(new EndlessScrollListener(
                (GridLayoutManager) Objects.requireNonNull(
                        binding.RecyclerViewCollections.getLayoutManager())) {
            @Override
            public boolean setResetState() {
                if (mIsResetState) {
                    mIsResetState = false;
                    return true;
                }
                return false;
            }

            @Override
            public void onLoadMore(int page, RecyclerView view) {
                if (mActionType == ActionType.ACTION_GET_LIST) {
                    mCollectionsViewModel.getCollections(page);
                } else if (mActionType == ActionType.ACTION_SEARCH) {
                    mCollectionsViewModel.searchCollections(mSearchQuery, page);
                }
                mCollectionsViewModel.getCollections(page);
            }
        });
    }

    @Override
    public void onItemClick(Collection item) {
        mNavigator.goNextChildFragment(
                Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                R.id.layoutContainer, CollectionsDetailFragment.newInstance(item), true, TAG);
    }

    public void searchCollections(String searchQuery) {
        mSearchQuery = searchQuery;
        mIsResetState = true;
        mCollectionsViewModel.resetRecyclerViewData();
        mCollectionsViewModel.searchCollections(searchQuery, Constants.DEFAULT_PAGE);
    }
}
