package com.framgia.my_editor_02.data.source.remote.config.service;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceClient {
    private static final String BASE_API_URL = "https://api.unsplash.com/";

    public static ImageApi createServiceClient() {
        return new Retrofit.Builder().baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ImageApi.class);
    }
}
