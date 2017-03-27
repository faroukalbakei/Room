package com.dev.farouk.roomx.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.orm.SugarContext;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by hamdy on 6/6/15.
 */
public class VolleySingleton extends Application {

    public static final String TAG = VolleySingleton.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private static VolleySingleton mInstance;
    private ImageLoader imageLoader;


    @Override
    public void onCreate() {
        super.onCreate();
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        mInstance = this;
        SugarContext.init(this);

    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
    public static synchronized VolleySingleton getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setShouldCache(false);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.imageLoader;
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
