package com.example.shopapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PreviousOrder extends AppCompatActivity {

    LinearLayout layout;
    TextView amount,deliverycharge;
    String AMOUNT,ORDERID;
    Integer DELCHARGE,TOTAL;
    TextView item,qty,itemamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order);
        layout = findViewById(R.id.layoutprev);
        amount = findViewById(R.id.prevorder_total_price);
        deliverycharge = findViewById(R.id.prevorder_deliverycharge);
        Intent i = getIntent();
        ORDERID = i.getStringExtra("orderid");
        AMOUNT = i.getStringExtra("amount");
        Log.d("pamount",AMOUNT);
        amount.setText("Rs."+AMOUNT);

        RequestQueue queue = Volley.newRequestQueue(this);

        String URL1= "http://34.66.129.129/orderdetail/?format=json&o="+ORDERID;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                LayoutInflater inflator = getLayoutInflater();


                int k = response.length();
                JSONObject jsonobject = new JSONObject();
                for(int i=0;i<k;i++)
                {
                    try {
                        jsonobject = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    View view = inflator.inflate(R.layout.productcartfinal,null);
                    layout.addView(view,layout.getChildCount());

                    item = view.findViewById(R.id.cartfinal_productListitem);
                    qty = view.findViewById(R.id.cartfinal_qtylistitem);
                    itemamount = view.findViewById(R.id.cartfinal_amountlistitem);

                    item.setText(jsonobject.optString("itemname"));
                    qty.setText(jsonobject.optString("qty"));
                    itemamount.setText("Rs."+jsonobject.optString("amount"));

                  // TOTAL = TOTAL + Integer.parseInt(jsonobject.optString("amount"));


                }
               // DELCHARGE = Integer.parseInt(AMOUNT) - TOTAL;
               // deliverycharge.setText(DELCHARGE.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);



    }
}
