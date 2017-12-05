package com.immymemine.kevin.skillshare.utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by JisangYou on 2017-12-02.
 */

public class PreferenceUtill {

    private static final String filename = "skillshareSharedPreference";

    private static SharedPreferences getPreference(Context context){
        return context.getSharedPreferences(filename,Context.MODE_PRIVATE);
    }

    public static void setValue(Context context, String key, ArrayList<String> value){
        SharedPreferences.Editor editor = getPreference(context).edit();
        Set<String> set = new HashSet<String>();
        set.addAll(value);
        editor.putStringSet(key, set);
        editor.apply();
//        editor.commit();
    }

    public static ArrayList<String> getString(Context context, String key){
        return (ArrayList<String>) getPreference(context).getStringSet("",null);
    }

}
