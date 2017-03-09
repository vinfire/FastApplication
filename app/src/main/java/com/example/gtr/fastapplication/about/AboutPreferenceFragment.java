package com.example.gtr.fastapplication.about;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.example.gtr.fastapplication.R;

/**
 * Created by GTR on 2017/3/8.
 */

public class AboutPreferenceFragment extends PreferenceFragmentCompat implements AboutContract.View{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.about_preference_fragment);

        initViews(getView());

        findPreference("rate").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void showRateError() {

    }

    @Override
    public void showFeedbackError() {

    }

    @Override
    public void showBrowserNotFoundError() {

    }
}
