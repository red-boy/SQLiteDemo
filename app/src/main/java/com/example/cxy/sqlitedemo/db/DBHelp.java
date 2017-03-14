package com.example.cxy.sqlitedemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HaodaHw on 2017/3/14.
 * 创建数据库，保存小数据
 */

public class DBHelp extends SQLiteOpenHelper {
    private final static String DBNAME = "UserInfo_db";
    private static final int VERSION = 1;

    //必须有该构造方法
    public DBHelp(Context context) {
        super(context, DBNAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String sql = "CREATE TABLE UserInfo_db (username varchar(20) not null,password varchar(20) not null,question varchar(30) not null,answer varchar(30) not null)";
        db.execSQL(sql);//执行SQL语句

    }


    /**
     * 添加数据
     * ContentValues:只能存储基本类型的数据，不能存贮对象
     */
    public void insert(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            Log.d("DBHelp", "db不为空");
            db.insert("UserInfo_db", null, contentValues);
            if (db.isOpen()) {
                db.close();
            }

        } else {
            Log.d("DBHelp", "db为空");
        }
    }

    /**
     * 查询数据:查询语句根据输入的用户名检索出password或question用户校验 query和query2方法  query3方法是查询返回一个数组（所有数据）
     */
    public String query(String userName) {
        String password = null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("UserInfo_db", new String[]{"username", "password"}, "username=?", new String[]{userName}, null, null, null);
        while (c.moveToNext()) {
            password = c.getString(c.getColumnIndex("password"));
        }
        db.close();
        c.close();
        return password;
    }

    public String query2(String userName) {
        String question = null;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("UserInfo_db", new String[]{"username", "question"}, "username=?", new String[]{userName}, null, null, null);
        while (c.moveToNext()) {
            question = c.getString(c.getColumnIndex("question"));
        }
        db.close();
        c.close();
        return question;
    }

    public String[] query3(String userName) {
        String[] result = new String[3];//存储三个数据的数组
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("UserInfo_db", new String[]{"username", "password", "question", "answer"}, "username=?", new String[]{userName}, null, null, null);
        while (c.moveToNext()) {
            result[0] = c.getString(c.getColumnIndex("password"));
            result[1] = c.getString(c.getColumnIndex("question"));
            result[2] = c.getString(c.getColumnIndex("answer"));
        }
        db.close();
        c.close();
        return result;
    }

    //判断用户名是否注册过
    public boolean isExit(String userName) {
        boolean b = false;
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("UserInfo_db", new String[]{"username"}, "username=" + userName, null, null, null, null);
        if (c.getCount() != 0) {
            b = true;
        }
        return b;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
