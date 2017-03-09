package com.example.gtr.fastapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.VolleyError;
import com.example.gtr.fastapplication.bean.BeanType;
import com.example.gtr.fastapplication.db.DatabaseHelper;
import com.example.gtr.fastapplication.detail.DetailActivity;
import com.example.gtr.fastapplication.service.CacheService;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by GTR on 2017/3/5.
 */

public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private ZhihuDailyContract.View view;
    private Context context;
    private StringModelImpl model;

    private final DatabaseHelper helper;
    private final SQLiteDatabase db;

    private DateFormatter formatter = new DateFormatter();

    private Gson gson = new Gson();

    private ArrayList<ZhihuDailyNews.Question> list = new ArrayList<ZhihuDailyNews.Question>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        helper = new DatabaseHelper(context, "History.db", null, 4);
        db = helper.getWritableDatabase();
    }

    @Override
    public void start() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        if (clearing){
            view.showLoading();
        }
        if (NetworkState.networkConnected(context)){
            model.load(Api.ZHIHU_HISTORY + formatter.ZhihuDailyDateFormat(date), new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    ZhihuDailyNews post = gson.fromJson(result, ZhihuDailyNews.class);
                    ContentValues values = new ContentValues();
                    if (clearing){
                        list.clear();
                    }
                    for (ZhihuDailyNews.Question item : post.getStories()){
                        list.add(item);
                        if (!queryIfIdExists(item.getId())){
                            db.beginTransaction();
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                                Date date = format.parse(post.getDate());
                                values.put("zhihu_id", item.getId());
                                values.put("zhihu_news", gson.toJson(item));
                                values.put("zhihu_content", "");
                                values.put("zhihu_time", date.getTime()/1000);
                                db.insert("Zhihu", null, values);
                                values.clear();
                                db.setTransactionSuccessful();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }finally {
                                db.endTransaction();
                            }
                        }
                        Intent intent = new Intent("com.marktony.zhihudaily.LOCAL_BROADCAST");
                        intent.putExtra("type", CacheService.TYPE_ZHIHU);
                        intent.putExtra("id", item.getId());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                    view.showResults(list);
                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                }
            });
        }else {
            if (clearing){
                list.clear();
                Cursor cursor = db.query("Zhihu", null, null, null, null, null, null);
                if (cursor.moveToFirst()){
                    do {
                        ZhihuDailyNews.Question question = gson.fromJson(cursor.getString(cursor.getColumnIndex("zhihu_news")), ZhihuDailyNews.Question.class);
                        list.add(question);
                    }while (cursor.moveToNext());
                }
                cursor.close();
                view.stopLoading();
                view.showResults(list);
            }else {
                view.showError();
            }
        }
    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void startReading(int position) {
        context.startActivity(new Intent(context, DetailActivity.class)
                .putExtra("type", BeanType.TYPE_ZHIHU)
                .putExtra("id", list.get(position).getId())
                .putExtra("title", list.get(position).getTitle())
                .putExtra("coverUrl", list.get(position).getImages().get(0)));
    }

    @Override
    public void feelLucky() {
        if (list.isEmpty()){
            view.showError();
            return;
        }
        startReading(new Random().nextInt(list.size()));
    }

    private boolean queryIfIdExists(int id){
        Cursor cursor = db.query("Zhihu", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                if (id == cursor.getInt(cursor.getColumnIndex("zhihu_id"))){
                    cursor.close();
                    return true;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
}
