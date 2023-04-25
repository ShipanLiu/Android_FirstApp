package com.example.chapter05_shape;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.chapter05_shape.utils.Utils;

public class t01_DrawableShape extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View viewContent;
    private TextView tv_result;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t01_drawable_shape);

        viewContent = findViewById(R.id.v_content);
        findViewById(R.id.btn_oval).setOnClickListener(this);
        findViewById(R.id.btn_rect).setOnClickListener(this);

        //  我在 res/drawable 里面定义了两个形状，我想把他们 作为背景应用
        viewContent.setBackgroundResource(R.drawable.shape_oval_rose);


        //  test checkbox
        CheckBox ck_system = findViewById(R.id.ck_system);
        CheckBox ck_custom = findViewById(R.id.ck_custom);

        ck_system.setOnCheckedChangeListener(this);
        ck_custom.setOnCheckedChangeListener(this);

        // test switch
        Switch switch1 =  findViewById(R.id.sw_status1);
        CheckBox switch2 = findViewById(R.id.sw_status2);


        tv_result = findViewById(R.id.tv_sw_result);

        switch1.setOnCheckedChangeListener(this);
        switch2.setOnCheckedChangeListener(this);

        // 由于页面不够， 点击到下一个页面进行展示
        Button btn = findViewById(R.id.btn_jump);
        btn.setOnClickListener(this);
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                }
            }
        });

        // jump 2 button 的 监听
        findViewById(R.id.btn_jump2).setOnClickListener(this);
        findViewById(R.id.btn_jump3).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_rect:
                viewContent.setBackgroundResource(R.drawable.shape_rect_gold);
                break;
            case R.id.btn_oval:
                viewContent.setBackgroundResource(R.drawable.shape_oval_rose);
                break;

            case R.id.btn_jump:
                // create Intent
                Intent intent = new Intent(this, t02_Components1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // create bundle and diliver someinformation
                Bundle bundle = new Bundle();
                bundle.putString("info", Utils.getCurrentTime() + "this is from previous page");
                intent.putExtras(bundle);
                register.launch(intent);
                break;
            case R.id.btn_jump2:
                // create Intent
                Intent intent2 = new Intent(this, t02_Components2.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // create bundle and diliver someinformation
                startActivity(intent2);
                break;
            case R.id.btn_jump3:
                // create Intent
                Intent intent3 = new Intent(this, t02_Components3.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // create bundle and diliver someinformation
                startActivity(intent3);
                break;

            default:
                break;
        }



    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        switch(compoundButton.getId()) {
            case R.id.ck_system:
            case R.id.ck_custom:
                String str = String.format("you have %s this checkbox", checked ? "chosen" : "not chosen");
                compoundButton.setText(str);
                break;
            case R.id.sw_status1:
            case R.id.sw_status2:
                String str1 = String.format("you have %s this switch", checked ? "chosen" : "not chosen");
                tv_result.setText(str1);
            default:
                break;
        }
    }
}