package com.nkdroid.apidemo.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;


/**
 * Created by nirav on 10/10/15.
 */
public class MyApplication extends Application {

    private RequestQueue mRequestQueue;
    private static MyApplication sInstance;



    public static synchronized MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;



    }


    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? "tag" : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }


    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag("tag");

        getRequestQueue().add(req);
    }


}
