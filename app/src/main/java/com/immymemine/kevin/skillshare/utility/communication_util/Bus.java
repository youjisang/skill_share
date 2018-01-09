package com.immymemine.kevin.skillshare.utility.communication_util;

import android.util.Log;

import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.model.m_class.Reply;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Util is made for communicating Fragment and Activity
 * Created by quf93 on 2017-12-15.
 */

public class Bus {

    Map<Class<?>, Subscription> subscriptionByType;

    static Bus instance;

    public static Bus getInstance() {
        if(instance == null) {
            instance = new Bus();
        }
        return instance;
    }

    private Bus() {
        subscriptionByType = new HashMap<>();
    }

    public void register(Object subscriber) { // Bus.getInstance().register(this);
        Class<?> subscriberClass = subscriber.getClass();

        SubscriberMethod subscriberMethod = getMethod(subscriberClass);
        subscribe(subscriber, subscriberMethod);
    }

    private void subscribe(Object subscriber, SubscriberMethod subscriberMethod) {
        if(subscriptionByType != null) {
            subscriptionByType.put(subscriberMethod.type, new Subscription(subscriber, subscriberMethod));
        }
    }

    public void post(Map<Integer, List<Reply>> data) {
        invokeSubscriber(subscriptionByType.get(java.util.Map.class), data);
    }

    private void invokeSubscriber(Subscription subscription, Map<Integer, List<Reply>> data) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO [ FIX ] 다형성을 가진 객체가 넘어올 때 TYPE 이 맞지 않아서 null 값이 전달된다
    public void post(List<Group> groups) {
        invokeSubscriber(subscriptionByType.get(java.util.List.class), groups);
    }

    private void invokeSubscriber(Subscription subscription, List<Group> groups) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, groups);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO module 화
    public void post(Object data) {
        invokeSubscriber(subscriptionByType.get(data.getClass()), data);
    }

    private void invokeSubscriber(Subscription subscription, Object data) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SubscriberMethod getMethod(Class<?> subscriberClass) {
        for(Method method : subscriberClass.getMethods()) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if(annotation != null) { // annotation null check
                Class<?>[] parameters = method.getParameterTypes();

                if(parameters.length == 1) {
                    return new SubscriberMethod(method, parameters[0]); // method, parameter type
                }
            }
        }

        return null;
    }
}
