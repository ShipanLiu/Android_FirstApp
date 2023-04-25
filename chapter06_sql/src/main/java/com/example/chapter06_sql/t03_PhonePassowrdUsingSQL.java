package com.example.chapter06_sql;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter06_sql.utils.Utils;


public class t03_PhonePassowrdUsingSQL extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView textView_pwd;
    private EditText editText_pwd;
    private EditText editText_phone;
    private Button btn_pwd_forget;
    private Button btn_login; //登陆按钮
    private static String MY_PASSWORD = "123456";
    private static String sms_code;
    private CheckBox checkBox_pwd_remember;
    // 注意 这个不是  RadioGroup， 是RadioButton
    private RadioButton radioButton_pwd;
    private RadioButton radioButton_sms;
    // 跳转页面用的。
    private ActivityResultLauncher<Intent> register1;

    // 储存密码（只能 储存一个， 新的手机号 会覆盖原来的）
    private SharedPreferences preferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t03_phone_passowrd_using_sql);

        // for radiogroup
        RadioGroup radioGroup = findViewById(R.id.radio_group_login_method);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton_pwd = findViewById(R.id.radioBtn_login_pwd);
        radioButton_sms = findViewById(R.id.radioBtn_login_sms);

        // for textView_pwd
        textView_pwd = findViewById(R.id.tv_password);
        editText_pwd = findViewById(R.id.et_password);
        editText_phone = findViewById(R.id.et_phone);
        btn_pwd_forget = findViewById(R.id.btn_forget);
        checkBox_pwd_remember = findViewById(R.id.ck_remember);

        // phone EditText 的文本的监听器, 里面 要自定义一个 watcher, 11 和 6 代表 最大的代码量。
        editText_phone.addTextChangedListener(new MyTextWatcher(editText_phone, 11));
        editText_phone.addTextChangedListener(new MyTextWatcher(editText_pwd, 6));


        // 忘记密码，点击事件跳转到 另一个页面
        btn_pwd_forget.setOnClickListener(this);
        register1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) -> {
            //在 寻找pwd页面 返回结果 不是 null 的前提下。
            if (result != null) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    // 用户在 这个时候 创建了新的密码，
                    MY_PASSWORD = intent.getStringExtra("new_password");
                }
            }
        });


        // login
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        // 保存密码到 sharedPreferences
        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        loadMemory();

    }

    private void loadMemory() {
        // 在重新启动page 的时候， 把 存下来的密码存进去
        boolean pwd_remembered = preferences.getBoolean("pwd_remembered", false);
        if(pwd_remembered) {
            String phone = preferences.getString("phone", null);
            if(phone != null) {
                editText_phone.setText(phone);
            }

            String pwd = preferences.getString("pwd", null);
            if(pwd != null) {
                editText_pwd.setText(pwd);
            }

            checkBox_pwd_remember.setChecked(true);
        } else {
            editText_phone.setText(null);
            editText_pwd.setText(null);
            checkBox_pwd_remember.setChecked(false);
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        // 在重新启动page 的时候， 把 存下来的密码存进去
//
//
//    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch(checkedId) {
            case R.id.radioBtn_login_pwd:
                Toast.makeText(this, "login with pwd", Toast.LENGTH_SHORT).show();
                // login by using pwd
                // 登录密码：
                textView_pwd.setText(getString(R.string.login_password));
                // hint: 请输入密码
                editText_pwd.setHint(getString(R.string.hint_password));
                // button: 忘记密码
                btn_pwd_forget.setText(getString(R.string.forget_password));
                // 隐藏 单选框
                checkBox_pwd_remember.setVisibility(View.VISIBLE);
                break;
            case R.id.radioBtn_login_sms:
                // login by using sms
                // string.xml 里面的 string 要想拿到， 需要 使用 getString（）
                Toast.makeText(this, "login with sms", Toast.LENGTH_SHORT).show();
                textView_pwd.setText(getString(R.string.verifycode));
                // hint: 请输入验证码
                editText_pwd.setHint(getString(R.string.hint_verifycode));
                // button: 获取验证码
                btn_pwd_forget.setText(getString(R.string.get_verifycode));
                // 隐藏 单选框
                checkBox_pwd_remember.setVisibility(View.GONE);
                break;
        }
    }


    // for "addTextChangedListener"
    private class MyTextWatcher implements TextWatcher {
        private EditText editText;
        private int maxLen;
        public MyTextWatcher(EditText editText_phone, int i) {
            editText_phone = this.editText;
            i = this.maxLen;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // 在 phone 那个区域 完成输入之后
            // 隐藏软键盘， 在 输入maxLen 之后
            if(editable.toString().length() == maxLen) {
                Utils.hideKeyboard(t03_PhonePassowrdUsingSQL.this, editText);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String phoneNrStr = editText_phone.getText().toString();
        switch(view.getId()) {
            case R.id.btn_forget:
                // if the phone it not enough long（手机号位数不够）, then "忘记密码" 和 ”发送验证码“ 不会 起作用
                if(phoneNrStr.length() != 11) {
                    Toast.makeText(this, "phone too short", Toast.LENGTH_SHORT).show();
                    // phone 重新获得焦点
                    editText_phone.requestFocus();
                    return;
                }
                // 假如满足了 11 位
                // check if 点击的是 "找回密码", 跳到找回密码界面
                if(radioButton_pwd.isChecked()) {
                    // create new Intent
//                    Intent intent1 = new Intent(this, t04_ProjectFogetPassword.class);
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent1.putExtra("phoneNrStr", phoneNrStr);
//                    // 因为还需要返回结果， 所以不使用 putActivity, 使用register
//                    register1.launch(intent1);
                } else if(radioButton_sms.isChecked()) {
                    // 假如 选择的是sms 登陆， 那就生成 6 位 随机的验证码
                    sms_code = Utils.create6RandomSmsCode();
                    // 弹出 AlertDialog， 提示用户记住 6 位 验证码数字
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("please remember this sms Code");
                    builder.setMessage("PhoneNumber: " + phoneNrStr + ":sms cose "+ sms_code);
                    builder.setPositiveButton("yes", (dialog, which) -> {
                        // 关闭 AlertDialog 的同时， 把 sms code 填进去
                        editText_pwd.setText(phoneNrStr);
                    });
                    AlertDialog alertDialog = b uilder.create();
                    alertDialog.show();
                }

                break;

            case R.id.btn_login:
                // 登陆
                // 判断 密码正确 则登陆
                String pwd = editText_pwd.getText().toString();
                // pwd 验证方式
                if(radioButton_pwd.isChecked()) {
                    if(pwd.equals(MY_PASSWORD)) {
                        loginSuccess();
                    } else {
                        Toast.makeText(this, "wrong pwd", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else if(radioButton_sms.isChecked()) {
                    // sms 的方式
                    Toast.makeText(this, "login with success: " + sms_code, Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    // 提示用户登陆成功
    private void loginSuccess() {
        String desc = String.format("Phone%s，login with success, click yes to previous page",
                editText_phone.getText().toString());
        // 以下弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("login with success");
        builder.setMessage(desc);
        builder.setPositiveButton("yes", (dialog, which) -> {

            // 结束当前的活动页面
            finish();
        });
        builder.setNegativeButton("no", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        // 登陆成功之后，把密码存起来
        if(checkBox_pwd_remember.isChecked()) {
//            SharedPreferences.Editor editer = preferences.edit();
//            editer.putString("phone", editText_phone.getText().toString());
//            editer.putString("pwd", editText_pwd.getText().toString());
//            editer.putBoolean("pwd_remembered", checkBox_pwd_remember.isChecked());
//            editer.commit();

            // 存到数据库里面

        }

    }
}