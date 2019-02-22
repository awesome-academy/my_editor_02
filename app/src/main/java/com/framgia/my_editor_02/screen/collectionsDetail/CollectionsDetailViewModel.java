package com.framgia.my_editor_02.screen.collectionsDetail;

import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.screen.BaseViewModel;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class CollectionsDetailViewModel extends BaseViewModel {
    private static final String TARGET = "h=128&w=128";
    private static final String REPLACEMENT = "h=256&w=256";
    private static final String PHOTO = " Photos";
    private CompositeDisposable mCompositeDisposable;
    private ImageRepository mImageRepository;
    private CollectionDetailAdapter mDetailAdapter;
    private Collection mCollection;

    public CollectionsDetailViewModel(ImageRepository imageRepository, Collection collection,
            OnItemRecyclerViewClick<Photo> itemRecyclerViewClick) {
        mCompositeDisposable = new CompositeDisposable();
        mImageRepository = imageRepository;
        mDetailAdapter = new CollectionDetailAdapter(itemRecyclerViewClick);
        mCollection = collection;
    }

    public void getCollectionsDetail(String id, int page) {
        Disposable disposable = mImageRepository.getListCollectionsDetail(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Photo>>() {
                    @Override
                    public void accept(List<Photo> photos) throws Exception {
                        mDetailAdapter.updateData(photos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public String getImageProfile() {
        return mCollection.getUser().getProfileImage().getLargeImage().replace(TARGET, REPLACEMENT);
    }

    public String getTotalPhoto() {
        return (mCollection.getTotalPhotos() + PHOTO);
    }

    public Collection getCollection() {
        return mCollection;
    }

    public CollectionDetailAdapter getCollectionDetailAdapter() {
        return mDetailAdapter;
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }
}
