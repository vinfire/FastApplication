package com.example.gtr.fastapplication.detail;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.example.gtr.fastapplication.Api;
import com.example.gtr.fastapplication.NetworkState;
import com.example.gtr.fastapplication.OnStringListener;
import com.example.gtr.fastapplication.StringModelImpl;
import com.example.gtr.fastapplication.bean.BeanType;
import com.example.gtr.fastapplication.bean.DoubanMomentNews;
import com.example.gtr.fastapplication.bean.DoubanMomentStory;
import com.example.gtr.fastapplication.bean.ZhihuDailyStory;
import com.example.gtr.fastapplication.db.DatabaseHelper;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by GTR on 2017/3/8.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private Context context;
    private DetailContract.View view;
    private StringModelImpl model;
    private final SharedPreferences sp;
    private final DatabaseHelper dbHelper;
    private final Gson gson;

    private BeanType type;
    private int id;
    private String title;
    private String coverUrl;

    private ZhihuDailyStory zhihuDailyStory;
    private String guokrStory;
    private DoubanMomentStory doubanMomentStory;

    public void setType(BeanType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public DetailPresenter(Context context, DetailContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        sp = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        dbHelper = new DatabaseHelper(context, "History.db", null, 4);
        gson = new Gson();
    }

    @Override
    public void start() {

    }

    @Override
    public void openInBrowser() {

    }

    @Override
    public void shareAsText() {

    }

    @Override
    public void openUrl(WebView webView, String url) {

    }

    @Override
    public void copyText() {

    }

    @Override
    public void copyLink() {

    }

    //加收藏和取消收藏
    @Override
    public void addToOrDeleteFromBookmarks() {
        String tmpTable = "";
        String tmpId = "";
        switch (type) {
            case TYPE_ZHIHU:
                tmpTable = "Zhihu";
                tmpId = "zhihu_id";
                break;
            case TYPE_GUOKR:
                tmpTable = "Guokr";
                tmpId = "guokr_id";
                break;
            case TYPE_DOUBAN:
                tmpTable = "Douban";
                tmpId = "douban_id";
                break;
            default:
                break;
        }

        if (queryIfIsBookmarked()) {
            // delete
            // update Zhihu set bookmark = 0 where zhihu_id = id
            ContentValues values = new ContentValues();
            values.put("bookmark", 0);
            dbHelper.getWritableDatabase().update(tmpTable, values, tmpId + " = ?", new String[]{String.valueOf(id)});
            values.clear();

            view.showDeletedFromBookmarks();
        } else {
            // add
            // update Zhihu set bookmark = 1 where zhihu_id = id

            ContentValues values = new ContentValues();
            values.put("bookmark", 1);
            dbHelper.getWritableDatabase().update(tmpTable, values, tmpId + " = ?", new String[]{String.valueOf(id)});
            values.clear();

            view.showAddedToBookmarks();
        }
    }

    @Override
    public boolean queryIfIsBookmarked() {
        return false;
    }

    @Override
    public void requestData() {
        if (id==0 || type==null){
            view.showLoadingError();
            return;
        }

        view.showLoading();
        view.setTitle(title);
        view.showCover(coverUrl);

        // set the web view whether to show the image
        view.setImageMode(sp.getBoolean("no_picture_mode", false));

        switch (type){
            case TYPE_ZHIHU:
                if (NetworkState.networkConnected(context)){
                    model.load(Api.ZHIHU_NEWS + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            zhihuDailyStory = gson.fromJson(result, ZhihuDailyStory.class);
                            if (zhihuDailyStory.getBody() == null){
                                view.showResultWithoutBody(zhihuDailyStory.getShare_url());
                            }else {
                                view.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            view.showLoadingError();
                        }
                    });
                }else {
                    Cursor cursor = dbHelper.getReadableDatabase().query("Zhihu", null, null, null, null, null, null);
                    if (cursor.moveToFirst()){
                        do {
                            if (cursor.getInt(cursor.getColumnIndex("zhihu_id")) == id){
                                String content = cursor.getString(cursor.getColumnIndex("zhihu_content"));
                                zhihuDailyStory = gson.fromJson(content, ZhihuDailyStory.class);
                                view.showResult(convertZhihuContent(zhihuDailyStory.getBody()));
                                break;
                            }
                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;

            case TYPE_GUOKR:
                if (NetworkState.networkConnected(context)){
                    model.load(Api.GUOKR_ARTICLE_LINK_V1 + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            guokrStory = convertGuokrContent(result);
                            view.showResult(guokrStory);
                        }

                        @Override
                        public void onError(VolleyError error) {
                            view.showLoadingError();
                        }
                    });
                }else {
                    Cursor cursor = dbHelper.getReadableDatabase().query("Guokr", null, null, null, null, null, null);
                    if (cursor.moveToFirst()){
                        do {
                            if (cursor.getInt(cursor.getColumnIndex("guokr_id")) == id){
                                guokrStory = cursor.getString(cursor.getColumnIndex("guokr_conten"));
                                convertGuokrContent(guokrStory);
                                view.showResult(guokrStory);
                                break;
                            }
                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;

            case TYPE_DOUBAN:
                if (NetworkState.networkConnected(context)){
                    model.load(Api.DOUBAN_ARTICLE_DETAIL + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            doubanMomentStory = gson.fromJson(result, DoubanMomentStory.class);
                            view.showResult(convertDoubanContent());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            view.showLoadingError();
                        }
                    });
                }else {
                    Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select douban_content from Douban where douban_id =" + id, null);
                    if (cursor.moveToFirst()){
                        do {
                            if (cursor.getCount() == 1){
                                doubanMomentStory = gson.fromJson(cursor.getString(0), DoubanMomentStory.class);
                                view.showResult(convertDoubanContent());
                            }
                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;

            default:
                view.showLoadingError();
                break;
        }
        view.stopLoading();
    }
    private String convertZhihuContent(String preResult){

        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // in fact,in api,css addresses are given as an array
        // api中还有js的部分，这里不再解析js
        // javascript is included,but here I don't use it
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        // use the css file from local assets folder,not from network
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        // load content judging by different theme
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        //对获取的数据进行一下拼接，组成一个完整的HTML页面的内容。需要注意的是CSS文件，它负责整个HTML的样式，可以在这里查看整个CSS文件的内容或下载CSS文件。
        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }


    private String convertGuokrContent(String content) {
        // 简单粗暴的去掉下载的div部分
        String guokrStory = content.replace("<div class=\"down\" id=\"down-footer\">\n" +
                "        <img src=\"http://static.guokr.com/apps/handpick/images/c324536d.jingxuan-logo.png\" class=\"jingxuan-img\">\n" +
                "        <p class=\"jingxuan-txt\">\n" +
                "            <span class=\"jingxuan-title\">果壳精选</span>\n" +
                "            <span class=\"jingxuan-label\">早晚给你好看</span>\n" +
                "        </p>\n" +
                "        <a href=\"\" class=\"app-down\" id=\"app-down-footer\">下载</a>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"down-pc\" id=\"down-pc\">\n" +
                "        <img src=\"http://static.guokr.com/apps/handpick/images/c324536d.jingxuan-logo.png\" class=\"jingxuan-img\">\n" +
                "        <p class=\"jingxuan-txt\">\n" +
                "            <span class=\"jingxuan-title\">果壳精选</span>\n" +
                "            <span class=\"jingxuan-label\">早晚给你好看</span>\n" +
                "        </p>\n" +
                "        <a href=\"http://www.guokr.com/mobile/\" class=\"app-down\">下载</a>\n" +
                "    </div>", "");

        // 替换css文件为本地文件
        guokrStory = guokrStory.replace("<link rel=\"stylesheet\" href=\"http://static.guokr.com/apps/handpick/styles/d48b771f.article.css\" />",
                "<link rel=\"stylesheet\" href=\"file:///android_asset/guokr.article.css\" />");

        // 替换js文件为本地文件
        guokrStory = guokrStory.replace("<script src=\"http://static.guokr.com/apps/handpick/scripts/9c661fc7.base.js\"></script>",
                "<script src=\"file:///android_asset/guokr.base.js\"></script>");
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES){
            guokrStory = guokrStory.replace("<div class=\"article\" id=\"contentMain\">",
                    "<div class=\"article \" id=\"contentMain\" style=\"background-color:#212b30; color:#878787\">");
        }
        return guokrStory;
    }

    private String convertDoubanContent() {

        if (doubanMomentStory.getContent() == null) {
            return null;
        }
        String css;
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES) {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_dark.css\" type=\"text/css\">";
        } else {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_light.css\" type=\"text/css\">";
        }
        String content = doubanMomentStory.getContent();
        ArrayList<DoubanMomentNews.posts.thumbs> imageList = doubanMomentStory.getPhotos();
        for (int i = 0; i < imageList.size(); i++) {
            String old = "<img id=\"" + imageList.get(i).getTag_name() + "\" />";
            String newStr = "<img id=\"" + imageList.get(i).getTag_name() + "\" "
                    + "src=\"" + imageList.get(i).getMedium().getUrl() + "\"/>";
            content = content.replace(old, newStr);
        }
        StringBuilder builder = new StringBuilder();
        builder.append( "<!DOCTYPE html>\n");
        builder.append("<html lang=\"ZH-CN\" xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        builder.append("<head>\n<meta charset=\"utf-8\" />\n");
        builder.append(css);
        builder.append("\n</head>\n<body>\n");
        builder.append("<div class=\"container bs-docs-container\">\n");
        builder.append("<div class=\"post-container\">\n");
        builder.append(content);
        builder.append("</div>\n</div>\n</body>\n</html>");

        return builder.toString();
    }

}
