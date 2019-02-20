package com.framgia.my_editor_02.data.source.remote.config.service;

import com.framgia.my_editor_02.BuildConfig;
import com.framgia.my_editor_02.data.model.Collection;
import com.framgia.my_editor_02.data.model.Photo;
import com.framgia.my_editor_02.data.source.remote.config.response.SearchCollectionResponse;
import com.framgia.my_editor_02.data.source.remote.config.response.SearchPhotoResponse;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageApi {
    @GET("collections/?client_id=" + BuildConfig.API_KEY)
    Single<List<Collection>> getListCollections(@Query("page") int page);

    @GET("photos?client_id=" + BuildConfig.API_KEY)
    Single<List<Photo>> getListPhotos(@Query("page") int page);

    @GET("search/photos?client_id=" + BuildConfig.API_KEY)
    Single<SearchPhotoResponse> searchPhotos(@Query("query") String query, @Query("page") int page);

    @GET("search/collections?client_id=" + BuildConfig.API_KEY)
    Single<SearchCollectionResponse> searchCollections(@Query("query") String query,
            @Query("page") int page);
}
