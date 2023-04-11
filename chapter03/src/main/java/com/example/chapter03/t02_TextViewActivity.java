package com.example.chapter03;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class t02_TextViewActivity extends AppCompatActivity {

    // 我must 重写 onCreate 方法


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        R 是 自动生成的， 通过 R 可以找到 layout 里面的 自己创建的 layout text_view
        setContentView(R.layout.t02_activity_text_view);

//        更改 文字的内容
        TextView textView = findViewById(R.id.tv_hello);
        textView.setText(R.string.hello_string2);
    }
}
