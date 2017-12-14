package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-03.
 */

public class Reply {
    String name;
    String pictureUrl;
    String content;
    String time;

    // test

    public Reply(String name,
            String pictureUrl,
            String content,
            String time) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.content = content;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
