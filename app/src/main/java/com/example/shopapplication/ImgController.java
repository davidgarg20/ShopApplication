package com.example.shopapplication;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class ImgController extends Application {

    public static final String TAG = ImgController.class.getSimpleName();
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private static ImgController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static synchronized ImgController getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        return this.requestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());
        }
        return this.imageLoader;
    }

    ;


}
