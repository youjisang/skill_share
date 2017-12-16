package com.immymemine.kevin.skillshare.utility.communication_util;

import com.immymemine.kevin.skillshare.model.m_class.Reply;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * This Util is made for communicating Fragment and Activity
 * Created by quf93 on 2017-12-15.
 */

public class Bus {

    Subscription subscription;

    static Bus instance;
    public static Bus getInstance() {
        if(instance == null) {
            instance = new Bus();
        }
        return instance;
    }

//    private final ThreadLocal<ThreadLocalWrapper> threadLocal = new ThreadLocal<ThreadLocalWrapper>() {
//        @Override
//        protected ThreadLocalWrapper initialValue() {
//            return new ThreadLocalWrapper();
//        }
//    };

    public void register(Object subscriber) { // Bus.getInstance().register(this);
        Class<?> subscriberClass = subscriber.getClass();

        SubscriberMethod subscriberMethod = getMethod(subscriberClass);
        subscribe(subscriber, subscriberMethod);
    }

    private void subscribe(Object subscriber, SubscriberMethod subscriberMethod) {
        if(subscription == null) {
            subscription = new Subscription(subscriber, subscriberMethod);
        }
    }

    public void post(Map<Integer, List<Reply>> data) {
//        ThreadLocalWrapper wrapper = threadLocal.get();
//        wrapper.data = data;

        invokeSubscriber(subscription, data);
    }

    private void invokeSubscriber(Subscription subscription, Map<Integer, List<Reply>> data) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    static class ThreadLocalWrapper {
//        Map<Integer, List<Reply>> data;
//    }

    private SubscriberMethod getMethod(Class<?> subscriberClass) {
        for(Method method : subscriberClass.getMethods()) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);

            if(annotation != null) { // annotation null check
                Class<?>[] parameters = method.getParameterTypes();
                if(parameters.length == 1)
                    return new SubscriberMethod(method, parameters[0]); // method, parameter type
            }

        }

        return null;
    }
}
