package com.immymemine.kevin.skillshare.model.m_class;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quf93 on 2017-12-05.
 */

public class Subscriber implements Parcelable {

    String _id;
    String name;
    String imageUrl;

    // for test
    public Subscriber(String _id, String name, String imageUrl) {
        this._id = _id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    protected Subscriber(Parcel in) {
        _id = in.readString();
        name = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Subscriber> CREATOR = new Creator<Subscriber>() {
        @Override
        public Subscriber createFromParcel(Parcel in) {
            return new Subscriber(in);
        }

        @Override
        public Subscriber[] newArray(int size) {
            return new Subscriber[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(imageUrl);
    }
}
