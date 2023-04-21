package com.example.chapter05_shape;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class t02_Components2 extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    // TimePicker
    private TimePicker timePicker;
    private TextView timePicker_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_components2);

        // time picker
        timePicker = findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        timePicker_result = findViewById(R.id.tv_timePicker_result);
        findViewById(R.id.btn_timePicker_ok).setOnClickListener(this);
        findViewById(R.id.btn_timePicker_dialog).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_timePicker_ok:
                String desc = String.format("您选择的时间是%d时%d分", timePicker.getHour(), timePicker.getMinute());
                timePicker_result.setText(desc);
                break;
            case R.id.btn_timePicker_dialog:
                // 通过Calendar 来获取 当前的 时间
                Calendar calendar = Calendar.getInstance();

                // 构建一个时间的对话框，该对话框 集成了时间选择器
                TimePickerDialog dialog = new TimePickerDialog(this, this,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true); // true表示24小时制，false表示12小时制
                dialog.show();
                break;
            default:
                break;
        }
    }

    // 这个 listener 是 和 TimePickerDialog 里面的 第二个this 搭配的

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String desc = String.format("您选择的时间是%d时%d分", i, i1);
        timePicker_result.setText(desc);
    }
}