package com.framgia.my_editor_02.data.source.remote.config.service;

import com.framgia.my_editor_02.BuildConfig;
import com.framgia.my_editor_02.data.model.Collection;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageApi {
    @GET("collections/?client_id=" + BuildConfig.API_KEY)
    Single<List<Collection>> getListCollections(@Query("page") int page);
}
