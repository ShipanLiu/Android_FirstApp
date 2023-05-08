package com.example.chapter06_sql.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chapter06_sql.entity.BookInfo;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(BookInfo... book);

    @Delete
    void delete(BookInfo... book);

    // 删除所有书籍信息
    // 清空表
    @Query("DELETE FROM BookInfo")
    void deleteAll();

    @Update
    int update(BookInfo... book);

    // 加载所有书籍信息, BookInfo 要和 类名一致
    @Query("SELECT * FROM BookInfo")
    List<BookInfo> queryAll();

    // 根据名字加载书籍
    // :bookName 就是 queryByName(String bookName) 里面的参数
    // 可能有 重名的， 所以 ORDER BY id DESC limit 1， 就是 查询第一条（就是最新添加的 最后一条）
    @Query("SELECT * FROM BookInfo WHERE name = :bookName ORDER BY id DESC limit 1")
    BookInfo queryByName(String bookName);

}
