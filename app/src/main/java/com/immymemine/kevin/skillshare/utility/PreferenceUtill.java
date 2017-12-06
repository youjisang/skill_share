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

    /* TODO 지상
     이 유틸은 자동로그인, 셀렉트 선택시 나갔다 들어올경우, 동영상 보다가 전화가 올경우나 앱을 나갔다가 경우 등
     서버에서 굳이 디비를 받아올 필요가 없는 작은 데이터들을 저장하기 위한 것
     아래 코드는 작업중....ㅜ
     */

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
