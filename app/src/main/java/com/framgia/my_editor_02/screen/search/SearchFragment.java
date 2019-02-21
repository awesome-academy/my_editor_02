package com.framgia.my_editor_02.screen.search;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.framgia.my_editor_02.R;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import com.framgia.my_editor_02.databinding.FragmentSearchBinding;
import com.framgia.my_editor_02.screen.collections.CollectionsFragment;
import com.framgia.my_editor_02.screen.home.HomeFragment;
import com.framgia.my_editor_02.screen.home.ViewPagerAdapter;
import com.framgia.my_editor_02.screen.photos.PhotosFragment;
import com.framgia.my_editor_02.utils.ActionType;
import com.framgia.my_editor_02.utils.Constants;
import com.framgia.my_editor_02.utils.Navigator;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment
        implements SearchView.OnQueryTextListener, OnItemRecyclerViewClick.OnSearchHistoryItemClick,
        TabLayout.OnTabSelectedListener {
    public static final String TAG = SearchFragment.class.getSimpleName();
    private FragmentSearchBinding mBinding;
    private SearchViewModel mSearchViewModel;
    private Navigator mNavigator;
    private PhotosFragment mPhotosFragment;

    public static SearchFragment newInstance() {
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        searchFragment.setArguments(args);
        return searchFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        setUp();
        mBinding.setViewModel(mSearchViewModel);
        return mBinding.getRoot();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_search, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        searchItem.expandActionView();
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        ImageView searchViewIcon =
                searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavigator.removeFragment(getFragmentManager(), HomeFragment.TAG);
                return true;
        }
        return false;
    }

    private void setUp() {
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(
                mBinding.toolBar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar())
                .setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar())
                .setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar())
                .setDisplayShowTitleEnabled(false);
        mNavigator = new Navigator(this);
        ImageRepository repository =
                ImageRepository.getInstance(ImageRemoteDataSource.getInstance(),
                        ImageLocalDataSource.getInstance(new SharedPrefsApi(
                                Objects.requireNonNull(getActivity()).getApplicationContext())));
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        mPhotosFragment = PhotosFragment.newInstance(ActionType.ACTION_SEARCH);
        adapter.addFragment(mPhotosFragment, Constants.PHOTOS);
        adapter.addFragment(CollectionsFragment.newInstance(), Constants.COLLECTIONS);
        mSearchViewModel = new SearchViewModel(repository, adapter, this);
        mSearchViewModel.getSearchHistory();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchViewModel.saveSearchQuery(query);
        searchData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public void onHistoryItemClicked(String searchQuery) {
        searchData(searchQuery);
    }

    @Override
    public void onRemoveHistoryItemClick(int position) {
        mSearchViewModel.removeSearchQuery(position);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mBinding.viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    private void searchData(String query) {
        mBinding.groupSearchHistory.setVisibility(View.GONE);
        mBinding.viewPager.setVisibility(View.VISIBLE);
        mPhotosFragment.searchPhotos(query);
    }
}
