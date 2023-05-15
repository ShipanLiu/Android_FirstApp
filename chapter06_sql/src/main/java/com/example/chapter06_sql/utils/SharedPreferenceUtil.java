package com.example.chapter06_sql.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private static SharedPreferenceUtil mUtil;

    private SharedPreferences preferences;

    public static SharedPreferenceUtil getInstance(Context context) {
        if(mUtil == null) {
            mUtil = new SharedPreferenceUtil();
            // 创建/得到 preferences
            mUtil.preferences = context.getSharedPreferences("shopping", Context.MODE_PRIVATE);
        }

        return mUtil;
    }

    /*
    *
    * for check, if this is the first time to open this app
    * */

    // provide fast method to support write and read
    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean readBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }
}
