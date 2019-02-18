package com.framgia.my_editor_02.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection implements Parcelable {
    public static final Creator<Collection> CREATOR = new Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("published_at")
    @Expose
    private String mPublishAt;
    @SerializedName("total_photos")
    @Expose
    private Integer mTotalPhotos;
    @SerializedName("cover_photo")
    @Expose
    private ConvertPhoto mConvertPhoto;

    protected Collection(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mPublishAt = in.readString();
        if (in.readByte() == 0) {
            mTotalPhotos = null;
        } else {
            mTotalPhotos = in.readInt();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mPublishAt);
        if (mTotalPhotos == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mTotalPhotos);
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPublishAt() {
        return mPublishAt;
    }

    public void setPublishAt(String publishAt) {
        mPublishAt = publishAt;
    }

    public Integer getTotalPhotos() {
        return mTotalPhotos;
    }

    public void setTotalPhotos(Integer totalPhotos) {
        mTotalPhotos = totalPhotos;
    }

    public ConvertPhoto getConvertPhoto() {
        return mConvertPhoto;
    }

    public void setConvertPhoto(ConvertPhoto convertPhoto) {
        mConvertPhoto = convertPhoto;
    }
}
