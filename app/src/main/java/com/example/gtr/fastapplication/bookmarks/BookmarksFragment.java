package com.example.gtr.fastapplication.bookmarks;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.gtr.fastapplication.ZhihuDailyNews;
import com.example.gtr.fastapplication.bean.DoubanMomentNews;
import com.example.gtr.fastapplication.bean.GuokrHandpickNews;

import java.util.ArrayList;

/**
 * Created by GTR on 2017/3/6.
 */

public class BookmarksFragment extends Fragment implements BookmarksContract.View {

    public BookmarksFragment(){}

    public static BookmarksFragment newInstence(){
        return new BookmarksFragment();
    }

    @Override
    public void setPresenter(BookmarksContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList, ArrayList<GuokrHandpickNews.result> guokrList, ArrayList<DoubanMomentNews.posts> doubanList, ArrayList<Integer> types) {

    }

    @Override
    public void notifyDataChanged() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
