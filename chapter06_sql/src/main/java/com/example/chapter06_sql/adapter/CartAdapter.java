package com.example.chapter06_sql.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter06_sql.R;
import com.example.chapter06_sql.entity.CartInfo;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    // CartAdapter  需要 两个属性：

    // current context
    private Context mContext;
    // data list
    private List<CartInfo> mCartInfoList;

    public CartAdapter(Context mContext, List<CartInfo> mCartInfoList) {
        this.mContext = mContext;
        this.mCartInfoList = mCartInfoList;
    }

    @Override
    public int getCount() {
        return mCartInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCartInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            // 获取布局文件item_cart.xml的根视图
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cart, null);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.tv_count = convertView.findViewById(R.id.tv_count);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.tv_sum = convertView.findViewById(R.id.tv_sum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartInfo info = mCartInfoList.get(position);
        holder.iv_thumb.setImageURI(Uri.parse(info.goodsInfo.picPath));
        holder.tv_name.setText(info.goodsInfo.name);
        holder.tv_desc.setText(info.goodsInfo.description);
        holder.tv_count.setText(String.valueOf(info.count));
        holder.tv_price.setText(String.valueOf((int) info.goodsInfo.price));
        // 设置商品总价
        holder.tv_sum.setText(String.valueOf((int) (info.count * info.goodsInfo.price)));
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_desc;
        public TextView tv_count;
        public TextView tv_price;
        public TextView tv_sum;

    }
}
