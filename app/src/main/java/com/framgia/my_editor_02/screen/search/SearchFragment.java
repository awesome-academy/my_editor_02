package com.framgia.my_editor_02.screen.search;

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
import com.framgia.my_editor_02.databinding.FragmentSearchBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private SearchViewModel mSearchViewModel;

    public static SearchFragment newInstance() {
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        searchFragment.setArguments(args);
        return searchFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        FragmentSearchBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        setUp();
        binding.setViewModel(mSearchViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mSearchViewModel.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSearchViewModel.onStop();
    }

    private void setUp() {
        ImageRepository repository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance());
        mSearchViewModel = new SearchViewModel(repository);
    }
}
