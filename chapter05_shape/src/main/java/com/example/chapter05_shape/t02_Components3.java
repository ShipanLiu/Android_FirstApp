package com.example.chapter05_shape;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.chapter05_shape.utils.Utils;

public class t02_Components3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Spinner dropdown
    private Spinner spinner_dropdown;
    // 定义 spinner 下拉里面的内容
    private final static String[] starArray = {"水星", "金星", "地球", "火星", "木星", "土星"};

    // SpinnerDialog
    private Spinner sp_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_components3);

        // Spinner dropdown
        Spinner spinner_dropdown = findViewById(R.id.sp_dropdown1);
        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this, R.layout.item_select, starArray);
        spinner_dropdown.setAdapter(startAdapter);
        // 设置下拉框默认显示第一项
        spinner_dropdown.setSelection(0);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        spinner_dropdown.setOnItemSelectedListener(this);



        // SpinnerDialog
        sp_dialog = findViewById(R.id.sp_dialog1);
        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> startAdapter1 = new ArrayAdapter<>(this, R.layout.item_select, starArray);
        // 设置下拉框的标题。对话框模式才显示标题，下拉模式不显示标题
        sp_dialog.setPrompt("请选择行星");
        sp_dialog.setAdapter(startAdapter1);
        // 设置下拉框默认显示第一项
        sp_dialog.setSelection(0);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp_dialog.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch(view.getId()) {
            case R.id.sp_dropdown1:
                Utils.showToast(this, "dropdown您选择的是" + starArray[position] + " id: " + id);
                break;
            case R.id.sp_dialog1:
                Utils.showToast(this, "dialog您选择的是" + starArray[position] + " id: " + id);
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // 所有 都没有选择的情况下。
    }
}