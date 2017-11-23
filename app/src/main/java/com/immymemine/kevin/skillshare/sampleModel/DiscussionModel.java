package com.immymemine.kevin.skillshare.sampleModel;

/**
 * Created by JisangYou on 2017-11-22.
 */

public class DiscussionModel {

    public int profile;
    public String userName;
    public String content;
    public String reply;
    public String date;
    public String likeNum;
    public int heart;


    private DiscussionModel() {

    }

    public DiscussionModel(int profile, String userName, String content, String reply, String date, String likeNum, int heart) {
        this.profile = profile;
        this.userName = userName;
        this.content = content;
        this.reply = reply;
        this.date = date;
        this.likeNum = likeNum;
        this.heart = heart;
    }


}
