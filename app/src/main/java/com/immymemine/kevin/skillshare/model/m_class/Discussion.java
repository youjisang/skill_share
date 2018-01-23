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
    String time;
    String likeCount;
    String userId;
    String resId;
    List<String> likeUsersIds;
    List<Reply> replies;

    // for test
    public Discussion(String _id,
            String name,
            String imageUrl,
            String content,
            String time,
            String likeCount,
            String userId,
            String resId,
            List<String> likeUsersIds,
            List<Reply> replies) {
        this._id = _id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.content = content;
        this.time = time;
        this.likeCount = likeCount;
        this.userId = userId;
        this.resId = resId;
        this.likeUsersIds = likeUsersIds;
        this.replies = replies;
    }

    public Discussion(String name, String imageUrl, String content, String time, String likeCount, String userId, String resId, List<String> likeUsersIds, List<Reply> replies) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.content = content;
        this.time = time;
        this.likeCount = likeCount;
        this.userId = userId;
        this.resId = resId;
        this.likeUsersIds = likeUsersIds;
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

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getLikeUsersIds() {
        return likeUsersIds;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public String getResId() {
        return resId;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }
}
