package com.immymemine.kevin.skillshare.model.m_class;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quf93 on 2017-12-05.
 */

public class Project implements Parcelable {
    String _id;
    String imageUrl;

    public Project(String _id, String imageUrl) {
        this._id = _id;
        this.imageUrl = imageUrl;
    }

    protected Project(Parcel in) {
        _id = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(imageUrl);
    }
}
