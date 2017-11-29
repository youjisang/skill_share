package com.immymemine.kevin.skillshare.model.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class Followers
{
    private String picture_url;

    private String name;

    private String userId;

    public String getPicture_url ()
    {
        return picture_url;
    }

    public void setPicture_url (String picture_url)
    {
        this.picture_url = picture_url;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [picture_url = "+picture_url+", name = "+name+", userId = "+userId+"]";
    }
}
