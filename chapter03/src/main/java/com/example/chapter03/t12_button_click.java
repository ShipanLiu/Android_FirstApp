package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.utils.Utils;

public class t12_button_click extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t12_button_click);

        textView = findViewById(R.id.tv_result);

        Button button = findViewById(R.id.btn_click_id2);
        Button longClickBtn = findViewById(R.id.btn_long_click);
        Button btnEnable = findViewById(R.id.btn_enable_Id1);
        Button btnTarget = findViewById(R.id.testTargetBtn);


        button.setOnClickListener(this);
        // 使用 匿名内部类。
//        button.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View view) {
//                System.out.println("long clicked");
//                String desc = String.format("%s you have clicked: %s", Utils.getCurrentTime(), ((Button)view).getText());
//                textView.setText(desc);
//                return true;
//            }
//        });

        // 使用 lamda 表达式
        longClickBtn.setOnLongClickListener(view -> {
            System.out.println("long clicked");
            String desc = String.format("%s you have clicked: %s", Utils.getCurrentTime(), ((Button)view).getText());

            textView.setText(desc);
            return true;
        } );

        // 此方法 也可以定义在  下面的 onClick 里面
        btnEnable.setOnClickListener(v -> {
            btnTarget.setEnabled(false);
            btnTarget.setBackgroundColor(Color.GRAY);
        });

    }

    @Override
    public void onClick(View view) {
        // 如何区分那个按钮按下了呢？
        if(view.getId() == R.id.btn_click_id2) {

            System.out.println("short clicked");
            //得到一个格式化的字符串
            String desc = String.format("%s you have clicked: %s", Utils.getCurrentTime(), ((Button)view).getText());

            textView.setText(desc);
        }

    }

}