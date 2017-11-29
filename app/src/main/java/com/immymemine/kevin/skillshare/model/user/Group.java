package com.immymemine.kevin.skillshare.model.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class Group
{
    private String groupId;

    private String groupName;

    public String getGroupId ()
    {
        return groupId;
    }

    public void setGroupId (String groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupName ()
    {
        return groupName;
    }

    public void setGroupName (String groupName)
    {
        this.groupName = groupName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [groupId = "+groupId+", groupName = "+groupName+"]";
    }
}
