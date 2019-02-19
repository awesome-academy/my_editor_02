package com.framgia.my_editor_02.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {
    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("urls")
    @Expose
    private UrlImage mUrlImage;
    @SerializedName("likes")
    @Expose
    private int mLikes;
    @SerializedName("user")
    @Expose
    private User mUser;
    @SerializedName("location")
    @Expose
    private Location mLocation;
    @SerializedName("views")
    @Expose
    private int mViews;
    @SerializedName("downloads")
    @Expose
    private int mDownloads;

    public Photo() {
    }

    protected Photo(Parcel in) {
        mId = in.readString();
        mDescription = in.readString();
        mUrlImage = in.readParcelable(UrlImage.class.getClassLoader());
        mLikes = in.readInt();
        mUser = in.readParcelable(User.class.getClassLoader());
        mViews = in.readInt();
        mDownloads = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mDescription);
        dest.writeParcelable(mUrlImage, flags);
        dest.writeInt(mLikes);
        dest.writeParcelable(mUser, flags);
        dest.writeInt(mViews);
        dest.writeInt(mDownloads);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int likes) {
        mLikes = likes;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public int getViews() {
        return mViews;
    }

    public void setViews(int views) {
        mViews = views;
    }

    public int getDownloads() {
        return mDownloads;
    }

    public void setDownloads(int downloads) {
        mDownloads = downloads;
    }
}
