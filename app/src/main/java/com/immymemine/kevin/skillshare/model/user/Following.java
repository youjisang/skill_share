package com.immymemine.kevin.skillshare.model.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class Following
{
    private String pictureUrl;

    private String name;

    private String userId;

    public String getPictureUrl ()
    {
        return pictureUrl;
    }

    public void setPictureUrl (String pictureUrl)
    {
        this.pictureUrl = pictureUrl;
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
        return "ClassPojo [pictureUrl = "+pictureUrl+", name = "+name+", userId = "+userId+"]";
    }
}
