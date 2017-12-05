package com.immymemine.kevin.skillshare.model.online_Class;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class RelatedClass
{
    private String title;

    private String thumbnail;

    private String tutor;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getThumbnail ()
    {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getTutor ()
    {
        return tutor;
    }

    public void setTutor (String tutor)
    {
        this.tutor = tutor;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", thumbnail = "+thumbnail+", tutor = "+tutor+"]";
    }
}