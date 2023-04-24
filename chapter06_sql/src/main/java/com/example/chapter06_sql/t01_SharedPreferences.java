package com.example.chapter06_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class t01_SharedPreferences extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_name;
    private EditText editText_age;
    private EditText editText_height;
    private EditText editText_weight;
    private CheckBox checkBox_married;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t01_shared_preferences);

        //findViewByID
        editText_name = findViewById(R.id.et_name);
        editText_age = findViewById(R.id.et_age);
        editText_height = findViewById(R.id.et_height);
        editText_weight = findViewById(R.id.et_weight);
        checkBox_married = findViewById(R.id.ck_married);

        // button save data
        findViewById(R.id.btn_save).setOnClickListener(this);

        // 设置 sharedPreferences 的xml存储文件名 和 mode, Context.MODE_PRIVATE 私有， 其他app 不能访问
        preferences = getSharedPreferences("mySharedArea", Context.MODE_PRIVATE);

        // 一打开这个 activity（page）， 就 把信息 读出来
        infoReload();





    }
    @Override
    public void onClick(View view) {
        // 点击button， 要获取 上面输入的内容
        String name = editText_name.getText().toString();
        String age = editText_age.getText().toString();
        String height = editText_height.getText().toString();
        String weight = editText_weight.getText().toString();

        // 获得 preferences 的编辑器
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.putInt("age", Integer.parseInt(age));
        editor.putFloat("height", Float.parseFloat(height));
        editor.putFloat("weight", Float.parseFloat(weight));
        editor.putBoolean("married", checkBox_married.isChecked());

        editor.commit();
        Toast.makeText(this, "data saved into SharedPreferenced", Toast.LENGTH_SHORT).show();


    }

    private void infoReload() {
        String name = preferences.getString("name", null);
        if (name != null) {
            editText_name.setText(name);
        }

        int age = preferences.getInt("age", 0);
        if (age != 0) {
            editText_age.setText(String.valueOf(age));
        }

        float height = preferences.getFloat("height", 0f);
        if (height != 0f) {
            editText_height.setText(String.valueOf(height));
        }

        float weight = preferences.getFloat("weight", 0f);
        if (weight != 0f) {
            editText_weight.setText(String.valueOf(weight));
        }

        // default value is false
        boolean married = preferences.getBoolean("married", false);
        checkBox_married.setChecked(married);
    }

}