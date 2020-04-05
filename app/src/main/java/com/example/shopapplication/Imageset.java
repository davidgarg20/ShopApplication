package com.example.shopapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;

public class Imageset extends AppCompatActivity {

    ImageView imgview;
    String URL;
    gridadapter g;


    public Imageset(ImageView imageview ,String url){
        this.imgview = imageview;
        this.URL = url;

    }



    // Add ImageRequest to the RequestQueue

}
