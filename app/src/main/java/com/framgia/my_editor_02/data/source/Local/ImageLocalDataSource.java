package com.framgia.my_editor_02.data.source.Local;

import com.framgia.my_editor_02.data.source.ImageDataSource;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsApi;
import com.framgia.my_editor_02.data.source.Local.config.SharedPrefsKey;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ImageLocalDataSource implements ImageDataSource.ImageLocalDataSource {
    private static final int FIRST_INDEX = 0;
    private static ImageLocalDataSource sInstance;
    private SharedPrefsApi mSharedPrefsApi;

    public static ImageLocalDataSource getInstance(SharedPrefsApi sharedPrefsApi) {
        if (sInstance == null) {
            sInstance = new ImageLocalDataSource(sharedPrefsApi);
        }
        return sInstance;
    }

    private ImageLocalDataSource(SharedPrefsApi sharedPrefsApi) {
        mSharedPrefsApi = sharedPrefsApi;
    }

    @Override
    public List<String> saveSearchQuery(String searchQuery) {
        List<String> searchHistory = getSearchHistory();
        searchHistory.add(FIRST_INDEX, searchQuery);
        mSharedPrefsApi.put(SharedPrefsKey.SEARCH_HISTORY_KEY, new Gson().toJson(searchHistory));
        return searchHistory;
    }

    @Override
    public List<String> getSearchHistory() {
        String searchHistoryString = mSharedPrefsApi.get(SharedPrefsKey.SEARCH_HISTORY_KEY, null);
        if (searchHistoryString != null) {
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            return new Gson().fromJson(searchHistoryString, listType);
        }
        return null;
    }
}
