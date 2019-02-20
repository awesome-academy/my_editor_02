package com.framgia.my_editor_02.screen.collectionsDetail;

import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.screen.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class CollectionsDetailViewModel extends BaseViewModel {
    private CompositeDisposable mCompositeDisposable;
    private ImageRepository mImageRepository;

    public CollectionsDetailViewModel(ImageRepository imageRepository) {
        mCompositeDisposable = new CompositeDisposable();
        mImageRepository = imageRepository;
    }

    public void getCollectionsDetail(String id, int page) {
        Disposable disposable = mImageRepository.getListCollectionsDetail(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Collection>>() {
                    @Override
                    public void accept(List<Collection> collections) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }
}
