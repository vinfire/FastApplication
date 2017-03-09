package com.example.gtr.fastapplication.detail;

import android.webkit.WebView;

import com.example.gtr.fastapplication.BasePresenter;
import com.example.gtr.fastapplication.BaseView;

/**
 * Created by GTR on 2017/3/8.
 */

public class DetailContract {

    interface View extends BaseView<Presenter>{

        // 显示正在加载
        void showLoading();
        // 停止加载
        void stopLoading();
        // 显示加载错误
        void showLoadingError();
        // 显示分享时错误
        void showSharingError();
        // 正确获取数据后显示内容
        void showResult(String result);
        // 对于body字段的消息，直接接在url的内容
        void showResultWithoutBody(String url);
        // 设置顶部大图
        void showCover(String url);
        // 设置标题
        void setTitle(String title);
        // 设置是否显示图片
        void setImageMode(boolean showImage);
        // 用户选择在浏览器中打开时，如果没有安装浏览器，显示没有找到浏览器错误
        void showBrowserNotFoundError();
        // 显示已复制文字内容
        void showTextCopied();
        // 显示文字复制失败
        void showCopyTextError();
        // 显示已添加至收藏夹
        void showAddedToBookmarks();
        // 显示已从收藏夹中移除
        void showDeletedFromBookmarks();
    }

    interface Presenter extends BasePresenter{

        // 在浏览器中打开
        void openInBrowser();
        // 作为文字分享
        void shareAsText();
        // 打开文章中的链接
        void openUrl(WebView webView, String url);
        // 复制文字内容
        void copyText();
        // 复制文章链接
        void copyLink();
        // 添加至收藏夹或者从收藏夹中删除
        void addToOrDeleteFromBookmarks();
        // 查询是否已经被收藏了
        boolean queryIfIsBookmarked();
        // 请求数据
        void requestData();
    }
}
