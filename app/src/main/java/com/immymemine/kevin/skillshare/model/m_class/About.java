package com.immymemine.kevin.skillshare.model.m_class;

import java.util.List;

/**
 * Created by quf93 on 2017-12-04.
 */

public class About {
    String projectSubscriberCount;
    List<Project> projects;

    Reviews reviews;
    Subscribers subscriber;
    List<RelatedClass> relatedClasses;

    public String getProjectSubscriberCount() {
        return projectSubscriberCount;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public Subscribers getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscribers subscriber) {
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
