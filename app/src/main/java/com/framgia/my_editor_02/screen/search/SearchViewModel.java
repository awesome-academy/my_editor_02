package com.framgia.my_editor_02.screen.search;

import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.screen.BaseViewModel;
import com.framgia.my_editor_02.screen.home.ViewPagerAdapter;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class SearchViewModel extends BaseViewModel {
    private ImageRepository mImageRepository;
    private CompositeDisposable mCompositeDisposable;
    private HistoryAdapter mHistoryAdapter;
    private ViewPagerAdapter mViewPagerAdapter;

    public SearchViewModel(ImageRepository imageRepository, ViewPagerAdapter viewPagerAdapter,
            OnItemRecyclerViewClick.OnSearchHistoryItemClick listener) {
        mImageRepository = imageRepository;
        mViewPagerAdapter = viewPagerAdapter;
        mCompositeDisposable = new CompositeDisposable();
        mHistoryAdapter = new HistoryAdapter(listener);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    public void getSearchHistory() {
        if (mImageRepository.getSearchHistory() != null) {
            mHistoryAdapter.setData(mImageRepository.getSearchHistory());
        }
    }

    public void saveSearchQuery(String searchQuery) {
        mHistoryAdapter.setData(mImageRepository.saveSearchQuery(searchQuery));
    }

    public void removeSearchQuery(int position) {
        List<String> searchQueries = mImageRepository.removeSearchQuery(position);
        if (searchQueries != null) {
            mHistoryAdapter.setData(searchQueries);
        }
    }

    public HistoryAdapter getHistoryAdapter() {
        return mHistoryAdapter;
    }

    public ViewPagerAdapter getViewPagerAdapter() {
        return mViewPagerAdapter;
    }
}
