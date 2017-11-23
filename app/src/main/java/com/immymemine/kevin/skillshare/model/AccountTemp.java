package com.immymemine.kevin.skillshare.model;

import android.net.Uri;

/**
 * Created by quf93 on 2017-11-22.
 */

public class AccountTemp {
    String name;
    String email;
    String id;
    Uri photo;

    public AccountTemp(String name, String email, String id, Uri photo) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public Uri getPhoto() {
        return photo;
    }
}
