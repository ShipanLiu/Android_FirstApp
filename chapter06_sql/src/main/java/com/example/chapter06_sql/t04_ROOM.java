package com.example.chapter06_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.chapter06_sql.dao.BookDao;
import com.example.chapter06_sql.entity.BookInfo;
import com.example.chapter06_sql.utils.Utils;

import java.util.List;

public class t04_ROOM extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_author;
    private EditText et_press;
    private EditText et_price;

    private BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t04_room);

        et_name = findViewById(R.id.et_name);
        et_author = findViewById(R.id.et_author);
        et_press = findViewById(R.id.et_press);
        et_price = findViewById(R.id.et_price);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

        // 首先 要获取 bookDao, MyApplication.getInstance().getBookDatebase()是 得到 database，
        bookDao = MyApplication.getInstance().getBookDatebase().bookDao();


    }

    @Override
    public void onClick(View view) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();

        switch (view.getId()) {
            case R.id.btn_save:
                // 以下声明一个书籍信息对象，并填写它的各字段值
                BookInfo b1 = new BookInfo();
                b1.setName(name);
                b1.setAuthor(author);
                b1.setPress(press);
                b1.setPrice(Double.parseDouble(price));
                bookDao.insert(b1);
                Utils.showToast(this, "保存成功");
                break;
                /*
                *
                *   BookInfo{id=1, name='book1', author='jier', press='dan', price=100.0}
                    BookInfo{id=2, name='book2', author='jier', press='dan', price=100.0}
                    BookInfo{id=3, name='book3', author='jier', press='dan', price=100.0}
                *
                *
                * */



            case R.id.btn_delete:
                // now detete the book with id 1, b2的其他属性 可以不给
                // 因为 id 是 主键， 你只能根据 id 来删除。
                // 假如你想根据 name 来 删， 则， 先需要根据 name 查询， 拿到id， 再根据 id 的值 来删
                // 根据名字查询到数据库中已有的记录， 拿到要修改 target book 的 id
                BookInfo b2 = bookDao.queryByName(name);
                if(b2 != null) {
                    Utils.showToast(this, "delete book name: " + b2.getName() + " id: " + b2.getId());
                    // 然后 把 b2 删了
                    bookDao.delete(b2);
                } else {
                    Utils.showToast(this, "book name does not exist");
                }

                break;

            case R.id.btn_update:
                BookInfo b3 = new BookInfo();
                // 根据名字查询到数据库中已有的记录， 拿到要修改 target book 的 id
                BookInfo b4 = bookDao.queryByName(name);
                if(b4 == null) {
                    Utils.showToast(this, "book name does not exist");
                } else {
                    Utils.showToast(this, "update book name: " + b4.getName() + " id: " + b4.getId());
                    // 拿到要修改 book 的 id
                    b3.setId(b4.getId());
                    // 对 target book 进行修改
                    b3.setName(name);
                    b3.setAuthor(author);
                    b3.setPress(press);
                    // 因为price 是一个 string， 要转成 double
                    b3.setPrice(Double.parseDouble(price));
                    bookDao.update(b3);
                }
                break;

            case R.id.btn_query:
                List<BookInfo> list = bookDao.queryAll();
                Log.d("my_tag", "############################");
                for (BookInfo b : list) {
                    Log.d("my_tag", b.toString());
                }
                break;
        }

    }
}