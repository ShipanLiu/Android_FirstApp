package com.example.chapter06_sql.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAMW = "user.db";
    private static final int DB_VERSION = 1;

    public UserDBHelper(@Nullable Context context) {
        // 调用父类的构造方法。
        super(context,DB_NAMW, null, DB_VERSION);
    }


    // 实现 父类中的 abstarct method
    // 建立数据库 的时候， 需要执行的 建表语句
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
