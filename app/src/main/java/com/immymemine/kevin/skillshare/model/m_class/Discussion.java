package com.immymemine.kevin.skillshare.model.m_class;

import java.util.List;

/**
 * Created by quf93 on 2017-12-02.
 */

public class Discussion {
    // TODO teacher 여부를 확인해야 함

    String _id;
    String name;
    String imageUrl;
    String content;
    String time; // Time Util 을 만들어서 String 으로 둔갑되어 있는 long 값을 파싱해서 18 minutes ago / 1 year ago ... 와 같이 표시해줘야 한다
    String likeCount;
    String userId;
    List<Reply> replies;

    // for test
    public Discussion(String _id,
            String name,
            String imageUrl,
            String content,
            String time,
            String likeCount,
            String userId,
            List<Reply> replies) {
        this._id = _id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.content = content;
        this.time = time;
        this.likeCount = likeCount;
        this.userId = userId;
        this.replies = replies;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public String getUserId() {
        return userId;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
