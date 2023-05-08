package com.example.chapter06_sql.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.chapter06_sql.dao.BookDao;
import com.example.chapter06_sql.entity.BookInfo;

/*
*
*
* BookDatebase_Room 提供 BookDao接口 的 实例（一般接口是 没有instance 的， 因此用 abstract， 目的就是 把
* BookDao 暴露出去），
*
* BookDao 提供 增删改查的 语句 支持
*
*
* */


/*
* entities 表示 该数据库谜面有那些表（table name 自动和 实体类 名字一致）， version表示 数据库的版本号
*exportSchema 表示 是否到处 数据库信息的json 串, 记录你用了那些语句，  假如为 true 的话， 需要 build.gradle 中设置。
* */
@Database(entities = {BookInfo.class}, version = 1, exportSchema = true)
public abstract class BookDatabase_Room extends RoomDatabase {

    // 获取该 databse 中 某个 table 的持久化对象, 这是一个 abstract 函数，
    // you can have anyname for this method
    public abstract BookDao bookDao();

}
