package com.example.shopapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapplication.ui.Cart;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class product extends AppCompatActivity {

    TextView productnametextview, pricetextview, discountpricetv, unit1tv, unit2tv;
    RelativeLayout AddToCart;

    Integer itemid, price, discountprice, qty, amount;
    String itemname, unit, mImageURLString,URL2;

    Spinner spinner;
    ImageView mImageView;

    TemporaryDatabase tb;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productnametextview = findViewById(R.id.product_productname);
        pricetextview = findViewById(R.id.product_price1);
        discountpricetv = findViewById(R.id.product_discount_price1);
        unit1tv = findViewById(R.id.product_unit1);
        spinner = findViewById(R.id.product_spinner);
        AddToCart = findViewById(R.id.product_add_to_cart_layout);

        tb = new TemporaryDatabase(this);
        mImageView = findViewById(R.id.activityproduct_item_image);


        Intent i = getIntent();
        itemid = i.getIntExtra("ItemId", 0);
        price = i.getIntExtra("Price", 0);
        discountprice = i.getIntExtra("DiscountPrice", 0);
        itemname = i.getStringExtra("ItemName");
        mImageURLString = i.getStringExtra("ImageUrl");

        productnametextview.setText(itemname.toString());
        pricetextview.setText("Rs."+price.toString());
        discountpricetv.setText("Rs."+discountprice.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Initialize a new ImageRequest
        ImageRequest imageRequest = new ImageRequest(
                mImageURLString, // Image URL
                new Response.Listener<Bitmap>() { // Bitmap listener
                    @Override
                    public void onResponse(Bitmap response) {
                        // Do something with response
                        mImageView.setImageBitmap(response);
                        Log.d("productimage", response.toString());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        response.compress(Bitmap.CompressFormat.PNG, 100, stream);
                         byteArray = stream.toByteArray();
                         Log.d("bytearray",byteArray.toString());

                        // Save this downloaded bitmap to internal storage

                    }
                },
                400, // Image width
                500, // Image height
                ImageView.ScaleType.CENTER_CROP, // Image scale type
                Bitmap.Config.RGB_565, //Image decode configuration
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with error response
                        error.printStackTrace();


                    }
                }
        );

        // Add ImageRequest to the RequestQueue
        requestQueue.add(imageRequest);

        final List<String> list= new ArrayList<String>();

        URL2="http://34.66.129.129/itemunit/?format=json&i="+itemid;

        JsonArrayRequest request7 = new JsonArrayRequest(Request.Method.GET, URL2, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject obj = null;
                try {
                    obj = response.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                unit = obj.optString("unit");

                if(obj.optString("unit1")!="No Qty"){
                    list.add(obj.optString("unit1"));
                }
                if(obj.optString("unit2")!="No Qty"){
                    list.add(obj.optString("unit2"));
                }
                if(obj.optString("unit3")!="No Qty"){
                    list.add(obj.optString("unit3"));
                }
                if(obj.optString("unit4")!="No Qty"){
                    list.add(obj.optString("unit4"));
                }
                if(obj.optString("unit4")!="No Qty"){
                    list.add(obj.optString("unit1"));
                }
                if(obj.optString("unit5")!="No Qty"){
                    list.add(obj.optString("unit5"));
                }
                if(obj.optString("unit6")!="No Qty"){
                    list.add(obj.optString("unit6"));
                }
                if(obj.optString("unit7")!="No Qty"){
                    list.add(obj.optString("unit7"));
                }
                if(obj.optString("unit8")!="No Qty"){
                    list.add(obj.optString("unit8"));
                }
                if(obj.optString("unit9")!="No Qty"){
                    list.add(obj.optString("unit9"));
                }
                if(obj.optString("unit10")!="No Qty"){
                    list.add(obj.optString("unit10"));
                }
               unit1tv.setText("per"+unit);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(product.this,android.R.layout.simple_spinner_dropdown_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);


                qty = spinner.getSelectedItemPosition() +1;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request7);





        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = spinner.getSelectedItemPosition()+1;
                if(qty==0)
                    qty=1;
                amount = discountprice * qty;



                tb.insertData(itemid, itemname, qty.toString(), price, discountprice, unit, amount,byteArray);
                Intent i = new Intent(product.this, Cart.class);

                startActivity(i);

            }
        });

    }
}














