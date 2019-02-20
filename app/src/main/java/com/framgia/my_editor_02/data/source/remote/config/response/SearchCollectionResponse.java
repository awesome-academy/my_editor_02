package com.framgia.my_editor_02.data.source.remote.config.response;

import com.framgia.my_editor_02.data.model.Collection;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchCollectionResponse {
    @SerializedName("results")
    @Expose
    private List<Collection> mCollections;

    public SearchCollectionResponse() {
    }

    public List<Collection> getCollections() {
        return mCollections;
    }

    public void setCollections(List<Collection> collections) {
        mCollections = collections;
    }
}
