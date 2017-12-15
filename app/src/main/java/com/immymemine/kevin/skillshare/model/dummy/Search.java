package com.immymemine.kevin.skillshare.model.dummy;

/**
 * Created by JisangYou on 2017-12-12.
 */

public class Search {

    String title;
    String tutor;
    String imageUrl;
    String duration;
    String thumbup;
    String attendanceNum;


    public Search(String title, String tutor, String imageUrl, String duration, String thumbup, String attendanceNum) {
        this.title = title;
        this.tutor = tutor;
        this.imageUrl = imageUrl;
        this.duration = duration;
        this.thumbup = thumbup;
        this.attendanceNum = attendanceNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getThumbup() {
        return thumbup;
    }

    public void setThumbup(String thumbup) {
        this.thumbup = thumbup;
    }

    public String getAttendanceNum() {
        return attendanceNum;
    }

    public void setAttendanceNum(String attendanceNum) {
        this.attendanceNum = attendanceNum;
    }
}
