package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-02.
 */

public class Discussion {
    // TODO teacher 여부를 확인해야 함
    // TODO data 를 가져오는 순서가 중요함
    // TODO time 과 rediscussions 처리해야함
    String _id;
    String name;
    String pictureUrl;
    String content;
    String time;
    int like;
    ReDiscussion[] reDiscussions;

    // for test
    public Discussion(String _id,
            String name,
            String pictureUrl,
            String content,
            String time,
            int like,
            ReDiscussion[] reDiscussions) {
        this._id = _id;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.content = content;
        this.time = time;
        this.like = like;
        this.reDiscussions = reDiscussions;
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

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getLike() {
        return like;
    }

    public ReDiscussion[] getReDiscussions() {
        return reDiscussions;
    }
}
