package com.immymemine.kevin.skillshare.model.dummy;

/**
 * Created by JisangYou on 2017-12-16.
 */

public class Saved {

    String title;
    String tutorName;
    String profileImage;
    String duration;
    String thumbPercent;
    String attendanceNum;

    public Saved(String title, String tutorName, String profileImage, String duration, String thumbPercent, String attendanceNum) {
        this.title = title;
        this.tutorName = tutorName;
        this.profileImage = profileImage;
        this.duration = duration;
        this.thumbPercent = thumbPercent;
        this.attendanceNum = attendanceNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getThumbPercent() {
        return thumbPercent;
    }

    public void setThumbPercent(String thumbPercent) {
        this.thumbPercent = thumbPercent;
    }

    public String getAttendanceNum() {
        return attendanceNum;
    }

    public void setAttendanceNum(String attendanceNum) {
        this.attendanceNum = attendanceNum;
    }
}
