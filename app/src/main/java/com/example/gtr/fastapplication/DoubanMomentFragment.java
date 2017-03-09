package com.example.gtr.fastapplication;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by GTR on 2017/3/6.
 */

public class DoubanMomentFragment extends Fragment implements DoubanMomentContract.View {

    public DoubanMomentFragment(){}

    public static DoubanMomentFragment newInstence(){
        return new DoubanMomentFragment();
    }


    @Override
    public void setPresenter(DoubanMomentContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showResults(ArrayList<ZhihuDailyNews.Question> list) {

    }

    @Override
    public void showPickDialog() {

    }
}
