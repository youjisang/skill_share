package com.immymemine.kevin.skillshare.utility.eventbusLibrary;
import com.immymemine.kevin.skillshare.model.m_class.Subscriber;


/**
 * Created by JisangYou on 2018-02-06.
 */

public class PushEvent {

    private Subscriber subscriber;


    public PushEvent(Subscriber subscribert) {
        this.subscriber = subscribert;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }


}
