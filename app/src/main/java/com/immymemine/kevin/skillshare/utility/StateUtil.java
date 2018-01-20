package com.immymemine.kevin.skillshare.utility;

import android.util.Log;

import com.immymemine.kevin.skillshare.model.user.User;

/**
 * Created by quf93 on 2017-12-21.
 */

public class StateUtil {

    private static User user;
    private static StateUtil stateUtil;

    // singleton
    private StateUtil() {
        user = new User();
    }

    public static StateUtil getInstance() {
        if(stateUtil == null) {
            stateUtil = new StateUtil();
        }
        return stateUtil;
    }

    // get sign in state
    private static boolean state;

    public boolean getState() {
        return state;
    }

    private void setState(boolean isSignIn) {
        state = isSignIn;
    }

    public void setUserInstance(User user) {
        if(user == null) {
            Log.d("JUWONLEE", "set user null");
            this.user = null;
            setState(false);
        } else {
            Log.d("JUWONLEE", "set user not null");
            this.user = user;
            setState(true);
        }
    }

    // get user state
    public User getUserInstance() {
        return user;
    }
}
