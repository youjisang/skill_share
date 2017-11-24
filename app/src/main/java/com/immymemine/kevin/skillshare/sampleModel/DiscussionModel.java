package com.immymemine.kevin.skillshare.sampleModel;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class DiscussionModel {

    public String profile;
    public String userName;
    public String content;
    public String reply;
    public String date;
    public String likeNum;
    public String heart;


    private DiscussionModel() {

    }

    public DiscussionModel(String profile, String userName, String content, String reply, String date, String likeNum, String heart) {
        this.profile = profile;
        this.userName = userName;
        this.content = content;
        this.reply = reply;
        this.date = date;
        this.likeNum = likeNum;
        this.heart = heart;
    }


}
