package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-05.
 */

public class RelatedClass {
    String _id;
    String thumbnailUrl;
    String title;
    String tutorName;

    // for test
    public RelatedClass(String _id,
            String thumbnailUrl,
            String title,
            String tutorName) {
        this._id = _id;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.tutorName = tutorName;
    }

    public String get_id() {
        return _id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTutorName() {
        return tutorName;
    }
}
