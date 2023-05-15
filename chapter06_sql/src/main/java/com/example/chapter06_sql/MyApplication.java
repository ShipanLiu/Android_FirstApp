package com.example.chapter06_sql;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.chapter06_sql.database.BookDatabase_Room;
import com.example.chapter06_sql.database.DBHelper3_ShoppingCart;
import com.example.chapter06_sql.entity.GoodsInfo;
import com.example.chapter06_sql.utils.FileUtil;
import com.example.chapter06_sql.utils.SharedPreferenceUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {

    // 购物车中的商品总数量， 全局变量
    public int goodsCount;

    //声明一个 全局变量， 所有的 activity 都可以使用（Application 的声明周期）
    private static MyApplication mApp;
    // 声明一个公共的信息映射对象，可当作全局变量使用
    public HashMap<String, String> infoMap = new HashMap<>();

     // here we will use the ROOM, 来实例化 database
    private BookDatabase_Room bookDatabase;


    // 单例模式 返回 MyApplication
    public static MyApplication getInstance() {
        return mApp;
    }



    //   在App 启动时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        // 配合 单例模式 使用
        mApp = this;


        Log.d("My-Application", "onCreate of Application");

        // 在应用启动的时候， 构建一个database实例 + 设置 database 的名字为 book
        bookDatabase = Room.databaseBuilder(this, BookDatabase_Room.class, "book")
                // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库。如此一来原来的记录会丢失，故而要改为迁移方式以便保存原有记录）
                .addMigrations()
                // 允许在主线程中操作数据库（Room默认不能在主线程中操作数据库）
                .allowMainThreadQueries()
                .build();


        // here is for 购物车项目
        // 初始化 商品信息, 第一次打开app 的时候， 数据就从 GoodsInfo里面的 集合 拿出来， 第二次 在打开App 的时候，
        // 就不用这样做了 ， 就直接 去数据库里卖弄查询 就行了。
        // how could I know this is my first time to open this App? -- via SharedPreferenced
        initialGoods();


    }



    // 你现在创建了一个 database 叫 “book”， 创建一个方法 返回  this database
    public BookDatabase_Room getBookDatebase() {
        return bookDatabase;
    }


    private void initialGoods() {
        // check if this is the first time to open the app
        // 获取共享参数保存的是否首次打开参数, 第一打开 ， first 的 默认value 是 true
        boolean isFirst = SharedPreferenceUtil.getInstance(this).readBoolean("first", true);
        // 获取当前App的私有下载路径(存储卡的 path)
        String directoryPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar;
        if (isFirst) {
            // 模拟网络图片下载
            // get the list of phons
            List<GoodsInfo> list = GoodsInfo.getDefaultList();
            for (GoodsInfo info : list) {
                // get the "pic" from GoodInfo
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.pic);
                String path = directoryPath + info.id + ".jpg";
                // 往存储卡保存商品图片
                FileUtil.saveImage(path, bitmap);
                // 回收bitMap
                bitmap.recycle();
                // update the picPath in GoodInfo, the pic path in SD card
                info.picPath = path;
            }
            // 打开数据库，把商品信息插入到表中
            DBHelper3_ShoppingCart dbHelper = DBHelper3_ShoppingCart.getInstance(this);
            dbHelper.openWriteLink();
            dbHelper.insertGoodsInfoList(list);
            dbHelper.closeLink();
            // 把是否首次打开写入共享参数, 以后打开不再是 第一次打开了。
            SharedPreferenceUtil.getInstance(this).writeBoolean("first", false);
        }
    }

















    // APP终止的时候(不要 用这个 onTerminate)

    @Override
    public void onTerminate() {
        Log.d("My-Application", "onTerminate of Application");
        super.onTerminate();
    }

    // 比如从横屏 到 竖屏
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
