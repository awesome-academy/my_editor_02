package com.framgia.my_editor_02.data.source.remote.config.response;

import com.framgia.my_editor_02.data.model.Photo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchPhotoResponse {
    @SerializedName("results")
    @Expose
    private List<Photo> mPhotos;

    public SearchPhotoResponse() {
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> photos) {
        mPhotos = photos;
    }
}
