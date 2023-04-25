package com.example.chapter06_sql.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.WindowAnimationFrameStats;


import com.example.chapter06_sql.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";
    private static final int DB_VERSION = 2;
    private static UserDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static UserDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " age INTEGER NOT NULL," +
                " height LONG NOT NULL," +
                " weight FLOAT NOT NULL," +
                " married INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    // 我想升级这个 数据库, 增加字段（由于功能扩展的需要）  一般不会用到。
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN phone VARCHAR;";
        db.execSQL(sql);
        sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN password VARCHAR;";
        db.execSQL(sql);
    }

    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.name);
        values.put("age", user.age);
        values.put("height", user.height);
        values.put("weight", user.weight);
        values.put("married", user.married);
        // 执行插入记录动作，该语句返回插入记录的行号
        // 如果第三个参数values 为Null或者元素个数为0， 由于insert()方法要求必须添加一条除了主键之外其它字段为Null值的记录，
        // 为了满足SQL语法的需要， insert语句必须给定一个字段名 ，如：insert into person(name) values(NULL)，
        // 倘若不给定字段名 ， insert语句就成了这样： insert into person() values()，显然这不满足标准SQL的语法。
        // 如果第三个参数values 不为Null并且元素的个数大于0 ，可以把第二个参数设置为null 。
        //return mWDB.insert(TABLE_NAME, null, values);

        try {
            // 开启事务
            mWDB.beginTransaction();
//            mWDB.insert(TABLE_NAME, null, values);
            //int i = 10 / 0;
            mWDB.insert(TABLE_NAME, null, values);

            // 假如上面的 insert 执行成功， 就会执行这行 代码， 否则不执行
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭事务
            mWDB.endTransaction();
        }

        return 1;
    }

    public long deleteAll(String name) {
        //删除所有
        return mWDB.delete(TABLE_NAME, "1=1", null);
    }

    public long deleteByName(String name) {

        // ?是个占位符，
        return mWDB.delete(TABLE_NAME, "name=?", new String[]{name});
    }

    public long deleteByNameAndAge(String name, String age) {
        //删除所有
        //mWDB.delete(TABLE_NAME, "1=1", null);
        // ?是个占位符，new String[]{name, age} 是一个数组。
        return mWDB.delete(TABLE_NAME, "name=? and age=?", new String[]{name, age});
    }


    // 修改的话， 任何字段都可以被修改， 所以 传一个 user对象
    public long updateByName(User user) {
        ContentValues values = new ContentValues();
        values.put("name", user.name);
        values.put("age", user.age);
        values.put("height", user.height);
        values.put("weight", user.weight);
        values.put("married", user.married);
        return mWDB.update(TABLE_NAME, values, "name=?", new String[]{user.name});
    }

    public List<User> queryAll() {
        List<User> list = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null, null, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);
            //SQLite没有布尔型，用0表示false，用1表示true
            user.married = (cursor.getInt(5) == 0) ? false : true;
            list.add(user);
        }
        return list;
    }

    public List<User> queryByName(String name) {
        List<User> list = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, "name=?", new String[]{name}, null, null, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            User user = new User();
            user.id = cursor.getInt(0);
            user.name = cursor.getString(1);
            user.age = cursor.getInt(2);
            user.height = cursor.getLong(3);
            user.weight = cursor.getFloat(4);
            //SQLite没有布尔型，用0表示false，用1表示true
            user.married = (cursor.getInt(5) == 0) ? false : true;
            list.add(user);
        }
        return list;
    }
}







//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//import com.example.chapter06_sql.entity.User;
//import com.example.chapter06_sql.utils.Utils;
//
//public class UserDBHelper extends SQLiteOpenHelper {
//
//    private static final String DB_NAME = "user1.db";
//    private static final String TABLE_NAME = "user_info";
//    private static final int DB_VERSION = 1;
//    private static UserDBHelper mHelper = null;
//
//    //  一个是 write， 一个负责 read
//    private SQLiteDatabase mRDB = null;
//    private SQLiteDatabase mWDB = null;
//
//
//
//    public UserDBHelper(@Nullable Context context) {
//        // 调用父类的构造方法。
//        super(context,DB_NAME, null, DB_VERSION);
//    }
//
//
//    // 利用单例模式 获取 数据库帮助器 的唯一instance
//    public static UserDBHelper getInstance(Context context) {
//        if(mHelper == null) {
//            mHelper = new UserDBHelper(context);
//        }
//        return mHelper;
//    }
//
//    //打开数据库的 读链接
//    public SQLiteDatabase openReadLink() {
//        if(mRDB == null || !mRDB.isOpen()) {
//            mRDB = mHelper.getReadableDatabase();
//        }
//
//        return mRDB;
//    }
//
//    //打开数据库的 写链接
//    public SQLiteDatabase openWriteLink() {
//        if(mWDB == null || !mWDB.isOpen()) {
//            mWDB = mHelper.getReadableDatabase();
//        }
//        return mWDB;
//    }
//
//    // 关闭数据库链接
//    public void closeLink() {
//        if(mRDB != null && mRDB.isOpen()) {
//            mRDB.close();
//            mRDB = null;
//        }
//
//        if(mWDB != null && mWDB.isOpen()) {
//            mWDB.close();
//            mWDB = null;
//        }
//    }
//
//
//
//
//
//
//
//    // 实现 父类中的 abstarct method
//    // 建立数据库 的时候， 需要执行的 建表语句
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
//                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
//                " name VARCHAR NOT NULL," +
//                " age INTEGER NOT NULL," +
//                " height LONG NOT NULL," +
//                " weight FLOAT NOT NULL," +
//                " married INTEGER NOT NULL);";
//        db.execSQL(sql);
//    }
//
//
//
//    // 在 version 升级的时候。
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//
//    // 下面就是 对数据的 操作。
//
//    public long insert(User user) {
//        ContentValues values = new ContentValues();
//        values.put("name", user.name);
//        values.put("age", user.age);
//        values.put("height", user.height);
//        values.put("weight", user.weight);
//        values.put("married", user.married);
//        // 执行插入记录动作，该语句返回插入记录的行号
//        // 如果第三个参数values 为Null或者元素个数为0， 由于insert()方法要求必须添加一条除了主键之外其它字段为Null值的记录，
//        // 为了满足SQL语法的需要， insert语句必须给定一个字段名 ，如：insert into person(name) values(NULL)，
//        // 倘若不给定字段名 ， insert语句就成了这样： insert into person() values()，显然这不满足标准SQL的语法。
//        // 如果第三个参数values 不为Null并且元素的个数大于0 ，可以把第二个参数设置为null 。
//        //return mWDB.insert(TABLE_NAME, null, values);
//
//        try {
//            mWDB.beginTransaction();
//            mWDB.insert(TABLE_NAME, null, values);
//            //int i = 10 / 0;
//            mWDB.insert(TABLE_NAME, null, values);
//            mWDB.setTransactionSuccessful();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            mWDB.endTransaction();
//        }
//
//        return 1;
//    }
//
//
//
//}
