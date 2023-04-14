package com.example.chapter04_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class t02_ActEndActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_act_end);

        // button listener
        findViewById(R.id.back_img).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.back_img:
            case R.id.btn_finish:
                Intent intent = new Intent(this, t01_ActStartActivity.class);
                // 就是 防止 重复返回的
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                // 结束当前的活动页面
//                finish();
                break;
            default:
                break;
        }
    }
}