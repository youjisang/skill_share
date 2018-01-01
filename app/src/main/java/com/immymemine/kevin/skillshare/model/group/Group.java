package com.immymemine.kevin.skillshare.model.group;

/**
 * Created by quf93 on 2017-12-12.
 */

public class Group {
    String _id;
    String groupName;
    String groupThumbnail;
    String memberCount;

    public Group(String _id, String groupName, String groupThumbnail, String memberCount) {
        this._id = _id;
        this.groupName = groupName;
        this.groupThumbnail = groupThumbnail;
        this.memberCount = memberCount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupThumbnail() {
        return groupThumbnail;
    }

    public void setGroupThumbnail(String groupThumbnail) {
        this.groupThumbnail = groupThumbnail;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(String memberCount) {
        this.memberCount = memberCount;
    }
}
