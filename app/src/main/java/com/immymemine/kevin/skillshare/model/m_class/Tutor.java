package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-04.
 */

public class Tutor {
    String tutorId;
    String name;
    String followers;
    String imageUrl;

    // for test
    public Tutor(String tutorId,
            String name,
            String followers,
            String imageUrl) {
        this.tutorId = tutorId;
        this.name = name;
        this.followers = followers;
        this.imageUrl = imageUrl;
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
