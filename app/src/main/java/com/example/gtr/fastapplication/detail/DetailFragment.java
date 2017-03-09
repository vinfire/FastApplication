package com.example.gtr.fastapplication.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.gtr.fastapplication.R;
import com.example.gtr.fastapplication.detail.DetailContract;

/**
 * Created by GTR on 2017/3/8.
 */

public class DetailFragment extends Fragment implements DetailContract.View{

    private Context context;
    private DetailContract.Presenter presenter;

    private SwipeRefreshLayout refreshLayout;
    private WebView webView;
    private ImageView imageView;
    private NestedScrollView scrollView;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;

    public DetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.universal_read_layout, container, false);
        initViews(view);
        setHasOptionsMenu(true);

        presenter.requestData();

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.smoothScrollTo(0,0);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestData();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_more, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home){
            getActivity().onBackPressed();
        }else if (itemId == R.id.action_more){

        }
        return true;
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);


        imageView = (ImageView) view.findViewById(R.id.image_view);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        toolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        webView = (WebView) view.findViewById(R.id.web_view);
        webView.setScrollbarFadingEnabled(true);

        DetailActivity activity = (DetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        WebSettings settings = webView.getSettings();
        //能够和js交互
        settings.setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.setBuiltInZoomControls(false);
        //缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        settings.setDomStorageEnabled(true);
        //开启application Cache功能
        settings.setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }
        });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showSharingError() {

    }

    @Override
    public void showResult(String result) {
        webView.loadDataWithBaseURL("x_data://base", result, "text/html", "utf-8", null);
    }

    @Override
    public void showResultWithoutBody(String url) {

    }

    @Override
    public void showCover(String url) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setImageMode(boolean showImage) {

    }

    @Override
    public void showBrowserNotFoundError() {

    }

    @Override
    public void showTextCopied() {

    }

    @Override
    public void showCopyTextError() {

    }

    @Override
    public void showAddedToBookmarks() {

    }

    @Override
    public void showDeletedFromBookmarks() {

    }
}
