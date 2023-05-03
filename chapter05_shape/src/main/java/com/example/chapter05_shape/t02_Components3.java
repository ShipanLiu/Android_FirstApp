package com.example.chapter05_shape;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.chapter05_shape.adapter.PlanetBaseAdapter1;
import com.example.chapter05_shape.entity.Planet;
import com.example.chapter05_shape.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class t02_Components3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Spinner dropdown
    private Spinner spinner_dropdown;
    // 定义 spinner 下拉里面的内容
    private final static String[] starArray = {"水星", "金星", "地球", "火星", "木星", "土星"};

    // SpinnerDialog
    private Spinner sp_dialog;


    // for BaseAdapter
    private List<Planet> planetList;


    // dropdownIcon， 定义 显示 图片的数组
    // 定义下拉列表需要显示的行星图标数组
    private static final int[] iconArray = {
            R.drawable.shuixing, R.drawable.jinxing, R.drawable.diqiu,
            R.drawable.huoxing, R.drawable.muxing, R.drawable.tuxing
    };



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


        // Spinner里 Icon+ 文字， 需要使用 simpleAdapter， 不再是 ArrayAdapter
        // 声明一个映射对象的列表，用于保存行星的图标与名称配对信息
        List<Map<String, Object>> list = new ArrayList<>();
        // iconArray是行星的图标数组，starArray是行星的名称数组
        // 把东西装进去。
        for (int i = 0; i < iconArray.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("icon", iconArray[i]);
            item.put("name", starArray[i]);
            list.add(item);
        }

        // 声明一个下拉列表的简单适配器，其中指定了图标与文本两组数据
        // new String[]{"icon", "name"}, 里卖的 icon 和 name 是 key 的名称
        // new int[]{R.id.iv_icon, R.id.tv_name} 在 item_simple.xml里面。
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
                R.layout.item_simple,
                new String[]{"icon", "name"},
                new int[]{R.id.iv_icon, R.id.tv_name});

        Spinner sp_icon = findViewById(R.id.sp_icon);
        sp_icon.setAdapter(simpleAdapter);
        sp_icon.setSelection(0);
        sp_icon.setOnItemSelectedListener(this);



        // 测试 BaseAdapter（用的最多）, 但是 BaseAdapter 是一个抽象类， 无法直接使用， 所以 需要来一个 adapter 的实现方法。
        Spinner sp_planet = findViewById(R.id.sp_base);
        // 获取默认的行星列表，即水星、金星、地球、火星、木星、土星
        planetList = Planet.getDefaultList();
        // 构建一个行星列表的适配器
        PlanetBaseAdapter1 adapter = new PlanetBaseAdapter1(this, planetList);
        sp_planet.setAdapter(adapter);
        sp_planet.setSelection(0);
        sp_planet.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//        Utils.showToast(this, "view.getId: " + view.getId() + " SpinnerIcon您选择的是" + starArray[position] + " id: " + id);
        Utils.showToast(this, "您选择的是" + planetList.get(position).name);


//        switch(view.getId()) {
//            case R.id.sp_dropdown1:
//                System.out.println("1");
//                Utils.showToast(this, "dropdown您选择的是" + starArray[position] + " id: " + id);
//                break;
//            case R.id.sp_dialog1:
//                System.out.println("2");
//                Utils.showToast(this, "dialog您选择的是" + starArray[position] + " id: " + id);
//                break;
//            case R.id.sp_icon:
//                System.out.println("3");
//                Utils.showToast(this, "SpinnerIcon您选择的是" + starArray[position] + " id: " + id);
//                break;
//
//        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // 所有 都没有选择的情况下。
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int pisition, long id) {
//        switch(view.getId()) {
//            case R.id.id1:
//
//
//
//                break;
//        }
//    }
}