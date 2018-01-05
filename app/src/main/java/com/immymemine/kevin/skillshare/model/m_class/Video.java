package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-04.
 */

public class Video {
    String _id;
    String url;
    String title;
    String duration;
    String thumbnailUrl;
    String order;

    // for test
    public Video(String _id,
                 String url,
            String order,
            String title,
            String duration,
            String thumbnailUrl) {
        this._id = _id;
        this.url = url;
        this.order = order;
        this.title = title;
        this.duration = duration;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String get_id() {
        return _id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getOrder() {
        return order;
    }
}
