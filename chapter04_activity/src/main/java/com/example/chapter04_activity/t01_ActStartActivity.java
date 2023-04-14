package com.example.chapter04_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class t01_ActStartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "jier";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 研究生命周期，来打印一下
        Log.d(TAG, "t01_ActStartActivity: onCreate");
        setContentView(R.layout.t01_act_start);

        // get the btn
        findViewById(R.id.btn_act_next).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_act_next:
                Intent intent1 = new Intent(this, t02_ActEndActivity.class);
                // 就是 防止 重复返回的。[按下手机的 返回 键 会发生]
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);

                // 第二种方式： 可以指定 package， 而不只是 this
//                Intent intent = new Intent();
//                ComponentName component = new ComponentName(this, t02_ActEndActivity.class);
//                intent.setComponent(component);
//                startActivity(intent);

                break;
            case R.id.btn_login:
                Intent intent2 = new Intent(this, t02_ActEndActivity.class);
                // 按下 手机的 返回键， 不会退回到原来的  比如 验证码的页面， 会立刻退出app
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            default:
                break;
        }

    }

    // 现在研究生命周期：

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "t01_ActStartActivity: onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "t01_ActStartActivity: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "t01_ActStartActivity: onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "t01_ActStartActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "t01_ActStartActivity: onDestroy");
    }

    // 返老还童

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "t01_ActStartActivity: onRestart");
    }
}