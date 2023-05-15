package com.example.chapter06_sql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chapter06_sql.database.DBHelper3_ShoppingCart;
import com.example.chapter06_sql.entity.CartInfo;
import com.example.chapter06_sql.entity.GoodsInfo;
import com.example.chapter06_sql.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class t07_ShoppingCart extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_count;
    private TextView tv_total_price;
    private LinearLayout linearLayout_cart;
    private DBHelper3_ShoppingCart mHelper;

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
        setContentView(R.layout.t07_shopping_cart);

        // reste the title
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("welcome to Cart");
        linearLayout_cart = findViewById(R.id.ll_cart);
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


    private void showCart() {
        // 移除下面的所有子视图(linearLayout 里的所有 item 清除，起到 刷新的效果，要不然就会 累加，一直添加)
        linearLayout_cart.removeAllViews();
        // 查询购物车数据库中所有的商品记录
        mCartList = mHelper.queryAllCartInfo();
        if (mCartList.size() == 0) {
            return;
        }

        for (CartInfo info : mCartList) {
            // 根据商品编号查询商品数据库中的商品记录,  because I also need the price
            GoodsInfo goods = mHelper.queryGoodsInfoById(info.goodsId);
            // 先放到 一个Map 里， 存起来
            mGoodsMap.put(info.goodsId, goods);

            // 现在开始 显示到列表里面
            // 获取布局文件item_cart.xml的根视图， 就是一个 item 的 layout
            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            TextView tv_count = view.findViewById(R.id.tv_count);
            // 单价
            TextView tv_price = view.findViewById(R.id.tv_price);
            // 总价
            TextView tv_sum = view.findViewById(R.id.tv_sum);

            iv_thumb.setImageURI(Uri.parse(goods.picPath));
            tv_name.setText(goods.name);
            tv_desc.setText(goods.description);
            tv_count.setText(String.valueOf(info.count));
            tv_price.setText(String.valueOf((int) goods.price));
            // 设置商品总价
            tv_sum.setText(String.valueOf((int) (info.count * goods.price)));

            // 给商品行添加长按事件。长按商品行就删除该商品
            view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(t07_ShoppingCart.this);
                builder.setMessage("delete " + goods.name + " item from cart?");
                builder.setPositiveButton("yes", (dialog, which) -> {
                    // 移除当前视图
                    linearLayout_cart.removeView(v);
                    // 删除该商品(Map中 delete + databse 中delate)
                    deleteGoods(info);
                });
                builder.setNegativeButton("no", null);
                builder.create().show();
                // 这个 return 是针对  listener 的。
                return true;
            });

            // 给商品行添加点击事件。点击商品行跳到商品的详情页
            view.setOnClickListener(v -> {
                Intent intent = new Intent(this, t06_ShoppingItemDetail.class);
                intent.putExtra("goods_id", goods.id);
                startActivity(intent);
            });

            // 往购物车列表添加该商品行
            linearLayout_cart.addView(view);
        }

        // 重新计算购物车中的商品总金额
        refreshTotalPrice();
    }


    private void deleteGoods(CartInfo info) {
        MyApplication.getInstance().goodsCount -= info.count;
        // 从购物车的数据库中删除商品
        mHelper.deleteCartInfoByGoodsId(info.goodsId);
        // 从购物车的列表中删除商品
        CartInfo removed = null;
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
            linearLayout_cart.removeAllViews();
        } else {
            linearLayout_content.setVisibility(View.VISIBLE);
            linearLayout_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

    }
}