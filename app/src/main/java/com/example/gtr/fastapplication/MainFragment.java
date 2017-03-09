package com.example.gtr.fastapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * 首页的MainFragment主要负责显示与TabLayout + ViewPager相关的内容
 */

public class MainFragment extends Fragment {

    private Context context;
    private MainPagerAdapter adapter;

    private TabLayout tabLayout;

    private ZhihuDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    private DoubanMomentFragment doubanMomentFragment;

    private ZhihuDailyPresenter zhihuDailyPresenter;
    private GuokrPresenter guokrPresenter;
    private DoubanMomentPresenter doubanMomentPresenter;

    public MainFragment(){}

    public static MainFragment newInstence(){
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getActivity();

        // Fragment状态恢复
        if (savedInstanceState != null){
            FragmentManager manager = getChildFragmentManager();
            zhihuDailyFragment = (ZhihuDailyFragment) manager.getFragment(savedInstanceState, "zhihu");
            guokrFragment = (GuokrFragment) manager.getFragment(savedInstanceState, "guokr");
            doubanMomentFragment = (DoubanMomentFragment) manager.getFragment(savedInstanceState, "douban");
        }else {
            // 创建View实例
            zhihuDailyFragment= ZhihuDailyFragment.newInstance();
            guokrFragment = GuokrFragment.newInstence();
            doubanMomentFragment = DoubanMomentFragment.newInstence();
        }

        // 创建Presenter实例
        zhihuDailyPresenter = new ZhihuDailyPresenter(context, zhihuDailyFragment);
        guokrPresenter = new GuokrPresenter(context, guokrFragment);
        doubanMomentPresenter = new DoubanMomentPresenter(context, doubanMomentFragment);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_main, container, false);

        // 初始化控件
        initViews(view);

        // 显示菜单
        setHasOptionsMenu(true);

        // 当tab layout位置为果壳精选时，隐藏FloatingActionButton
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                if (tab.getPosition() == 1){
                    fab.hide();
                }else {
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    // 初始化控件
    private void initViews(View view) {

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        // 设置离线数为3
        viewPager.setOffscreenPageLimit(3);

        adapter = new MainPagerAdapter(
                getChileFragmentManager(),
                context,
                zhihuDailyFragment,
                guokrFragment,
                doubanMomentFragment);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_feel_lucky){
            feelLucky();
        }
        return true;
    }

    // 随便看看
    public void feelLucky() {
        Random random = new Random();
        int type = random.nextInt(3);
        switch (type){
            case 0:
                zhihuDailyPresenter.feelLucky();
                break;
            case 1:
                guokrPresenter.feelLucky();
                break;
            case 2:
                doubanMomentPresenter.feelLucky();
                break;
            default:
                break;
        }

    }

    // 保存状态
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getChildFragmentManager();
        manager.putFragment(outState, "zhihu", zhihuDailyFragment);
        manager.putFragment(outState, "guokr", guokrFragment);
        manager.putFragment(outState, "douban", doubanMomentFragment);
    }

    public MainPagerAdapter getAdapter(){
        return adapter;
    }
}
