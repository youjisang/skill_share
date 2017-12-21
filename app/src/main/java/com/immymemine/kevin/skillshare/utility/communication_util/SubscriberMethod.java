package com.immymemine.kevin.skillshare.utility.communication_util;

import java.lang.reflect.Method;

/**
 * Created by quf93 on 2017-12-15.
 */

public class SubscriberMethod {
    Method method;
    Class<?> type;

    public SubscriberMethod(Method method, Class<?> type) {
        this.method = method;
        this.type = type;
    }
}
