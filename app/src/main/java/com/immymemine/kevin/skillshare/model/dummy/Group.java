package com.immymemine.kevin.skillshare.model.dummy;

/**
 * Created by JisangYou on 2017-12-13.
 */

public class Group {


    String groupJoinNum;
    String groupName;
    String imageUrl;

    public Group(String groupJoinNum, String groupName, String imageUrl) {
        this.groupJoinNum = groupJoinNum;
        this.groupName = groupName;
        this.imageUrl = imageUrl;
    }

    public String getGroupJoinNum() {
        return groupJoinNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
