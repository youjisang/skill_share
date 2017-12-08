package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-05.
 */

public class Review {

    String reviewPercent;
    String content;
    String reviewerName;
    String pictureUrl;

    public Review(String reviewPercent,
            String content,
            String reviewerName,
            String pictureUrl) {
        this.pictureUrl = pictureUrl;
        this.content = content;
        this.reviewerName = reviewerName;
        this.reviewPercent = reviewPercent;
    }

    public String getReviewPercent() {
        return reviewPercent;
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
}
