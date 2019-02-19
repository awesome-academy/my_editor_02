package com.framgia.my_editor_02.data.repository;

import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.source.Local.ImageLocalDataSource;
import com.framgia.my_editor_02.data.source.remote.ImageRemoteDataSource;
import io.reactivex.Single;
import java.util.List;

public class ImageRepository {
    private static ImageRepository sInstance;
    private ImageRemoteDataSource mRemoteDataSource;
    private ImageLocalDataSource mLocalDataSource;

    private ImageRepository(ImageRemoteDataSource remoteDataSource,
            ImageLocalDataSource imageLocalDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = imageLocalDataSource;
    }

    public static ImageRepository getInstance(ImageRemoteDataSource imageRemoteDataSource,
            ImageLocalDataSource imageLocalDataSource) {
        if (sInstance == null) {
            sInstance = new ImageRepository(imageRemoteDataSource, imageLocalDataSource);
        }
        return sInstance;
    }

    public Single<List<Collection>> getListCollections(int page) {
        return mRemoteDataSource.getListCollections(page);
    }
}
