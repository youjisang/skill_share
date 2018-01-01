package com.immymemine.kevin.skillshare.model.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class SubscribedClass {
    // test
    public SubscribedClass(String classThumbnail, String feedback, String title, String totalDuration, String classId, String views, String tutorName) {
        this.classThumbnail = classThumbnail;
        this.feedback = feedback;
        this.title = title;
        this.totalDuration = totalDuration;
        this.classId = classId;
        this.views = views;
        this.tutorName = tutorName;
    }

    private String classThumbnail;

    private String feedback;

    private String title;

    private String totalDuration;

    private String classId;

    private String views;

    private String tutorName;

    public String getClassThumbnail ()
    {
        return classThumbnail;
    }

    public void setClassThumbnail (String classThumbnail)
    {
        this.classThumbnail = classThumbnail;
    }

    public String getFeedback ()
    {
        return feedback;
    }

    public void setFeedback (String feedback)
    {
        this.feedback = feedback;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getTotalDuration ()
    {
        return totalDuration;
    }

    public void setTotalDuration (String totalDuration)
    {
        this.totalDuration = totalDuration;
    }

    public String getClassId ()
    {
        return classId;
    }

    public void setClassId (String classId)
    {
        this.classId = classId;
    }

    public String getViews ()
    {
        return views;
    }

    public void setViews (String views)
    {
        this.views = views;
    }

    public String getTutorName ()
    {
        return tutorName;
    }

    public void setTutorName (String tutorName)
    {
        this.tutorName = tutorName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [classThumbnail = "+classThumbnail+", feedback = "+feedback+", title = "+title+", totalDuration = "+totalDuration+", classId = "+classId+", views = "+views+", tutorName = "+tutorName+"]";
    }
}
