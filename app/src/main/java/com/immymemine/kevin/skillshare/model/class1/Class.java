package com.immymemine.kevin.skillshare.model.class1;

/**
 * Created by JisangYou on 2017-11-30.
 */

public class Class
{
    private String subscriberCount;

    private Discussion discussion;

    private String classId;

    private String feedback;

    private Project project;

    private RelatedClass relatedClass;

    private String title;

    private String[] category;

    private String totalDuration;

    private Tutor tutor;

    private Subscriber subscriber;

    private Video video;

    private Review review;

    public String getSubscriberCount ()
    {
        return subscriberCount;
    }

    public void setSubscriberCount (String subscriberCount)
    {
        this.subscriberCount = subscriberCount;
    }

    public Discussion getDiscussion ()
    {
        return discussion;
    }

    public void setDiscussion (Discussion discussion)
    {
        this.discussion = discussion;
    }

    public String getClassId ()
    {
        return classId;
    }

    public void setClassId (String classId)
    {
        this.classId = classId;
    }

    public String getFeedback ()
    {
        return feedback;
    }

    public void setFeedback (String feedback)
    {
        this.feedback = feedback;
    }

    public Project getProject ()
    {
        return project;
    }

    public void setProject (Project project)
    {
        this.project = project;
    }

    public RelatedClass getRelatedClass ()
    {
        return relatedClass;
    }

    public void setRelatedClass (RelatedClass relatedClass)
    {
        this.relatedClass = relatedClass;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String[] getCategory ()
    {
        return category;
    }

    public void setCategory (String[] category)
    {
        this.category = category;
    }

    public String getTotalDuration ()
    {
        return totalDuration;
    }

    public void setTotalDuration (String totalDuration)
    {
        this.totalDuration = totalDuration;
    }

    public Tutor getTutor ()
    {
        return tutor;
    }

    public void setTutor (Tutor tutor)
    {
        this.tutor = tutor;
    }

    public Subscriber getSubscriber ()
    {
        return subscriber;
    }

    public void setSubscriber (Subscriber subscriber)
    {
        this.subscriber = subscriber;
    }

    public Video getVideo ()
    {
        return video;
    }

    public void setVideo (Video video)
    {
        this.video = video;
    }

    public Review getReview ()
    {
        return review;
    }

    public void setReview (Review review)
    {
        this.review = review;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subscriberCount = "+subscriberCount+", discussion = "+discussion+", classId = "+classId+", feedback = "+feedback+", project = "+project+", relatedClass = "+relatedClass+", title = "+title+", category = "+category+", totalDuration = "+totalDuration+", tutor = "+tutor+", subscriber = "+subscriber+", video = "+video+", review = "+review+"]";
    }
}