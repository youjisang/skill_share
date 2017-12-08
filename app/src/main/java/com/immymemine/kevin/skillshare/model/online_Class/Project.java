package com.immymemine.kevin.skillshare.model.online_Class;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class Project
{
    private String thumbnail;

    public String getThumbnail ()
    {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [thumbnail = "+thumbnail+"]";
    }
}