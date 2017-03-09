package com.example.gtr.fastapplication.bookmarks;

import com.example.gtr.fastapplication.BasePresenter;
import com.example.gtr.fastapplication.BaseView;
import com.example.gtr.fastapplication.ZhihuDailyNews;
import com.example.gtr.fastapplication.bean.BeanType;
import com.example.gtr.fastapplication.bean.DoubanMomentNews;
import com.example.gtr.fastapplication.bean.GuokrHandpickNews;

import java.util.ArrayList;

/**
 * Created by GTR on 2017/3/8.
 */

public interface BookmarksContract {

    interface View extends BaseView<Presenter>{

        // 显示结果
        void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList,
                         ArrayList<GuokrHandpickNews.result> guokrList,
                         ArrayList<DoubanMomentNews.posts> doubanList,
                         ArrayList<Integer> types);

        // 提示数据变化
        void notifyDataChanged();

        // 显示正在加载
        void showLoading();

        // 停止加载
        void stopLoading();
    }

    interface Presenter extends BasePresenter{

        // 请求结果
        void loadResults(boolean refresh);

        // 跳转到详情页面
        void startReading(BeanType type, int position);

        // 请求新数据
        void checkForFreshData();

        // 随便看看
        void feelLucky();
    }
}
