package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-05.
 */

public class Review {

    String likeOrNot;
    String content;
    String reviewerName;
    String imageUrl;

    public Review(String likeOrNot,
                  String content,
                  String reviewerName,
                  String imageUrl) {
        this.imageUrl = imageUrl;
        this.content = content;
        this.reviewerName = reviewerName;
        this.likeOrNot = likeOrNot;
    }

    public String getLikeOrNot() {
        return likeOrNot;
    }

    public String getContent() {
        return content;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
