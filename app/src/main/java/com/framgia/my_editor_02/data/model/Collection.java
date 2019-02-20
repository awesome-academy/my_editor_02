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
    private CoverPhoto mConvertPhoto;
    @SerializedName("user")
    @Expose
    private User mUser;

    protected Collection(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mPublishAt = in.readString();
        if (in.readByte() == 0) {
            mTotalPhotos = null;
        } else {
            mTotalPhotos = in.readInt();
        }
        mConvertPhoto = in.readParcelable(CoverPhoto.class.getClassLoader());
        mUser = in.readParcelable(User.class.getClassLoader());
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
        dest.writeParcelable(mConvertPhoto, flags);
        dest.writeParcelable(mUser, flags);
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

    public CoverPhoto getConvertPhoto() {
        return mConvertPhoto;
    }

    public void setConvertPhoto(CoverPhoto convertPhoto) {
        mConvertPhoto = convertPhoto;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
