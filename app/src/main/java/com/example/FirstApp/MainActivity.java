package com.example.FirstApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        这个 java 文件来连接 一个 xml 文件， 取得控制的功能。 R 是 gradle 自动生成的
        setContentView(R.layout.activity_main);
        // gte the text view via id
        TextView tv = findViewById(R.id.tv);
        // change the text content
        tv.setText("卧槽");

        // button 的 click 事件
        Button button = findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                先 declare 一个意图
                Intent intent = new Intent();
                // 设置 要跳转到 MainActivity2, MainActivity.this 代表 class MainActivity
                // 意图 里面 设置了一个上下文。
                intent.setClass(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });




    }
}