package com.immymemine.kevin.skillshare.sampleModel;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class LessonsModel {

    public String order;
    public String content;
    public String duration;
    public int video;

    public LessonsModel(){

    }

    public LessonsModel(String order, String content, String duration, int video) {
        this.order = order;
        this.content = content;
        this.duration = duration;
        this.video = video;
    }
}
