package com.example.chapter06_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class t02_SQLite extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_result;

    private String testDatabaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_sqlite);

        findViewById(R.id.btn_database_create).setOnClickListener(this);
        findViewById(R.id.btn_database_delete).setOnClickListener(this);
        tv_result = findViewById(R.id.tv_database_result);

        // 生成一个 数据库的完整路径
        testDatabaseName = getFilesDir() + "./test.db";

        // 使用 SQLiteHelper






    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_database_create:
                // 创建 or  打开数据库  数据库如果不存在就 创建 它， 如果存在就 打开它
                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(testDatabaseName, Context.MODE_PRIVATE, null);
                String desc = String.format("database[%s] creation with [%s]", testDatabaseName, (sqLiteDatabase != null) ? "success" : "failure");
                tv_result.setText(desc);
                break;
            case R.id.btn_database_delete:
                // 删除数据库
                boolean b = deleteDatabase(testDatabaseName);
                String desc1 = String.format("database[%s] deletion with [%s]",testDatabaseName,  b ? "success" : "failure");
                tv_result.setText(desc1);
                break;
        }
    }
}