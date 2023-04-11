package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chapter03.utils.Utils;

import org.w3c.dom.Text;

public class t11_button_style extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t11_button_style);
        // 之所以 在这里用 findViewById， 不是在 doClick 里面用，是因为， 否则每点一次 就会 调用一次
        // onCreate 这个函数的生命周期 只有一次
        textView = findViewById(R.id.tv_result);
        button = findViewById(R.id.btn_click_id1);
        myOnClick1(button);

        // 第二种方法， 不用那么麻烦， see t12

    }

    public void doClick(View view) {
        //得到一个格式化的字符串
        String desc = String.format("%s you have clicked: %s", Utils.getCurrentTime(), ((Button)view).getText());

        textView.setText(desc);
    }

    public void myOnClick1(Button btn) {
        // 点击按钮， 会自动调用  自定义类, 然后 会回调 里面的 onClock 函数
        btn.setOnClickListener(new MyOnClickListener(textView));
    }

    // 是用static 是为了防止 内存泄露  ， 这叫 内部类   https://blog.csdn.net/weixin_39631370/article/details/114852372
    // 内部类声明成静态的，就不能随便的访问外部类的成员变量了, 此时内部类只能访问外部类的静态成员变量
    static class MyOnClickListener implements View.OnClickListener {

        private final TextView textView;

        // 创建一个  constructor
        public MyOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View view) {
            String desc = String.format("%s you have clicked: %s", Utils.getCurrentTime(), ((Button)view).getText());

            textView.setText(desc);
        }
    }
}