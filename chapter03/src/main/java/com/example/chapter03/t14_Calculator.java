package com.example.chapter03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class t14_Calculator extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    // = 键
    private Button equalBtn;

    // 第一个操作数
    private String firstNum = "";
    // 运算符
    private String operator = "";
    // 第二个操作数
    private String secondNum = "";
    // 当前的计算结果
    private String result = "";
    // 显示的文本内容
    private String showText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t14_calculator_layout);
        // 结果显示 区域 注册监听
        textView = findViewById(R.id.resultArea);
        // 给 button 注册监听
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this); // “除法”按钮
        findViewById(R.id.btn_multiply).setOnClickListener(this); // “乘法”按钮
        findViewById(R.id.btn_clear).setOnClickListener(this); // “清除”按钮
        findViewById(R.id.btn_seven).setOnClickListener(this); // 数字7
        findViewById(R.id.btn_eight).setOnClickListener(this); // 数字8
        findViewById(R.id.btn_nine).setOnClickListener(this); // 数字9
        findViewById(R.id.btn_plus).setOnClickListener(this); // “加法”按钮
        findViewById(R.id.btn_four).setOnClickListener(this); // 数字4
        findViewById(R.id.btn_five).setOnClickListener(this); // 数字5
        findViewById(R.id.btn_six).setOnClickListener(this); // 数字6
        findViewById(R.id.btn_minus).setOnClickListener(this); // “减法”按钮
        findViewById(R.id.btn_one).setOnClickListener(this); // 数字1
        findViewById(R.id.btn_two).setOnClickListener(this); // 数字2
        findViewById(R.id.btn_three).setOnClickListener(this); // 数字3
        findViewById(R.id.btn_reciprocal).setOnClickListener(this); // 求倒数按钮
        findViewById(R.id.btn_zero).setOnClickListener(this); // 数字0
        findViewById(R.id.btn_dot).setOnClickListener(this); // “小数点”按钮
        equalBtn = findViewById(R.id.btn_equal);
        equalBtn.setOnClickListener(this); // “等号”按钮
        findViewById(R.id.ib_sqrt).setOnClickListener(this); // “开平方”按钮

    }

    @Override
    public void onClick(View view) {
        // 这里面的蚕食 view， 就是 我传进来的button
        String inputText;

        // 如果是 开根号， 以为 开根号 btn 是一个imageBtn
        if(view.getId() == R.id.ib_sqrt) {
            inputText = "√";
        } else {
            // 其他情况
            inputText = ((TextView)view).getText().toString();
        }

        switch (view.getId()) {
            // 点击了清除按钮
            case R.id.btn_clear:
                clear();
                break;
            // 点击了取消按钮
            case R.id.btn_cancel:
                clear();
                break;
            // 点击了加、减、乘、除按钮
            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                // 按下 operator 之后，就会立刻显示
                operator = inputText; // 运算符
                refreshText(showText + operator); // 字符串是 一个累加的过程
                // enable 等号键
                equalBtn.setEnabled(true);
                break;
            // 点击了等号按钮
            case R.id.btn_equal:
                // 加减乘除四则运算
                if(firstNum.equals("") || operator.equals("") || secondNum.equals("")) {
                    // 防止上来就按下等号的情况, 或者 第二个操作数没有input， 就按下 =
                    break;
                } else {
                    double calculate_result = calculateFour();
                    refreshOperate(String.valueOf(calculate_result));
                    refreshText(showText + "=" + result);
                    //然后禁用 = button , 这里防止连按两次
                    equalBtn.setEnabled(false);
                    break;
                }

            // 点击了开根号按钮
            case R.id.ib_sqrt:
                double sqrt_result = Math.sqrt(Double.parseDouble(firstNum));
                refreshOperate(String.valueOf(sqrt_result)); // 里面把 double 换成 string
                refreshText(showText + "√=" + result);
                //然后禁用 = button
                equalBtn.setEnabled(false);
                break;
            // 点击了求倒数按钮
            case R.id.btn_reciprocal:
                double reciprocal_result = 1.0 / Double.parseDouble(firstNum);
                refreshOperate(String.valueOf(reciprocal_result));
                refreshText(showText + "/=" + result);
                //然后禁用 = button
                equalBtn.setEnabled(false);
                break;
            // 点击了其他按钮，包括数字和小数点
            default:
                // 上次的运算结果已经出来了
                if (result.length() > 0 && operator.equals("")) {
                    clear();
                }

                // 无运算符，则继续拼接第一个操作数， 根据判断 运算符 就可以 发现 你计算到那一步了
                if (operator.equals("")) {
                    System.out.println("firstNum");
                    firstNum = firstNum + inputText;
                } else {
                    // 有运算符，则继续拼接第二个操作数
                    secondNum = secondNum + inputText;
                }
                // 整数不需要前面的0， 比如输入一个0 再输入一个 8， 然后显示 08 是不允许的，  但是0. 是被允许的
                // showText 是 拼接完的 String， inputText 是 刚刚背按下的按键的 String
                if (showText.equals("0") && !inputText.equals(".")) {
                    refreshText(inputText);
                } else {
                    refreshText(showText + inputText);
                }
                break;
        }

    }


    // 加减乘除四则运算，返回计算结果
    private double calculateFour() {
        switch (operator) {
            case "＋":
                return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
            case "－":
                return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
            case "×":
                return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
            default:
                return Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }
    }

    // 清空并初始化
    private void clear() {
        refreshOperate("");
        refreshText("");
    }

    // 刷新运算结果
    private void refreshOperate(String new_result) {
        result = new_result;
        firstNum = result;
        secondNum = "";
        operator = "";
    }

    // 刷新文本显示
    private void refreshText(String text) {
        showText = text;
        textView.setText(showText);
    }
}