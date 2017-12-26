package com.immymemine.kevin.skillshare.model.see_all;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quf93 on 2017-12-08.
 */

public class Review implements Parcelable {
    String bool;
    String content;
    String reviewerName;
    String pictureUrl;

    public Review(String bool,
            String content,
            String reviewerName,
            String pictureUrl) {
        this.bool = bool;
        this.content = content;
        this.reviewerName = reviewerName;
        this.pictureUrl = pictureUrl;
    }

    protected Review(Parcel in) {
        bool = in.readString();
        content = in.readString();
        reviewerName = in.readString();
        pictureUrl = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getBool() {
        return bool;
    }

    public String getContent() {
        return content;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bool);
        dest.writeString(content);
        dest.writeString(reviewerName);
        dest.writeString(pictureUrl);
    }
}
