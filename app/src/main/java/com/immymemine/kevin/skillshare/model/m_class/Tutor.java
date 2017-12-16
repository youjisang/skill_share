package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-04.
 */

public class Tutor {
    String _id;
    String name;
    String followers;
    String imageUrl;

    // for test
    public Tutor(String _id,
            String name,
            String followers,
            String imageUrl) {
        this._id = _id;
        this.name = name;
        this.followers = followers;
        this.imageUrl = imageUrl;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
