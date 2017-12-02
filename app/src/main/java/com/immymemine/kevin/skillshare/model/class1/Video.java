package com.immymemine.kevin.skillshare.model.class1;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class Video
{
    private String duration;

    private String order;

    private String videoUrl;

    private String videoTitle;

    private String videoId;

    private String videoThumbnail;

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getOrder ()
    {
        return order;
    }

    public void setOrder (String order)
    {
        this.order = order;
    }

    public String getVideoUrl ()
    {
        return videoUrl;
    }

    public void setVideoUrl (String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle ()
    {
        return videoTitle;
    }

    public void setVideoTitle (String videoTitle)
    {
        this.videoTitle = videoTitle;
    }

    public String getVideoId ()
    {
        return videoId;
    }

    public void setVideoId (String videoId)
    {
        this.videoId = videoId;
    }

    public String getVideoThumbnail ()
    {
        return videoThumbnail;
    }

    public void setVideoThumbnail (String videoThumbnail)
    {
        this.videoThumbnail = videoThumbnail;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [duration = "+duration+", order = "+order+", videoUrl = "+videoUrl+", videoTitle = "+videoTitle+", videoId = "+videoId+", videoThumbnail = "+videoThumbnail+"]";
    }
}