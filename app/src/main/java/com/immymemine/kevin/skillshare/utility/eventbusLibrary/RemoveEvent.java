package com.immymemine.kevin.skillshare.utility.eventbusLibrary;

import com.immymemine.kevin.skillshare.model.m_class.Subscriber;

/**
 * Created by JisangYou on 2018-02-07.
 */

public class RemoveEvent {

    private Subscriber subscriber;

    public RemoveEvent(Subscriber subscribert) {
        this.subscriber = subscribert;
    }

    public Subscriber removeSubscriber() {
        return subscriber;
    }
}
