package com.akshay.mypicsumapplication.core;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class PicsumApplication extends MultiDexApplication {
    public static final String TAG = PicsumApplication.class.getSimpleName();
    private static Context mContext;
    private static RequestQueue mRequestQueue;


    static PicsumApplication picsumApplication;

    public static Context getContext() {
        if (mContext == null)
            mContext = PicsumApplication.getContext();
        return mContext;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        picsumApplication = new PicsumApplication();

    }

    public static Application getApplicationConext() {
        if (picsumApplication == null)
            picsumApplication = new PicsumApplication();

        return picsumApplication;

    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            //
            if (mContext != null)
                mRequestQueue = Volley.newRequestQueue(mContext);
        }

        return mRequestQueue;
    }

    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public static void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
