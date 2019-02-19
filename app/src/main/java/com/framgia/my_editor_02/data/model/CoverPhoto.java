package com.framgia.my_editor_02.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoverPhoto implements Parcelable {
    public static final Creator<CoverPhoto> CREATOR = new Creator<CoverPhoto>() {
        @Override
        public CoverPhoto createFromParcel(Parcel in) {
            return new CoverPhoto(in);
        }

        @Override
        public CoverPhoto[] newArray(int size) {
            return new CoverPhoto[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("created_at")
    @Expose
    private String mCreatedAt;
    @SerializedName("updated_at")
    @Expose
    private String mUpdatedAt;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("urls")
    @Expose
    private UrlImage mUrlImage;

    protected CoverPhoto(Parcel in) {
        mId = in.readString();
        mCreatedAt = in.readString();
        mUpdatedAt = in.readString();
        mDescription = in.readString();
        mUrlImage = in.readParcelable(UrlImage.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCreatedAt);
        dest.writeString(mUpdatedAt);
        dest.writeString(mDescription);
        dest.writeParcelable(mUrlImage, flags);
    }

    public String getId(){
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public UrlImage getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(UrlImage urlImage) {
        mUrlImage = urlImage;
    }
}
