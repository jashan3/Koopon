package com.han.koopon.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class PFUtil {

    public final static String AUTO_LOGIN_ID = "AUTO_LOGIN_ID";

    public static void savePreference (Context context,String key,String value){
        SharedPreferences pref = context.getSharedPreferences("koopon",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String getPreferenceString (Context context,String key){
        SharedPreferences pref = context.getSharedPreferences("koopon",context.MODE_PRIVATE);
        return pref.getString(key,"");
    }
}
