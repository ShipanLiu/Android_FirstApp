package com.example.chapter06_sql.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chapter06_sql.entity.LoginInfoEntity;

public class DBHelper2_PwdLogin extends SQLiteOpenHelper {

    private static final String DB_NAME = "login.db";
    private static final String TABLE_NAME = "login_info";
    private static final int DB_VERSION = 1;
    private static DBHelper2_PwdLogin mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private DBHelper2_PwdLogin(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static DBHelper2_PwdLogin getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new DBHelper2_PwdLogin(context);
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
                " phone VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                " remember INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 我不管 你phone在 数据库里面有没有， 假如有的话， 我先删掉， 在 insert新的 tuple
    public void save(LoginInfoEntity info) {
        // 如果存在则先删除，再添加
        try {
            mWDB.beginTransaction();
            // 假如 save 的密码已经存在， 为了更新， 可以使用update， 但是 delete + insert 也可以更新密码。
            delete(info);
            insert(info);
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }

    public long delete(LoginInfoEntity info) {
        return mWDB.delete(TABLE_NAME, "phone=?", new String[]{info.phone});
    }

    public long insert(LoginInfoEntity info) {
        ContentValues values = new ContentValues();
        values.put("phone", info.phone);
        values.put("password", info.password);
        values.put("remember", info.remember);
        return mWDB.insert(TABLE_NAME, null, values);
    }

    /* 读取 最后一条*/

    /*
    * In SQLite, "_id" is a reserved column name that is commonly used as the primary key for a table. This reserved column is automatically created when you define a table with the "INTEGER PRIMARY KEY" column constraint.
    *    "_id" is used instead of "id" to avoid conflicts with any "id" column that may exist in other tables in the same database. It also allows for easy integration with the Android platform, where "_id" is the standard primary key for database tables used in Android apps.
    *
    *
    * */
    public LoginInfoEntity queryTop() {
        LoginInfoEntity info = null;
        String sql = "select * from " + TABLE_NAME + " where remember = 1 ORDER BY _id DESC limit 1";
        // 执行记录查询动作，该语句返回结果集的游标
        // rawQuery 接受 自己拼接的一个 sql 语句
        Cursor cursor = mRDB.rawQuery(sql, null);
        // 因为只需要一条数据， 所以不用while， 用 if
        if (cursor.moveToNext()) {
            info = new LoginInfoEntity();
            info.id = cursor.getInt(0);
            info.phone = cursor.getString(1);
            info.password = cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false : true;
        }
        return info;
    }

    public LoginInfoEntity queryByPhone(String phone) {
        LoginInfoEntity info = null;
        // 第一种方法， 使用 raw sql 自拼语句
        //  数据库里面， 有些 phone 倍保存了， 但是 remember 是 0， 所以 不查出来
        String sql = "select * from " + TABLE_NAME + " where remember = 1 and phone=?";
        Cursor cursor = mRDB.rawQuery(sql, new String[]{phone});
        // 执行记录查询动作，该语句返回结果集的游标
//        Cursor cursor = mRDB.query(TABLE_NAME, null, "phone=? and remember=1", new String[]{phone}, null, null, null);
        if (cursor.moveToNext()) {
            info = new LoginInfoEntity();
            info.id = cursor.getInt(0);
            info.phone = cursor.getString(1);
            info.password = cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false : true;
        }
        return info;
    }
}

