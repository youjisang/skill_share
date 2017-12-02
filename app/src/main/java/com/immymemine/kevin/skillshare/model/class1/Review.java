package com.immymemine.kevin.skillshare.model.class1;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class Review
{
    private String content;

    private String registrationId;

    private String likeOrDislike;

    private String reviewerName;

    private String reviewerPicture;

    private String reviewId;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getRegistrationId ()
    {
        return registrationId;
    }

    public void setRegistrationId (String registrationId)
    {
        this.registrationId = registrationId;
    }

    public String getLikeOrDislike ()
    {
        return likeOrDislike;
    }

    public void setLikeOrDislike (String likeOrDislike)
    {
        this.likeOrDislike = likeOrDislike;
    }

    public String getReviewerName ()
    {
        return reviewerName;
    }

    public void setReviewerName (String reviewerName)
    {
        this.reviewerName = reviewerName;
    }

    public String getReviewerPicture ()
    {
        return reviewerPicture;
    }

    public void setReviewerPicture (String reviewerPicture)
    {
        this.reviewerPicture = reviewerPicture;
    }

    public String getReviewId ()
    {
        return reviewId;
    }

    public void setReviewId (String reviewId)
    {
        this.reviewId = reviewId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", registrationId = "+registrationId+", likeOrDislike = "+likeOrDislike+", reviewerName = "+reviewerName+", reviewerPicture = "+reviewerPicture+", reviewId = "+reviewId+"]";
    }
}
