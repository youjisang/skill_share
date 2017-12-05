package com.immymemine.kevin.skillshare.model.online_Class;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class Tutor
{
    private String follower;

    private String name;

    private String className;

    public String getFollower ()
    {
        return follower;
    }

    public void setFollower (String follower)
    {
        this.follower = follower;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getClassName ()
    {
        return className;
    }

    public void setClassName (String className)
    {
        this.className = className;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [follower = "+follower+", name = "+name+", className = "+className+"]";
    }
}