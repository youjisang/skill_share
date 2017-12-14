package com.immymemine.kevin.skillshare.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by super on 2017-12-12.
 */

public class GroupItem {

    private String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public GroupItem(String item) {
        this.item=item;
    }

}
