package com.framgia.my_editor_02.screen.photos;

import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.screen.BaseViewModel;
import com.framgia.my_editor_02.utils.OnItemRecyclerViewClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class PhotosViewModel extends BaseViewModel {
    private ImageRepository mImageRepository;
    private CompositeDisposable mCompositeDisposable;
    private PhotoAdapter mPhotoAdapter;

    public PhotosViewModel(ImageRepository imageRepository,
            OnItemRecyclerViewClick<Photo> listener) {
        mImageRepository = imageRepository;
        mCompositeDisposable = new CompositeDisposable();
        mPhotoAdapter = new PhotoAdapter(listener);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    void getListPhotos(int page) {
        mCompositeDisposable.add(mImageRepository.getListPhotos(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Photo>>() {
                    @Override
                    public void accept(List<Photo> photos) throws Exception {
                        mPhotoAdapter.setData(photos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    public PhotoAdapter getPhotoAdapter() {
        return mPhotoAdapter;
    }
}
