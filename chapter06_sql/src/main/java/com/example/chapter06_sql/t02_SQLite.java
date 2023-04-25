package com.example.chapter06_sql;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.chapter06_sql.database.UserDBHelper;
import com.example.chapter06_sql.entity.User;
import com.example.chapter06_sql.utils.Utils;

import java.util.List;

public class t02_SQLite extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private UserDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_sqlite);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        mHelper = UserDBHelper.getInstance(this);
        // 打开数据库帮助器的读写连接
        mHelper.openWriteLink();
        mHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();
        User user = null;
        switch (v.getId()) {
            case R.id.btn_save:
                // 以下声明一个用户信息对象，并填写它的各字段值
                user = new User(name,
                        Integer.parseInt(age),
                        Long.parseLong(height),
                        Float.parseFloat(weight),
                        ck_married.isChecked());
                if (mHelper.insert(user) > 0) {
                    Utils.showToast(this, "添加成功");
                }
                break;
            case R.id.btn_delete:
                if (mHelper.deleteByName(name) > 0) {
                    Utils.showToast(this, "删除成功");
                }
                break;
            case R.id.btn_update:
                user = new User(name,
                        Integer.parseInt(age),
                        Long.parseLong(height),
                        Float.parseFloat(weight),
                        ck_married.isChecked());
                if (mHelper.updateByName(user) > 0) {
                    Utils.showToast(this, "修改成功");
                } else {
                    Utils.showToast(this, "name不能被更改");
                }
                break;
            case R.id.btn_query:
                List<User> list = mHelper.queryAll();
                if(list.isEmpty()) {
                    Utils.showToast(this, "list 位空");
                } else {
                    //List<User> list = mHelper.queryByName(name);
                    for (User u : list) {
                        Log.d("ning", u.toString());
                    }
                }
                break;
        }
    }
}



//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.chapter06_sql.database.UserDBHelper;
//import com.example.chapter06_sql.entity.User;
//import com.example.chapter06_sql.utils.Utils;
//
//public class t02_SQLite extends AppCompatActivity implements View.OnClickListener {
//
//    private TextView tv_result;
//
//    private String testDatabaseName;
//    private EditText et_name;
//    private EditText et_age;
//    private EditText et_height;
//    private EditText et_weight;
//    private CheckBox ck_married;
//    private UserDBHelper mHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.t02_sqlite);
//
//        findViewById(R.id.btn_database_create).setOnClickListener(this);
//        findViewById(R.id.btn_database_delete).setOnClickListener(this);
//        tv_result = findViewById(R.id.tv_database_result);
//
//        // 生成一个 数据库的完整路径
//        testDatabaseName = getFilesDir() + "./test.db";
//
//        // 第二个例子， 增删改查
//        et_name = findViewById(R.id.et_name);
//        et_age = findViewById(R.id.et_age);
//        et_height = findViewById(R.id.et_height);
//        et_weight = findViewById(R.id.et_weight);
//        ck_married = findViewById(R.id.ck_married);
//
//        findViewById(R.id.btn_save).setOnClickListener(this);
//        findViewById(R.id.btn_delete).setOnClickListener(this);
//        findViewById(R.id.btn_update).setOnClickListener(this);
//        findViewById(R.id.btn_query).setOnClickListener(this);
//
//    }
//
//    // activity 的生命周期
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // 获得 helper 对象，
//        mHelper = UserDBHelper.getInstance(this);
//        //打开 write 和 read link
//        mHelper.openWriteLink();
//        mHelper.openReadLink();
//    }
//
//
//    // 退出到 后台了。
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        // 关闭数据库链接
//        mHelper.closeLink();
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        // 第二个案例，根据表格，把 user 构建出来。
//        String name = et_name.getText().toString();
//        String age = et_age.getText().toString();
//        String height = et_height.getText().toString();
//        String weight = et_weight.getText().toString();
//        User user = null;
//
//        switch(view.getId()) {
//            case R.id.btn_database_create:
//                // 创建 or  打开数据库  数据库如果不存在就 创建 它， 如果存在就 打开它
//                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(testDatabaseName, Context.MODE_PRIVATE, null);
//                String desc = String.format("database[%s] creation with [%s]", testDatabaseName, (sqLiteDatabase != null) ? "success" : "failure");
//                tv_result.setText(desc);
//                break;
//            case R.id.btn_database_delete:
//                // 删除数据库
//                boolean b = deleteDatabase(testDatabaseName);
//                String desc1 = String.format("database[%s] deletion with [%s]",testDatabaseName,  b ? "success" : "failure");
//                tv_result.setText(desc1);
//                break;
//
//            // 开始 第二个案例
//
//            // insert
//            case R.id.btn_save:
//                user = new User(name,
//                        Integer.parseInt(age),
//                        Long.parseLong(height),
//                        Float.parseFloat(weight),
//                        ck_married.isChecked());
//                if (mHelper.insert(user) > 0) {
//                    Utils.showToast(this, "添加成功");
//                }
//                break;
//
//
//        }
//    }
//}