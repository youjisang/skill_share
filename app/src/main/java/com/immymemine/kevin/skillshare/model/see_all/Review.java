package com.immymemine.kevin.skillshare.model.see_all;

/**
 * Created by quf93 on 2017-12-08.
 */

public class Review extends SeeAll {
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
}
