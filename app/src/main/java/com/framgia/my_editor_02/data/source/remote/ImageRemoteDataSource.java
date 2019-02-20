package com.framgia.my_editor_02.data.source.remote;

import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.source.ImageDataSource;
import com.framgia.my_editor_02.data.source.remote.config.response.SearchCollectionResponse;
import com.framgia.my_editor_02.data.source.remote.config.response.SearchPhotoResponse;
import com.framgia.my_editor_02.data.source.remote.config.service.ImageApi;
import com.framgia.my_editor_02.data.source.remote.config.service.ServiceClient;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.List;

public class ImageRemoteDataSource implements ImageDataSource.ImageRemoteDataSource {
    private static ImageRemoteDataSource sInstance;
    private ImageApi mImageApi;

    private ImageRemoteDataSource(ImageApi imageApi) {
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

    @Override
    public Single<List<Photo>> getListPhotos(int page) {
        return mImageApi.getListPhotos(page);
    }

    @Override
    public Single<List<Photo>> searchPhotos(String query, int page) {
        return mImageApi.searchPhotos(query, page)
                .flatMap(new Function<SearchPhotoResponse, SingleSource<? extends List<Photo>>>() {
                    @Override
                    public SingleSource<? extends List<Photo>> apply(
                            SearchPhotoResponse searchPhotoResponse) throws Exception {
                        return Single.just(searchPhotoResponse.getPhotos());
                    }
                });
    }

    @Override
    public Single<List<Collection>> searchCollections(String query, int page) {
        return mImageApi.searchCollections(query, page)
                .flatMap(
                        new Function<SearchCollectionResponse, SingleSource<?
                                extends List<Collection>>>() {
                            @Override
                            public SingleSource<? extends List<Collection>> apply(
                                    SearchCollectionResponse searchCollectionResponse)
                                    throws Exception {
                                return Single.just(searchCollectionResponse.getCollections());
                            }
                        });
    }

    public Single<Photo> getPhoto(String id, int page) {
        return mImageApi.getPhotos(id, page);
    }
}
