package com.example.gtr.fastapplication;

/**
 * 所有的Presenter的基类
 */

public interface BasePresenter {

    //Presenter开始获取数据并改变界面显示
    //在todo-mvp的项目中的调用时机为 Fragment 的 onResume()方法中
    void start();
}
