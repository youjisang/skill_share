package com.immymemine.kevin.skillshare.model.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class Discussion
{
    private String commentTitle;

    private String time;

    private String classId;

    private String discussionId;

    public String getCommentTitle ()
    {
        return commentTitle;
    }

    public void setCommentTitle (String commentTitle)
    {
        this.commentTitle = commentTitle;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getClassId ()
    {
        return classId;
    }

    public void setClassId (String classId)
    {
        this.classId = classId;
    }

    public String getDiscussionId ()
    {
        return discussionId;
    }

    public void setDiscussionId (String discussionId)
    {
        this.discussionId = discussionId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [commentTitle = "+commentTitle+", time = "+time+", classId = "+classId+", discussionId = "+discussionId+"]";
    }
}