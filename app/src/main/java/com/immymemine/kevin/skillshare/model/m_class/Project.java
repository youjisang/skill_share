package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-05.
 */

public class Project {
    String _id;
    String pictureUrl;

    public Project(String _id, String pictureUrl) {
        this._id = _id;
        this.pictureUrl = pictureUrl;
    }

    public String get_id() {
        return _id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
