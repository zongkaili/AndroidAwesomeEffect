package com.kelly.effect.database;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kelly.effect.R;
import com.kelly.effect.database.db.BaseDao;
import com.kelly.effect.database.db.BaseDaoFactory;
import com.kelly.effect.database.db.OrderDao;
import com.kelly.effect.database.db.UserDao;
import com.kelly.effect.database.subdb.BaseDaoSubFactory;
import com.kelly.effect.database.subdb.PhotoDao;
import com.kelly.effect.database.update.UpdateManager;

import java.util.Date;
import java.util.List;

public class DbActivity extends AppCompatActivity {

    int i = 0;
    UserDao userDao;
    public static String thisVersion = "V003";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        initView();
    }

    private void initView() {
        findViewById(R.id.insert).setOnClickListener(v -> {
            BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
            baseDao.insert(new User(1, "netease1", "111"));
            baseDao.insert(new User(2, "netease2", "111"));
            baseDao.insert(new User(3, "netease3", "111"));
            baseDao.insert(new User(4, "netease4", "111"));
            baseDao.insert(new User(5, "netease5", "111"));
            baseDao.insert(new User(6, "netease6", "111"));
        });

        // 如何自动创建数据库
        // 如何自动创建数据表
        // 如何让用户在使用的时候非常方便
        // 将user对象里面的类名 属性 转换成 创建数据库表的sql语句
        // create table user(id integer,name text,password text);

        findViewById(R.id.select).setOnClickListener(v -> {
            // 基类  user 商品表 订单表（关联查询）
            BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
            User where = new User();
//                where.setName("netease");
            where.setPassword("111");
            List<User> list = baseDao.query(where);
            Log.e(Constants.DB_TAG, " list size is " + list.size());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).toString());
            }
        });

        findViewById(R.id.update).setOnClickListener(v -> {
            //update tb_user where name='netease' set password='1111'
//            BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
//            User user = new User();
//            user.setId(2);
//            user.setName("netease111111");
//            user.setPassword("111");
//
//            User where = new User();
//            where.setId(2);
//            baseDao.update(user, where);
            Toast.makeText(this, "执行成功!", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.delete).setOnClickListener(v -> {
            BaseDao orderDao = BaseDaoFactory.getInstance().getBaseDao(OrderDao.class, User.class);
            User where = new User();
            where.setPassword("111");
            orderDao.delete(where);
        });

        findViewById(R.id.login).setOnClickListener(v -> {
            // 服务器返回的信息
            User user = new User();
            user.setName("netease" + (i++));
            user.setPassword("154657567");
            user.setId(i);
            // 数据插入
            userDao.insert(user);
            Toast.makeText(DbActivity.this, "执行成功", Toast.LENGTH_LONG).show();
        });

        findViewById(R.id.subInsert).setOnClickListener(v -> {
            Photo photo = new Photo();
            photo.setPath("/data/data/xxx.jpg");
            photo.setTime(new Date().toString());
            BaseDao photoDao = BaseDaoSubFactory.getInstance().getBaseDao(PhotoDao.class, Photo.class);
            photoDao.insert(photo);
        });

        findViewById(R.id.newVersion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateManager updateManager = new UpdateManager();
                updateManager.startUpdateDb(DbActivity.this);
            }
        });

    }
}
