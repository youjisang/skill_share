package com.immymemine.kevin.skillshare.model.dummy;

/**
 * Created by JisangYou on 2017-12-13.
 */

public class dummyDataForGroup {


    String groupJoinNum;
    String groupName;
    String groupImage;

    public dummyDataForGroup(String groupJoinNum, String groupName, String groupImage) {

        this.groupJoinNum = groupJoinNum;
        this.groupName = groupName;
        this.groupImage = groupImage;
    }



    public String getGroupJoinNum() {
        return groupJoinNum;
    }

    public void setGroupJoinNum(String groupJoinNum) {
        this.groupJoinNum = groupJoinNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }
}
