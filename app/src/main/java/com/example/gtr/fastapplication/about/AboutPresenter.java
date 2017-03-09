package com.example.gtr.fastapplication.about;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.example.gtr.fastapplication.R;

/**
 * Created by GTR on 2017/3/8.
 */

public class AboutPresenter implements AboutContract.Presenter {

    private AppCompatActivity activity;
    private AboutContract.View view;
    private SharedPreferences sp;

    public AboutPresenter(AboutPreferenceActivity activity, AboutContract.View view) {

    }

    @Override
    public void start() {

    }

    @Override
    public void rate() {

    }

    @Override
    public void openLicense() {

    }

    @Override
    public void followOnGithub() {

    }

    @Override
    public void followOnZhihu() {

    }

    //在反馈操作中，我们是通过调用邮件App实现的
    @Override
    public void feedback() {
        try {
            Uri uri = Uri.parse(activity.getString(R.string.sendto));
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.mail_topic));
            intent.putExtra(Intent.EXTRA_TEXT,
                    activity.getString(R.string.device_model)+Build.MODEL+"\n"
                            +activity.getString(R.string.sdk_version)+Build.VERSION.RELEASE+"\n"
                            +activity.getString(R.string.version));
            activity.startActivity(intent);
        }catch (android.content.ActivityNotFoundException e){
            view.showFeedbackError();
        }
    }

    @Override
    public void donate() {

    }

    @Override
    public void showEasterEgg() {

    }
}
