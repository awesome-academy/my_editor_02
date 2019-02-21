package com.framgia.my_editor_02.screen.photoDetail;

import android.databinding.ObservableField;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.repository.ImageRepository;
import com.framgia.my_editor_02.screen.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PhotoDetailViewModel extends BaseViewModel {
    public ObservableField<Photo> mPhotoObservableField = new ObservableField<>();
    private ImageRepository mImageRepository;
    private CompositeDisposable mCompositeDisposable;

    public PhotoDetailViewModel(ImageRepository imageRepository) {
        mImageRepository = imageRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {
        mCompositeDisposable.clear();
    }

    void getPhoto(String id, int page) {
        mCompositeDisposable.add(mImageRepository.getPhoto(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Photo>() {
                    @Override
                    public void accept(Photo photo) throws Exception {
                        setPhoto(photo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void setPhoto(Photo photo) {
        mPhotoObservableField.set(photo);
    }
}
