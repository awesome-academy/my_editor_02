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
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentCollectionsBinding;
import com.framgia.my_editor_02.utils.EndlessScrollListener;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionsFragment extends Fragment {
    private static final int DEFAULT_PAGE = 1;
    private CollectionsViewModel mCollectionsViewModel;

    public static CollectionsFragment newInstance() {
        CollectionsFragment collectionsFragment = new CollectionsFragment();
        Bundle args = new Bundle();
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
        mCollectionsViewModel = new CollectionsViewModel(repository);
        mCollectionsViewModel.getCollections(DEFAULT_PAGE);
        binding.setViewModel(mCollectionsViewModel);
        setLoadMore(binding);
        return binding.getRoot();
    }

    private void setLoadMore(FragmentCollectionsBinding binding) {
        binding.RecyclerViewCollections.addOnScrollListener(new EndlessScrollListener(
                (GridLayoutManager) Objects.requireNonNull(
                        binding.RecyclerViewCollections.getLayoutManager())) {
            @Override
            public void onLoadMore(int page, RecyclerView view) {
                mCollectionsViewModel.getCollections(page);
            }
        });
    }
}
