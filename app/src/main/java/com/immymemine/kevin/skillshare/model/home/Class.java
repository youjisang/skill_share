package com.immymemine.kevin.skillshare.model.home;

/**
 * Created by quf93 on 2017-12-05.
 */

public class Class {
    String _id;
    String title;
    String imageUrl;
    String tutorName;
    String duration;

    // for testing
    public Class(String _id,
            String title,
            String imageUrl,
            String tutorName,
            String duration) {
        this._id = _id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.tutorName = tutorName;
        this.duration = duration;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getDuration() {
        return duration;
    }
}
