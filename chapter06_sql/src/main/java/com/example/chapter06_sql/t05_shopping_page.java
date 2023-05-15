package com.example.chapter06_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chapter06_sql.database.DBHelper3_ShoppingCart;
import com.example.chapter06_sql.entity.GoodsInfo;
import com.example.chapter06_sql.utils.Utils;

import java.util.List;

public class t05_shopping_page extends AppCompatActivity implements View.OnClickListener {

    // define a mHelper
    private DBHelper3_ShoppingCart mHelper;

    private TextView tv_count;
    private GridLayout gl_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t05_shopping_page);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商场");

        tv_count = findViewById(R.id.tv_count);
        gl_channel = findViewById(R.id.gl_channel);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);



        // get mHelper(在 MyActivity 里， 打开 App 之后， 预设的 6 个 手机 已经存在了 db里面)
        mHelper = DBHelper3_ShoppingCart.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();

        // 不是 从 GoodsInfo 里面 拿出 list，而是 把 商品从 database 里卖弄查出来
        showGoods();


    }

    /*自然你要 展示 最新的 cart item number*/
    /*onResume 是在用户从cart 中心返回 t05_shopping_page 的时候 调用的。*/
    @Override
    protected void onResume() {
        super.onResume();
        // 查询购物车商品总数，并展示
        showCartInfoTotal();
    }

    private void showCartInfoTotal() {
        int count = mHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // close mHelper
        mHelper.closeLink();

    }



    private void showGoods() {
        // 商品条目是一个线性布局，设置布局的宽度为屏幕的一半
        // get the width of the screen
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        // create params of a linearlayout
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 查询商品数据库中的所有商品记录
        List<GoodsInfo> list = mHelper.queryAllGoodsInfo();

        // 移除下面的所有子视图(起到 应刷新的 作用)
        gl_channel.removeAllViews();

        for (GoodsInfo info : list) {
            // 获取布局文件item_goods.xml的根视图, 把 xml 变成一个 对象
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_price = view.findViewById(R.id.tv_price);
            Button btn_add = view.findViewById(R.id.btn_add);

            // 给控件设置值
            iv_thumb.setImageURI(Uri.parse(info.picPath)); // 把 图片 根据 path 加载出来
            tv_name.setText(info.name);
            tv_price.setText(String.valueOf((int) info.price));

             //添加到购物车
            btn_add.setOnClickListener(v -> {
                addToCart(info.id, info.name);
            });

            // 点击商品图片，跳转到商品详情页面
            iv_thumb.setOnClickListener(v -> {
                Intent intent = new Intent(this, t06_ShoppingItemDetail.class);
                intent.putExtra("goods_id", info.id);
                startActivity(intent);
            });

            // 把商品视图添加到网格布局, ，每个商品是 一般的 windows width
            gl_channel.addView(view, params);
        }
    }

    // 把指定编号的商品添加到购物车
    private void addToCart(int goodsId, String goodsName) {
        // 购物车商品数量+1, 这个 商品数量是 MyAppliaction 里的全局变量
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));


        mHelper.insertCartInfo(goodsId);
        Utils.showToast(this, "已添加一部" + goodsName + "到购物车");
    }

    @Override
    public void onClick(View view) {
        // 在 点击 <-   或者  购物车 按钮的时候。
        switch (view.getId()) {
            case R.id.iv_back:
                // 点击了返回图标，关闭当前页面
                finish();
                break;
            case R.id.iv_cart:
                // 点击了购物车图标
                // 从商场页面跳到购物车页面
                Intent intent = new Intent(this, t07_ShoppingCart.class);
                // 设置启动标志，避免多次返回同一页面的
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}