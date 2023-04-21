package com.example.chapter05_shape;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter05_shape.utils.Utils;

import java.util.Calendar;

public class t02_Components1 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {
    private TextView tv_info;
    private TextView tv_radio_result;

    // EditText
    private EditText textBox_phone;
    private EditText textBox_pwd;

    // alert
    private TextView tv_alert;

    // dataPicker
    private DatePicker datePicker;
    private TextView tv_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_components1);

        // find
        tv_info = findViewById(R.id.v_req_info);
        tv_radio_result = findViewById(R.id.tv_radio_result);
        RadioGroup radioGroup = findViewById(R.id.radio_group_gender);


        // find -- EditText
        textBox_phone = findViewById(R.id.textBox_phone);
        textBox_pwd = findViewById(R.id.textBox_pwd);
        Button textBox_login_btn = findViewById(R.id.textBox_login);


        // get the bundle from the previous page
        Bundle bundle = getIntent().getExtras();
        String msg = bundle.getString("info");
        tv_info.setText(msg);

        //on click
        textBox_login_btn.setOnClickListener(this);


        //on checked
        radioGroup.setOnCheckedChangeListener(this);


        //on focus
            //一旦focus pws box,就会check phone的位数是否 11位（这里的 listener 是 FocusChangeListener， 不是 onclick listener）
        textBox_pwd.setOnFocusChangeListener(this);

        // Text changed listener(当输入 到11 位的时候， 目的： 软键盘自动隐藏。)
        textBox_phone.addTextChangedListener(new MyTextWatcher(textBox_phone, 11));
        textBox_pwd.addTextChangedListener(new MyTextWatcher(textBox_pwd, 6));


        // test alert
        findViewById(R.id.btn_alert1).setOnClickListener(this);
        findViewById(R.id.btn_alert1).setOnClickListener(this);

        tv_alert = findViewById(R.id.tv_alert);


        // data_picker
        datePicker = findViewById(R.id.data_picker);
        tv_date = findViewById(R.id.tv_date);
        findViewById(R.id.btn_date).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);





    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch(checkedId) {
            case R.id.radio_male:
                tv_radio_result.setText("male is chosen, and the chosenId: " + radioGroup.getCheckedRadioButtonId());
                break;
            case R.id.radio_female:
                tv_radio_result.setText("female is chosenand the chosenId: " + radioGroup.getCheckedRadioButtonId());
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus) {
            // check length of textBox_phone
            String phone = textBox_phone.getText().toString();
            if(phone.isEmpty() || phone.length() < 11) {
                // the textBox_phone will get the focus again
                textBox_phone.requestFocus();
                // 一个小框从下面 弹出来
                Toast.makeText(this, "the phone should be 11", Toast.LENGTH_SHORT).show();
            }

        }
    }


    // 这里是一个 inner class， 当输入 到11 位的时候， 软键盘自动隐藏。

    private class MyTextWatcher implements TextWatcher {

            // a private EditText Object
        private EditText editText;
            // a private Integer
        private int maxLen;


        public MyTextWatcher(EditText textBox_phone, int maxLength) {
            this.editText = textBox_phone;
            this.maxLen = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        // 在 输入之后
        @Override
        public void afterTextChanged(Editable editable) {
            // get the text inputed
            String str = editable.toString();
            // check if the length == maxLen, if yes, then close the keyboard
            if(str.length() == maxLen) {
                // hide the keyboard
                // t02_Components1.this  就是一个页面，就是 一个activity
                Utils.hideKeyboard(t02_Components1.this, editText);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_alert1:
                //  对话框 builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // title
                builder.setTitle("this is a title");
                // message
                builder.setMessage("this is a message");
                //yes
                builder.setPositiveButton("残忍卸载", (dialog, which) -> {
                    tv_alert.setText("残忍卸载");
                });
                //no
                builder.setNegativeButton("我再想想", (dialog, which) -> {
                    tv_alert.setText("我再想想");
                });
                // builder 把信息 收集完毕之后:
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.btn_ok:
                String desc = String.format("您选择的日期是%d年%d月%d日", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                tv_date.setText(desc);
                break;
            case R.id.btn_date:
                // 获取日历的一个实例，里面包含了当前的年月日
                /*Calendar calendar = Calendar.getInstance();
                calendar.get(Calendar.YEAR);
                calendar.get(Calendar.MONTH);
                calendar.get(Calendar.DAY_OF_MONTH);*/
                DatePickerDialog dialog = new DatePickerDialog(this, this, 2090, 5, 11);
                // 显示日期对话框
                dialog.show();
                break;
            default:
                break;
        }
    }


    // 这就是 哪个 data Dialog 的 Listener
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String desc = String.format("您选择的日期是%d年%d月%d日", i, i1 + 1, i2);
        tv_date.setText(desc);
    }

}