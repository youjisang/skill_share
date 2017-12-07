package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-02.
 */

public class Discussion {
    private String _id;
    private String name;
    private String pictureUrl;
    private String content;
    private String time;
    private int like;
    private ReDiscussion[] reDiscussions;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setReDiscussions(ReDiscussion[] reDiscussions) {
        this.reDiscussions = reDiscussions;
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
