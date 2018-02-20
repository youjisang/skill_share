package com.immymemine.kevin.skillshare.utility.eventbusLibrary;

import com.immymemine.kevin.skillshare.model.group.Group;

/**
 * Created by JisangYou on 2018-02-09.
 */

public class dialogPushEvent {
    private String groupName;

    public dialogPushEvent(String groupName){
        this.groupName = groupName;
    }

    public String setName(){
        return groupName;
    }
}
