package com.immymemine.kevin.skillshare.model.class1;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class Tutor
{
    private String follower;

    private String mClass;

    private String tutorId;

    private String name;

    public String getFollower ()
    {
        return follower;
    }

    public void setFollower (String follower)
    {
        this.follower = follower;
    }

    public String getMClass ()
    {
        return mClass;
    }

    public void setMClass (String mClass)
    {
        this.mClass = mClass;
    }

    public String getTutorId ()
    {
        return tutorId;
    }

    public void setTutorId (String tutorId)
    {
        this.tutorId = tutorId;
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
        return "ClassPojo [follower = "+follower+", mClass = "+mClass+", tutorId = "+tutorId+", name = "+name+"]";
    }
}