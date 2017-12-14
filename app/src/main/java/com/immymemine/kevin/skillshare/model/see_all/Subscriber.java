package com.immymemine.kevin.skillshare.model.see_all;

/**
 * Created by quf93 on 2017-12-09.
 */

public class Subscriber extends SeeAll{
    String _id;
    String name;
    String pictureUrl;

    public Subscriber(String _id, String name, String pictureUrl) {
        this._id = _id;
        this.name = name;
        this.pictureUrl = pictureUrl;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
