package com.immymemine.kevin.skillshare.model.group;

/**
 * Created by quf93 on 2018-01-02.
 */

public class Comment {
    String userId;
    String userName;
    String userNickname;
    String imageUrl;
    String comment;
    String time;

    public Comment(String userId, String userName, String userNickname, String imageUrl, String comment, String time) {
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.imageUrl = imageUrl;
        this.comment = comment;
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }

}
