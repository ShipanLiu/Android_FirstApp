package com.example.chapter05_shape;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

public class t02_Components1 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private TextView tv_info;
    private TextView tv_radio_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_components);

        // find
        tv_info = findViewById(R.id.v_req_info);
        tv_radio_result = findViewById(R.id.tv_radio_result);
        RadioGroup radioGroup = findViewById(R.id.radio_group_gender);

        // get the bundle from the previous page
        Bundle bundle = getIntent().getExtras();
        String msg = bundle.getString("info");
        tv_info.setText(msg);

        //click
        radioGroup.setOnCheckedChangeListener(this);

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
}