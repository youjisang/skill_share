package com.immymemine.kevin.skillshare.model.m_class;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quf93 on 2017-12-03.
 */

public class Reply implements Parcelable {
    String name;
    String imageUrl;
    String content;
    String time;

    // test
    public Reply(String name,
            String imageUrl,
            String content,
            String time) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.content = content;
        this.time = time;
    }

    protected Reply(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        content = in.readString();
        time = in.readString();
    }

    public static final Creator<Reply> CREATOR = new Creator<Reply>() {
        @Override
        public Reply createFromParcel(Parcel in) {
            return new Reply(in);
        }

        @Override
        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(content);
        dest.writeString(time);
    }
}
