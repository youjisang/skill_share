package com.immymemine.kevin.skillshare.model.online_Class;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class Rediscussion
{
    private String content;

    private String time;

    private String pictureUrl;

    private String name;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

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

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", time = "+time+", pictureUrl = "+pictureUrl+", name = "+name+"]";
    }
}