package com.immymemine.kevin.skillshare.model.m_class;

/**
 * Created by quf93 on 2017-12-05.
 */

public class Subscribers {
    String subscriberNumber;
    String[] pictureUrls;
    String[] ids;

    // for test
    public Subscribers(String subscriberNumber,
                       String[] pictureUrls, String[] ids) {
        this.subscriberNumber = subscriberNumber;
        this.pictureUrls = pictureUrls;
        this.ids = ids;
    }

    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    public String[] getIds() {
        return ids;
    }

    public String[] getPictureUrls() {
        return pictureUrls;
    }
}
