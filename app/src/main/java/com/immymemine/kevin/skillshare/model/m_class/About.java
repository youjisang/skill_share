package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-04.
 */

public class About {
    String projectSubscriberCount;
    Project[] projects;

    Review review;
    Subscriber subscriber;
    RelatedClass[] relatedClasses;

    public String getProjectSubscriberCount() {
        return projectSubscriberCount;
    }

    public Project[] getProjects() {
        return projects;
    }

    public void setProjects(Project[] projects) {
        this.projects = projects;
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

    public RelatedClass[] getRelatedClasses() {
        return relatedClasses;
    }

    public void setRelatedClasses(RelatedClass[] relatedClasses) {
        this.relatedClasses = relatedClasses;
    }
}
