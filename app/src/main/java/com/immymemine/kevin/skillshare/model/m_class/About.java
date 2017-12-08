package com.immymemine.kevin.skillshare.model.m_class;

import java.util.List;

/**
 * Created by quf93 on 2017-12-04.
 */

public class About {
    String projectSubscriberCount;
    List<Project> projects;

    Review review;
    Subscriber subscriber;
    List<RelatedClass> relatedClasses;

    public String getProjectSubscriberCount() {
        return projectSubscriberCount;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public void setProjectSubscriberCount(String projectSubscriberCount) {
        this.projectSubscriberCount = projectSubscriberCount;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<RelatedClass> getRelatedClasses() {
        return relatedClasses;
    }

    public void setRelatedClasses(List<RelatedClass> relatedClasses) {
        this.relatedClasses = relatedClasses;
    }
}
