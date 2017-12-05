package com.immymemine.kevin.skillshare.model.online_Class;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class Discussion
{
    private String content;

    private String picture;

    private String time;

    private String name;

    private String like;

    private Rediscussion rediscussion;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getPicture ()
    {
        return picture;
    }

    public void setPicture (String picture)
    {
        this.picture = picture;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getLike ()
    {
        return like;
    }

    public void setLike (String like)
    {
        this.like = like;
    }

    public Rediscussion getRediscussion ()
    {
        return rediscussion;
    }

    public void setRediscussion (Rediscussion rediscussion)
    {
        this.rediscussion = rediscussion;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", picture = "+picture+", time = "+time+", name = "+name+", like = "+like+", rediscussion = "+rediscussion+"]";
    }
}
