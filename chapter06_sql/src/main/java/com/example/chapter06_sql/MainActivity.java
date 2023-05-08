package com.example.chapter06_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/*
*
* 从 main activity开始出发， 关联到各个 页面
*
*
*
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences 存储 案例 1
        findViewById(R.id.btn_sharedPreferences1).setOnClickListener(this);
        findViewById(R.id.btn_sqlLite).setOnClickListener(this);
        findViewById(R.id.btn_phone_password).setOnClickListener(this);
        findViewById(R.id.btn_room).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_sharedPreferences1:
                Intent intent1 = new Intent(this, t01_SharedPreferences.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.btn_sqlLite:
                Intent intent2 = new Intent(this, t02_SQLite.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
            case R.id.btn_phone_password:
                Intent intent3 = new Intent(this, t03_PhonePassowrdUsingSQL.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                break;
            case R.id.btn_room:
                Intent intent4 = new Intent(this, t04_ROOM.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
                break;
        }
    }
}