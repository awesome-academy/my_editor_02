package com.framgia.my_editor_02.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileImage implements Parcelable {
    public static final Creator<ProfileImage> CREATOR = new Creator<ProfileImage>() {
        @Override
        public ProfileImage createFromParcel(Parcel in) {
            return new ProfileImage(in);
        }

        @Override
        public ProfileImage[] newArray(int size) {
            return new ProfileImage[size];
        }
    };
    @SerializedName("small")
    @Expose
    private String mSmallImage;
    @SerializedName("medium")
    @Expose
    private String mMediumImage;
    @SerializedName("large")
    @Expose
    private String mLargeImage;

    protected ProfileImage(Parcel in) {
        mSmallImage = in.readString();
        mMediumImage = in.readString();
        mLargeImage = in.readString();
    }

    public String getSmallImage() {
        return mSmallImage;
    }

    public void setSmallImage(String smallImage) {
        mSmallImage = smallImage;
    }

    public String getMediumImage() {
        return mMediumImage;
    }

    public void setMediumImage(String mediumImage) {
        mMediumImage = mediumImage;
    }

    public String getLargeImage() {
        return mLargeImage;
    }

    public void setLargeImage(String largeImage) {
        mLargeImage = largeImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSmallImage);
        dest.writeString(mMediumImage);
        dest.writeString(mLargeImage);
    }
}
