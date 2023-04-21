package com.example.chapter05_shape.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        // 四舍五入
        return (int)(dpValue*scale + 0.5f);
    }

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    // hide keyboard
    public static void hideKeyboard(Activity act, View v) {
        // get InputMethodManager
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // close soft keyboard, 但是 要传递进去， 到是谁， 哪个View 让我 关闭这个 软键盘的。
        // v.getWindowToken() :
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }



}
