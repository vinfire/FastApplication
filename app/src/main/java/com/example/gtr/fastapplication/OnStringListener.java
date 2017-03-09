package com.example.gtr.fastapplication;

import com.android.volley.VolleyError;

/**
 * Created by GTR on 2017/3/5.
 */

public interface OnStringListener {
    /*
    * 请求成功时回调
    * */
    void onSuccess(String result);

    /*
    * 请求失败时回调
    * */
    void onError(VolleyError error);
}
