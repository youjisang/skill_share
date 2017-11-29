package com.immymemine.kevin.skillshare.utility;

import java.util.regex.Pattern;

/**
 * email / password validity check util
 * Created by quf93 on 2017-11-20.
 */

public class ValidationUtil {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_0-9a-zA-Z-]+@[0-9a-zA-Z-]+(.[_0-9a-zA-Z-]+)*$");
    private static final Pattern PW_REGEX = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{8,16}$"); // 8 - 16 자리

    public static boolean isEmpty(String any) {
        if(any == null || "".equals(any))
            return true;
        else
            return false;
    }

    public static boolean isValidEmailAddress(String email_address) {
        return EMAIL_REGEX.matcher(email_address).matches();
    }

    public static boolean isValidPassword(String password) {
        return PW_REGEX.matcher(password).matches();
    }
}
