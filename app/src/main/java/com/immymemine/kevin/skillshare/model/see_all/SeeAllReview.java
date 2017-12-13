package com.immymemine.kevin.skillshare.model.see_all;

import java.util.List;

/**
 * Created by quf93 on 2017-12-08.
 */

public class SeeAllReview extends SeeAll {
    String reviewPercent;
    String positiveReviewCount;
    String negativeReviewCount;
    List<Review> reviews;

    // test
    public SeeAllReview(String reviewPercent,
            String positiveReviewCount,
            String negativeReviewCount,
            List<Review> reviews) {
        this.reviewPercent = reviewPercent;
        this.positiveReviewCount = positiveReviewCount;
        this.negativeReviewCount = negativeReviewCount;
        this.reviews = reviews;
    }

    public String getReviewPercent() {
        return reviewPercent;
    }

    public String getPositiveReviewCount() {
        return positiveReviewCount;
    }

    public String getNegativeReviewCount() {
        return negativeReviewCount;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
