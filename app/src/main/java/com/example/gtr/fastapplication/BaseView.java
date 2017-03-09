package com.example.gtr.fastapplication;

import android.view.View;

/**
 * 所有的View的基类
 */

public interface BaseView<T> {

    //为View设置Presenter，调用时机为Presenter实现类的构造方法中
    void setPresenter(T presenter);

    //初始化界面控件，调用时机为Fragment的onCreate()方法中
    void initViews(View view);
}
