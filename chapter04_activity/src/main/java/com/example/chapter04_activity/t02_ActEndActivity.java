package com.example.chapter04_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.chapter04_activity.utils.Utils;

public class t02_ActEndActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String res = "this is a response";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_act_end);

        // textView show_request_id
        TextView textViewRequest = findViewById(R.id.show_request_id);
        // 从上一个 页面 传来的 intent 中 获取 包裹

        Bundle bundle = getIntent().getExtras();
        try{
            String requestTime = bundle.getString("request_time");
            String requestContent = bundle.getString("request_content");
            String desc = String.format("收到请求消息：\n请求时间为%s\n请求内容为%s", requestTime, requestContent);

            textViewRequest.setText(desc);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }


        // button listener
        findViewById(R.id.back_img).setOnClickListener(this);
        findViewById(R.id.btn_finish).setOnClickListener(this);

        //  button listener
        findViewById(R.id.btn_closePage).setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.back_img:
            case R.id.btn_finish:
                // 返回 上一个页面
                Intent intent = new Intent(this, t01_ActStartActivity.class);
                // 就是 防止 重复返回的
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                // 结束当前的活动页面
//                finish();
                break;

            case R.id.btn_closePage:
                // 发送 response 信息 回去
                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("response_time", Utils.getCurrentTime());
                bundle.putString("response_content", res);
                intent1.putExtras(bundle);
                // 携带意图返回上一个页面。RESULT_OK表示处理成功
                setResult(Activity.RESULT_OK, intent1);
                // 结束当前的活动页面， 自然就返回上一个页面了
                finish();

            default:
                break;
        }
    }
}