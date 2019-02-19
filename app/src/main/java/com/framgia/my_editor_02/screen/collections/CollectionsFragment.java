package com.framgia.my_editor_02.screen.collections;

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
import com.framgia.my_editor_02.databinding.FragmentCollectionsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionsFragment extends Fragment {
    private static final int DEFAULT_PAGE = 1;

    public static CollectionsFragment newInstance() {
        CollectionsFragment collectionsFragment = new CollectionsFragment();
        Bundle args = new Bundle();
        collectionsFragment.setArguments(args);
        return collectionsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FragmentCollectionsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_collections, container, false);
        ImageRepository repository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance());
        CollectionsViewModel viewModel = new CollectionsViewModel(repository);
        binding.setViewmodel(viewModel);
        viewModel.getCollections(DEFAULT_PAGE);
        return binding.getRoot();
    }
}
