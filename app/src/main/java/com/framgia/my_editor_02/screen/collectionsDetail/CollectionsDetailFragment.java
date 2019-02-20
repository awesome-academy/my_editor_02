package com.framgia.my_editor_02.screen.collectionsDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentDetailCollectionsBinding;
import com.framgia.my_editor_02.utils.Constants;
import java.util.Objects;

public class CollectionsDetailFragment extends Fragment {
    public static final String COLLECTIONS = "COLLECTIONS";
    private CollectionsDetailViewModel mDetailViewModel;
    private Collection mCollection;

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
        FragmentDetailCollectionsBinding collectionsBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_detail_collections, container,
                        false);
        ImageRepository imageRepository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance(new SharedPrefsApi(
                                Objects.requireNonNull(getActivity()).getApplicationContext())));
        mDetailViewModel = new CollectionsDetailViewModel(imageRepository);
        collectionsBinding.setViewModel(mDetailViewModel);
        mDetailViewModel.getCollectionsDetail(mCollection.getId(), Constants.DEFAULT_PAGE);
        return collectionsBinding.getRoot();
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
}
