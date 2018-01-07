package com.immymemine.kevin.skillshare.network.user;

/**
 * Created by quf93 on 2017-11-29.
 */

public class SignUpRequestBody {
    private String email;
    private String password;
    private String name;
    private String photoUrl;

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }


}
