package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class t04_testColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t04_activity_test_color);

        // 在这里 来设置 Text color
        TextView textView = findViewById(R.id.testTextColorId);
        textView.setTextColor(Color.BLUE);

        // set background
    }
}