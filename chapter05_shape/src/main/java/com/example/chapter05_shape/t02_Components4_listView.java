package com.example.chapter05_shape;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;

import com.example.chapter05_shape.adapter.PlanetBaseAdapter1;
import com.example.chapter05_shape.entity.Planet;
import com.example.chapter05_shape.utils.Utils;

import java.util.List;

public class t02_Components4_listView extends AppCompatActivity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private List<Planet> planetList;
    private ListView listView_Planet;


    private CheckBox ck_divider;
    private CheckBox ck_selectBackgroundColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_components4_list_view);

        // Planet list view
        listView_Planet = findViewById(R.id.lv_planet);
        planetList = Planet.getDefaultList();
        PlanetBaseAdapter1 planetBaseAdapter1 = new PlanetBaseAdapter1(this, planetList);
        listView_Planet.setAdapter(planetBaseAdapter1);


        // we want to know which one is chosen
        listView_Planet.setOnItemClickListener(this);

        // 2 checkbox
        ck_divider = findViewById(R.id.ck_divider);
        ck_divider.setOnCheckedChangeListener(this);
        ck_selectBackgroundColor = findViewById(R.id.ck_selector);
        ck_selectBackgroundColor.setOnCheckedChangeListener(this);


        // seekbar
        SeekBar ques1_seekbar = findViewById(R.id.ques1_seekbar);
        ques1_seekbar.setOnSeekBarChangeListener(this);
        SeekBar ques2_seekbar = findViewById(R.id.ques2_seekbar);
        ques2_seekbar.setOnSeekBarChangeListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Utils.showToast(this, "您选择的是: " + planetList.get(position).name);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.ck_divider:
                // 显示分隔线
                if (ck_divider.isChecked()) {
                    // 从资源文件获得图形对象
                    // 黑色的 分割线。 由 颜色  转换成 drawable，  为了 防止 冲突， 把 主题也 传进去
                    Drawable drawable = getResources().getDrawable(R.color.black, getTheme());
                    listView_Planet.setDivider(drawable);
                    // 设置列表视图的分隔线高度
                    listView_Planet.setDividerHeight(Utils.dip2px(this, 1));
                } else {
                    listView_Planet.setDivider(null);
                    listView_Planet.setDividerHeight(0);
                }
                break;

            case R.id.ck_selector:
                // 显示按压背景
                if (ck_selectBackgroundColor.isChecked()) {
                    // 设置列表项的按压状态图形
                    listView_Planet.setSelector(R.drawable.list_selector);
                } else {
                    Drawable drawable = getResources().getDrawable(R.color.transparent, getTheme());
                    listView_Planet.setSelector(drawable);
                }
                break;
        }
    }


    /* here is for seekbar*/

    /* 开始 拖动时*/
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /* 停止 拖动时*/
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.ques1_seekbar:
                String str = "ques1, progress: " + seekBar.getProgress();
                Utils.showToast(this, str);
                break;
            case R.id.ques2_seekbar:
                String str1 = "ques2, progress: " + seekBar.getProgress();
                Utils.showToast(this, str1);
                break;
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean triggerFromUser) {
//        switch (seekBar.getId()) {
//            case R.id.ques1_seekbar:
//                String str = "ques1, progress: " + progress + " triggerFromUser: " + (triggerFromUser ? "yes" : "no");
//                Utils.showToast(this, str);
//                break;
//            case R.id.ques2_seekbar:
//                String str1 = "ques2, progress: " + progress + " triggerFromUser: " + (triggerFromUser ? "yes" : "no");
//                Utils.showToast(this, str1);
//                break;
//        }
    }


}