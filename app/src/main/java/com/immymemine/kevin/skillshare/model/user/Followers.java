package com.immymemine.kevin.skillshare.model.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class Followers {
    private String userId;
    private String name;
    private String imageUrl;

    public Followers(String userId, String name, String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
