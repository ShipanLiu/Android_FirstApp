package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chapter03.utils.Utils;

public class t05_ViewBorderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t05_activity_view_border);

        TextView textView = findViewById(R.id.setViewSizeId);
        /* layoutParams 里面就有 width 和 height 的信息*/
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        // 在修改宽度的时候， 默认的是 px， 因此需要把 dp to px
        layoutParams.width = Utils.dip2px(this, 300);
        textView.setLayoutParams(layoutParams);

    }
}