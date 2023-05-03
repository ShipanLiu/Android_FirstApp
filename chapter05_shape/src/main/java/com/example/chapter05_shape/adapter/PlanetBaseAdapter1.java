package com.example.chapter05_shape.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.chapter05_shape.R;
import com.example.chapter05_shape.entity.Planet;

import java.util.List;


public class PlanetBaseAdapter1 extends BaseAdapter {
    private Context mContext;
    // I need a data list
    private List<Planet> mPlaneList;

    public PlanetBaseAdapter1(Context mContext, List<Planet> mPlaneList) {
        this.mContext = mContext;
        this.mPlaneList = mPlaneList;
    }

    // 获取列表项的个数,  我要告诉我这个 适配器： 集合里共有 多少个元素？
    @Override
    public int getCount() {
        return mPlaneList.size();
    }


    // return the corresponding item
    @Override
    public Object getItem(int position) {
        return mPlaneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // convertView 就是 一个 item 反复复用
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            // 根据布局文件item_list.xml生成转换视图对象
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
            holder = new ViewHolder();
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        // 给控制设置好数据
        Planet planet = mPlaneList.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);

        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
    }
}
