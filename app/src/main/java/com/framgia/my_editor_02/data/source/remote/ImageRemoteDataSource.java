package com.framgia.my_editor_02.data.source.remote;

import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.source.ImageDataSource;
import com.framgia.my_editor_02.data.source.remote.config.service.ImageApi;
import com.framgia.my_editor_02.data.source.remote.config.service.ServiceClient;
import io.reactivex.Single;
import java.util.List;

public class ImageRemoteDataSource implements ImageDataSource.ImageRemoteDataSource {
    private static ImageRemoteDataSource sInstance;
    private ImageApi mImageApi;

    public ImageRemoteDataSource(ImageApi imageApi) {
        mImageApi = imageApi;
    }

    public static ImageRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new ImageRemoteDataSource(ServiceClient.createServiceClient());
        }
        return sInstance;
    }

    @Override
    public Single<List<Collection>> getListCollections(int page) {
        return mImageApi.getListCollections(page);
    }
}
