package com.immymemine.kevin.skillshare.model.m_class;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quf93 on 2017-12-05.
 */

public class Review implements Parcelable {

    String likeOrNot;
    String content;
    String reviewerId;
    String reviewerName;
    String imageUrl;

    public Review(String likeOrNot,
                  String content,
                  String reviewerId,
                  String reviewerName,
                  String imageUrl) {
        this.imageUrl = imageUrl;
        this.content = content;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.likeOrNot = likeOrNot;
    }

    protected Review(Parcel in) {
        likeOrNot = in.readString();
        content = in.readString();
        reviewerId = in.readString();
        reviewerName = in.readString();
        imageUrl = in.readString();
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

    public String getLikeOrNot() {
        return likeOrNot;
    }

    public String getContent() {
        return content;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public String getReviewerName() {
        return reviewerName;
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
        dest.writeString(likeOrNot);
        dest.writeString(content);
        dest.writeString(reviewerId);
        dest.writeString(reviewerName);
        dest.writeString(imageUrl);
    }
}
