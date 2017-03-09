package com.example.gtr.fastapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GTR on 2017/3/7.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists Zhihu("
                + "id integer primary key autoincrement,"
                + "zhihu_id integer not null,"
                + "zhihu_news text,"
                + "zhihu_time real,"
                + "zhihu_content text)");

        db.execSQL("create table if not exists Guokr("
                + "id integer primary key autoincrement,"
                + "guokr_id integer not null,"
                + "guokr_news text,"
                + "guokr_time real,"
                + "guokr_content text)");

        db.execSQL("create table if not exists Douban("
                + "id integer primary key autoincrement,"
                + "douban_id integer not null,"
                + "douban_news text,"
                + "douban_time real,"
                + "douban_content text)");

        db.execSQL("alter table Zhihu add column bookmark integer default 0");
        db.execSQL("alter table Goukr add column bookmark integer default 0");
        db.execSQL("alter table Douban add column bookmark integer default 0");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:

            case 2:

            case 3:
        }
    }
}
