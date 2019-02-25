package com.framgia.my_editor_02.screen.collections;

import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.screen.BaseViewModel;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class CollectionsViewModel extends BaseViewModel {
    private ImageRepository mImageRepository;
    private CompositeDisposable mCompositeDisposable;
    private CollectionsAdapter mCollectionsAdapter;

    public CollectionsViewModel(ImageRepository imageRepository,
            OnItemRecyclerViewClick<Collection> itemRecycleViewListener) {
        mImageRepository = imageRepository;
        mCompositeDisposable = new CompositeDisposable();
        mCollectionsAdapter = new CollectionsAdapter(itemRecycleViewListener);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    public void getCollections(int page) {
        Disposable disposable = mImageRepository.getListCollections(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Collection>>() {
                    @Override
                    public void accept(List<Collection> collections) throws Exception {
                        mCollectionsAdapter.updateData(collections);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void searchCollections(String query, int page) {
        mCompositeDisposable.add(mImageRepository.searchCollections(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Collection>>() {
                    @Override
                    public void accept(List<Collection> collections) throws Exception {
                        mCollectionsAdapter.updateData(collections);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    void resetRecyclerViewData() {
        mCollectionsAdapter.resetData();
    }

    public CollectionsAdapter getCollectionsAdapter() {
        return mCollectionsAdapter;
    }
}
