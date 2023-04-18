package com.example.chapter04_activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter04_activity.utils.Utils;

public class t01_ActStartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "jier";
    private static final String req = "this is a request";
    private ActivityResultLauncher<Intent> register;
    private TextView textViewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 研究生命周期，来打印一下
        Log.d(TAG, "t01_ActStartActivity: onCreate");
        setContentView(R.layout.t01_act_start);

         // get the textview of "show_response_id"
        textViewResponse = findViewById(R.id.show_response_id);

        // get the btn
        findViewById(R.id.btn_act_next).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);



        // 得到的 response 回来之后：
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),  result -> {
            if (result != null) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                    String response_time = bundle.getString("response_time");
                    String response_content = bundle.getString("response_content");
                    String desc = String.format("收到返回消息：\n应答时间为%s\n应答内容为%s", response_time, response_content);
                    // 把返回消息的详情显示在文本视图上
                    textViewResponse.setText(desc);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_act_next:
                Intent intent1 = new Intent(this, t02_ActEndActivity.class);
                // 就是 防止 重复返回的。[按下手机的 返回 键 会发生]
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // 创建一个 包裹
                Bundle bundle = new Bundle();
                bundle.putString("request_time", Utils.getCurrentTime());
                bundle.putString("request_content", req);
                intent1.putExtras(bundle);

                register.launch(intent1);




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