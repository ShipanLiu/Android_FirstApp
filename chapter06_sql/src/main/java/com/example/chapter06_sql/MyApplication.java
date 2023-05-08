package com.example.chapter06_sql;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.chapter06_sql.database.BookDatabase_Room;

import java.util.HashMap;

public class MyApplication extends Application {

    //声明一个 全局变量， 所有的 activity 都可以使用（Application 的声明周期）
    private static MyApplication mApp;
    // 声明一个公共的信息映射对象，可当作全局变量使用
    public HashMap<String, String> infoMap = new HashMap<>();

     // here we will use the ROOM, 来实例化 database
    private BookDatabase_Room bookDatabase;


    // 单例模式 返回 MyApplication
    public static MyApplication getInstance() {
        return mApp;
    }



    //   在App 启动时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        // 配合 单例模式 使用
        mApp = this;


        Log.d("My-Application", "onCreate of Application");

        // 在应用启动的时候， 构建一个database实例 + 设置 database 的名字为 book
        bookDatabase = Room.databaseBuilder(this, BookDatabase_Room.class, "book")
                // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库。如此一来原来的记录会丢失，故而要改为迁移方式以便保存原有记录）
                .addMigrations()
                // 允许在主线程中操作数据库（Room默认不能在主线程中操作数据库）
                .allowMainThreadQueries()
                .build();

    }

    // 你现在创建了一个 database 叫 “book”， 创建一个方法 返回  this database
    public BookDatabase_Room getBookDatebase() {
        return bookDatabase;
    }

















    // APP终止的时候(不要 用这个 onTerminate)

    @Override
    public void onTerminate() {
        Log.d("My-Application", "onTerminate of Application");
        super.onTerminate();
    }

    // 比如从横屏 到 竖屏
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
