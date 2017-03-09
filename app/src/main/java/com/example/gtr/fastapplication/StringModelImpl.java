package com.example.gtr.fastapplication;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * 定义一个 StringModel 的实现类 StringModelImpl
 */

public class StringModelImpl {

    private Context context;

    public StringModelImpl(Context context){
        this.context = context;
    }

    public void load(String url, final OnStringListener listener){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });

        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }
}
