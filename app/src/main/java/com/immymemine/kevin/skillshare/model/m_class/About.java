package com.immymemine.kevin.skillshare.model.m_class;


import java.util.List;

/**
 * Created by quf93 on 2017-12-04.
 */

public class About {
    List<Project> projects;
    List<Review> reviews;
    List<Subscriber> subscribers;
    List<RelatedClass> relatedClasses;

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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }
}
