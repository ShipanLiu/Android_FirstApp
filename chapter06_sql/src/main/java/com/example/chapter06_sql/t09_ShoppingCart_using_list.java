package com.example.chapter06_sql;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter06_sql.adapter.CartAdapter;
import com.example.chapter06_sql.database.DBHelper3_ShoppingCart;
import com.example.chapter06_sql.entity.CartInfo;
import com.example.chapter06_sql.entity.GoodsInfo;
import com.example.chapter06_sql.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class t09_ShoppingCart_using_list extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView tv_count;
    private TextView tv_total_price;
    private ListView listView_cart;
    private DBHelper3_ShoppingCart mHelper;
    private CartAdapter cartAdapter;

    // 声明一个购物车中的商品信息列表
    private List<CartInfo> mCartList;
    // 声明一个根据商品编号查找商品信息的映射，把商品信息缓存起来，这样不用每一次都去查询数据库， 比如 在delete 一个 item之后， update database之后， 不用 query again
    // good idea
    private Map<Integer, GoodsInfo> mGoodsMap = new HashMap<>();
    private LinearLayout linearLayout_empty;
    private LinearLayout linearLayout_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t09_shopping_cart_using_list);

        // reste the title
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("welcome to Cart");
        listView_cart = findViewById(R.id.listView_cart);
        tv_total_price = findViewById(R.id.tv_total_price);

        // 购物车里面商品的 数量，  从 MyApplication 中的 全局变量 拿出来，
        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        // initialize the mHelper
        mHelper = DBHelper3_ShoppingCart.getInstance(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_pay).setOnClickListener(this);
        linearLayout_empty = findViewById(R.id.ll_empty);
        linearLayout_content = findViewById(R.id.ll_content);
    }


    /* 在 和 用具交互的 时候（onResume）， 展示 cart 的item*/
    @Override
    protected void onResume() {
        super.onResume();

        showCart();
    }


    /* 转变 成 使用listview， 主要是 这一部分的转变*/
    private void showCart() {
        // first get datalist  ---> give datalist to an adapter    ---> give adapter to listview

        // get cart ino
        mCartList = mHelper.queryAllCartInfo();
        if(mCartList.size() == 0) {
            return;
        }

        // to get goodinfo
        for(CartInfo item : mCartList) {
            GoodsInfo goodsInfo = mHelper.queryGoodsInfoById(item.goodsId);
            // the map is used to save goodsinfo
            mGoodsMap.put(item.goodsId, goodsInfo);
            // 把 查出来 的 对应的 GoodsInfo 存入到 item 里面
            item.goodsInfo = goodsInfo;
        }

        // now you get the datalist, so it's time to get the adapter
        cartAdapter = new CartAdapter(this, mCartList);
        // ListView receive the adapter
        listView_cart.setAdapter(cartAdapter);

        // add click short listener, go to detail page
        listView_cart.setOnItemClickListener(this);
        // add click long listener,  delete item
        listView_cart.setOnItemLongClickListener(this);



    }

    /* ListView */

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // go to deltail page with goodsId
        Intent intent = new Intent(this, t06_ShoppingItemDetail.class);
        intent.putExtra("goods_id", mCartList.get(position).goodsId);
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        // 长按的话, will delete

        // targert item
        CartInfo info =  mCartList.get(position);
        // dialog
        AlertDialog.Builder builder =  new AlertDialog.Builder(t09_ShoppingCart_using_list.this);
        builder.setMessage("delete " + info.goodsInfo.name + " ?");
        builder.setPositiveButton("yes", (dialog, which) -> {
            // delete this info in list
            mCartList.remove(position);

            //列表刷新， 通过 适配器
            cartAdapter.notifyDataSetChanged();

            //delete goods from Map and database
            deleteGoods(info);
        });

        builder.setNegativeButton("no", null);
        builder.create().show();
        return true;
    }


    private void deleteGoods(CartInfo info) {
        // 假如 info.count = 5, 则  MyApplication.getInstance().goodsCount -= 5
        MyApplication.getInstance().goodsCount -= info.count;
        // 从购物车的数据库中删除商品
        mHelper.deleteCartInfoByGoodsId(info.goodsId);
        // 从购物车的列表中删除商品
        CartInfo removed = null;
        // for循环 只负责查询出来， 在 for 循环里面 千万不要操作，
        for (CartInfo cartInfo : mCartList) {
            if (cartInfo.goodsId == info.goodsId) {
                removed = cartInfo;
                break;
            }
        }
        mCartList.remove(removed);
        // 显示最新的商品数量
        refreshItemCount();
        Utils.showToast(this, "已从购物车删除" + mGoodsMap.get(info.goodsId).name);
        mGoodsMap.remove(info.goodsId); // 更新本地 Map
        // 刷新购物车中所有商品的总金额
        refreshTotalPrice();
    }

    // 重新计算购物车中的商品总金额
    private void refreshTotalPrice() {
        int totalPrice = 0;
        for (CartInfo info : mCartList) {
            // info 中没有商品的价格， price 在 mGoodsMap里面找
            GoodsInfo goods = mGoodsMap.get(info.goodsId);
            totalPrice += goods.price * info.count;
        }
        tv_total_price.setText(String.valueOf(totalPrice));
    }

    private void refreshItemCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        // 购物车中没有商品，显示“空空如也”
        if (MyApplication.getInstance().goodsCount == 0) {
            linearLayout_empty.setVisibility(View.VISIBLE);
            linearLayout_content.setVisibility(View.GONE);
            //列表刷新， 通过 适配器
            cartAdapter.notifyDataSetChanged();
        } else {
            linearLayout_content.setVisibility(View.VISIBLE);
            linearLayout_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                // 点击了返回图标
                // 关闭当前页面
                finish();
                break;

            case R.id.btn_shopping_channel:
                // 从购物车页面跳到商场页面
                Intent intent = new Intent(this, t08_shopping_page_using_list.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.btn_clear:
                // 清空购物车数据库
                mHelper.deleteAllCartInfo();
                // update this global variable
                MyApplication.getInstance().goodsCount = 0;
                // 显示最新的商品数量
                refreshItemCount();
                Utils.showToast(this, "cart is cleared");
                break;

            case R.id.btn_pay:
                // 点击了“结算”按钮
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("pay process");
                builder.setMessage("sorry, paying function is not implemented yet");
                builder.setPositiveButton("I know", null);
                builder.create().show();
                break;
        }
    }



}