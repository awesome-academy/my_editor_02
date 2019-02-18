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
    private static CollectionsFragment sInstance;
    private FragmentCollectionsBinding mBinding;
    private CollectionsViewModel mViewModel;
    private ImageRepository mRepository;

    public static CollectionsFragment getInstance() {
        CollectionsFragment collectionsFragment = new CollectionsFragment();
        Bundle args = new Bundle();
        collectionsFragment.setArguments(args);
        return collectionsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_collections, container, false);
        mRepository = ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                ImageLocalDataSource.getInstance());
        mViewModel = new CollectionsViewModel(mRepository);
        mBinding.setViewmodel(mViewModel);
        mViewModel.getCollections();
        return mBinding.getRoot();
    }
}
