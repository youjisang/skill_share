package com.immymemine.kevin.skillshare.model.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class Following {
    private String _id;
    private String userId;
    private String name;
    private String imageUrl;

    public String get_id() {
        return _id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName ()
    {
        return name;
    }

    public String getUserId ()
    {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Following) {
            return ((Following)obj).get_id().equals(this.get_id());
        }
        return super.equals(obj);
    }
}
