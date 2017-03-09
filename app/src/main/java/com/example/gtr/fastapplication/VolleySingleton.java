package com.example.gtr.fastapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 创建VolleySingleton，即Volley的单例。这样，整个应用就可以只维护一个请求队列，加入新的网络请求也会更加的方便。
 */

public class VolleySingleton {

    private static VolleySingleton volleySingleton;
    private final RequestQueue requestQueue;

    private VolleySingleton(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static VolleySingleton getVolleySingleton(Context context){
        if (volleySingleton == null){
            synchronized(VolleySingleton.class){
                if (volleySingleton == null){
                    volleySingleton = new VolleySingleton(context);
                }
            }
        }
        return volleySingleton;
    }

    public RequestQueue getRequestQueue(){
        return this.requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
