package com.example.gtr.fastapplication.about;

import com.example.gtr.fastapplication.BasePresenter;
import com.example.gtr.fastapplication.BaseView;

/**
 * Created by GTR on 2017/3/8.
 */

public interface AboutContract {

    interface View extends BaseView<Presenter>{

        // 如果用户设备没有安装商店应用，提示此错误
        void showRateError();
        // 如果用户设备没有安装邮件应用，提示此错误
        void showFeedbackError();
        // 如果用户没有安装浏览器，提示此错误
        void showBrowserNotFoundError();
    }

    interface Presenter extends BasePresenter{

        // 在应用商店中评分
        void rate();
        // 展示开源许可页
        void openLicense();
        // 在GitHub上关注我
        void followOnGithub();
        // 在知乎上关注我
        void followOnZhihu();
        // 通过邮件反馈
        void feedback();
        // 捐赠
        void donate();
        // 显示小彩蛋
        void showEasterEgg();
    }
}
