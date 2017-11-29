package com.immymemine.kevin.skillshare.model.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class Project
{
    private String classId;

    private String likes;

    private String projectThumbnail;

    private String projectId;

    private String projectTitle;

    public String getClassId ()
    {
        return classId;
    }

    public void setClassId (String classId)
    {
        this.classId = classId;
    }

    public String getLikes ()
    {
        return likes;
    }

    public void setLikes (String likes)
    {
        this.likes = likes;
    }

    public String getProjectThumbnail ()
    {
        return projectThumbnail;
    }

    public void setProjectThumbnail (String projectThumbnail)
    {
        this.projectThumbnail = projectThumbnail;
    }

    public String getProjectId ()
    {
        return projectId;
    }

    public void setProjectId (String projectId)
    {
        this.projectId = projectId;
    }

    public String getProjectTitle ()
    {
        return projectTitle;
    }

    public void setProjectTitle (String projectTitle)
    {
        this.projectTitle = projectTitle;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [classId = "+classId+", likes = "+likes+", projectThumbnail = "+projectThumbnail+", projectId = "+projectId+", projectTitle = "+projectTitle+"]";
    }
}
