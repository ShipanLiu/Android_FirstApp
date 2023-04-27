package com.example.chapter05_shape.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
        if(v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

    }

    public static String create6RandomSmsCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public static void showToast(Context ctx, String desc) {
        Toast.makeText(ctx, desc, Toast.LENGTH_SHORT).show();
    }



}
