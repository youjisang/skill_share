package com.immymemine.kevin.skillshare.utility.communication_util;

/**
 * Created by quf93 on 2017-12-15.
 */

public class Subscription {
    Object subscriber;
    SubscriberMethod subscriberMethod;

    public Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }
}
