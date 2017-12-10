package com.immymemine.kevin.skillshare.model.see_all;

/**
 * Created by quf93 on 2017-12-10.
 */

public class Project {
    String _id;
    String projectPictureUrl;
    String projectLikeCount;
    String projectTitle;
    String subscriberName;

    public Project(String _id, String projectPictureUrl, String projectLikeCount, String projectTitle, String subscriberName) {
        this._id = _id;
        this.projectPictureUrl = projectPictureUrl;
        this.projectLikeCount = projectLikeCount;
        this.projectTitle = projectTitle;
        this.subscriberName = subscriberName;
    }

    public String get_id() {
        return _id;
    }

    public String getProjectPictureUrl() {
        return projectPictureUrl;
    }

    public String getProjectLikeCount() {
        return projectLikeCount;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getSubscriberName() {
        return subscriberName;
    }
}
