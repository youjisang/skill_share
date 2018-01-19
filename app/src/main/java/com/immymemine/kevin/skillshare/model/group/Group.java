package com.immymemine.kevin.skillshare.model.group;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by quf93 on 2017-12-12.
 */

public class Group implements Parcelable {
    String _id;
    String groupId;
    String groupName;
    String groupThumbnail;
    String memberCount;
    List<String> memberNicknames;

    protected Group(Parcel in) {
        _id = in.readString();
        groupId = in.readString();
        groupName = in.readString();
        groupThumbnail = in.readString();
        memberCount = in.readString();
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupThumbnail() {
        return groupThumbnail;
    }

    public String getMemberCount() {
        return memberCount;
    }

    public List<String> getMemberNicknames() {
        return memberNicknames;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(groupId);
        dest.writeString(groupName);
        dest.writeString(groupThumbnail);
        dest.writeString(memberCount);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Group) {
            return this.groupName.equals( ((Group)obj).getGroupName() );
        }
        return super.equals(obj);
    }
}
