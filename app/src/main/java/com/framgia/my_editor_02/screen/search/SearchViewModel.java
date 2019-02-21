package com.framgia.my_editor_02.screen.search;

import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.screen.BaseViewModel;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class SearchViewModel extends BaseViewModel {
    private ImageRepository mImageRepository;
    private CompositeDisposable mCompositeDisposable;
    private HistoryAdapter mHistoryAdapter;

    public SearchViewModel(ImageRepository imageRepository,
            OnItemRecyclerViewClick.OnSearchHistoryItemClick listener) {
        mImageRepository = imageRepository;
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

    public void searchPhotos(String query, int page) {
        mCompositeDisposable.add(mImageRepository.searchPhotos(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Photo>>() {
                    @Override
                    public void accept(List<Photo> photos) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void searchCollections(String query, int page) {
        mCompositeDisposable.add(mImageRepository.searchCollections(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Collection>>() {
                    @Override
                    public void accept(List<Collection> collections) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void getSearchHistory() {
        if (mImageRepository.getSearchHistory() != null) {
            mHistoryAdapter.setData(mImageRepository.getSearchHistory());
        }
    }

    public void saveSearchQuery(String searchQuery) {
        if (mImageRepository.getSearchHistory() != null) {
            mHistoryAdapter.setData(mImageRepository.saveSearchQuery(searchQuery));
        }
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
}
