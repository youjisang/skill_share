package com.immymemine.kevin.skillshare.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JisangYou on 2017-12-02.
 */

public class PreferenceUtil {

    private static final String filename = "skill_share_pref";

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
    }

    public static void setBooleanValue(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
        //editor.remove(key); 삭제하기
    }

    public static void setStringValue(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.clear();
        editor.putString(key, value);
        editor.commit();
    }

    public static Boolean getBooleanValue(Context context, String key) {
        return getPreference(context).getBoolean(key,false);
    }

    public static String getStringValue(Context context, String key) {
        return getPreference(context).getString(key,"");
    }
}
