package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class t03_TextSizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t03_activity_text_size);

        // set the text size
        TextView textView = findViewById(R.id.tv_hello);
        textView.setTextSize(30);
    }
}