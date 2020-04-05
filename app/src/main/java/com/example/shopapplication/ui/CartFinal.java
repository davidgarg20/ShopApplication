package com.example.shopapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapplication.R;
import com.example.shopapplication.TemporaryDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CartFinal extends AppCompatActivity {

    Button proceedfinal ;

    TemporaryDatabase tb;
    Button proceed;
    Cursor cursor;
    LinearLayout layout;

    TextView[] itemname, amount,qty;
    String[] ITEMNAME , UNIT,QTY;
    Integer[] AMOUNT,PRICE,ITEMID;

    TextView Total,Totalitem;
    Integer TOTAL=0;
    EditText name,street,village,pincode,city,phone,delnote;
    TextView finalamount1,finalamount2,delcharge;
    String NAME,STREET,VILLAGE,PINCODE,CITY,PHONE,DELNOTE,FINALAMOUNT,FINALAMOUNT2,DELCHARGE;
    RequestQueue queue;
    String URL1,URL2,URL3,URL4,URL5;
    String ORDERNO;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_final);
        queue = Volley.newRequestQueue(this);
        layout = findViewById(R.id.order_create_cart_items_layout);
        tb = new TemporaryDatabase(this);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        userid = pref.getString("UserId","-1");

        name= findViewById(R.id.order_create_name);
        street = findViewById(R.id.order_create_street);
        village = findViewById(R.id.order_create_houseNumber);
        city = findViewById(R.id.order_create_city);
        pincode = findViewById(R.id.order_create_zip);
        phone = findViewById(R.id.order_create_phone);
        delnote = findViewById(R.id.order_create_note);

        finalamount1 = findViewById(R.id.order_create_total_price);
        finalamount2 = findViewById(R.id.order_create_summary_total_price);
        delcharge = findViewById(R.id.cartfinal_deliverycharge);

        cursor = tb.gettemporder();
        itemname = new TextView[1000];
        amount = new TextView[1000];
        qty = new TextView[1000];

        ITEMID= new Integer[1000];
        ITEMNAME = new String[1000];
        UNIT = new String[1000];
        AMOUNT = new Integer[1000];
        PRICE = new Integer[1000];
        QTY = new String[1000];



        final int tt= cursor.getCount();

        cursor.moveToFirst();
        for(int i = 0 ; i<tt; i++)
        {   ITEMID[i] = cursor.getInt(0);
            ITEMNAME[i] = cursor.getString(1);
            QTY[i] = cursor.getString(2);
            PRICE[i] = cursor.getInt(4);
            UNIT[i]=cursor.getString(5);
            AMOUNT[i]=cursor.getInt(6);
            Log.d("cartitemnaem",ITEMNAME[i]);
            cursor.moveToNext();
        }


        for(int i=tt-1;i>=0;i--)
        {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.productcartfinal,null);
            layout.addView(rowView,layout.getChildCount());
            itemname[i]=rowView.findViewById(R.id.cartfinal_productListitem);
            qty[i]=rowView.findViewById(R.id.cartfinal_qtylistitem);
            amount[i]=rowView.findViewById(R.id.cartfinal_amountlistitem);

            itemname[i].setText(ITEMNAME[i]);
            qty[i].setText("Qty:"+QTY[i].toString());
            amount[i].setText("Rs."+AMOUNT[i].toString());

            TOTAL=TOTAL+AMOUNT[i];

        }
        proceedfinal = findViewById(R.id.order_create_finish);

        finalamount1.setText("Rs."+TOTAL.toString());
        finalamount2.setText("Rs."+TOTAL.toString());

        URL3="http://34.66.129.129/orderd/";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL3, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    DELCHARGE = response.getString("deliverycharge");
                    ORDERNO = response.getString("order");
                    Integer od = Integer.parseInt(ORDERNO);
                    ORDERNO = od.toString();
                    Integer i = Integer.parseInt(ORDERNO)+1;
                    ORDERNO = i.toString();
                     TOTAL = TOTAL + Integer.parseInt(DELCHARGE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("orderd",e.toString());
                }

                finalamount1.setText("Rs."+TOTAL.toString());
                finalamount2.setText("Rs."+TOTAL.toString());

                delcharge.setText("Rs."+DELCHARGE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("orderd",error.toString());
            }
        });

        queue.add(request);

        URL5="http://34.66.129.129/deliveryaddress/?format=json&u="+userid;

        JsonArrayRequest request6 = new JsonArrayRequest(Request.Method.GET, URL5, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                  JSONObject obj1 = new JSONObject();

                    try {
                        obj1 = response.getJSONObject(i);
                        name.setText(obj1.optString("delname"));
                        street.setText(obj1.optString("delstreet"));
                        village.setText(obj1.optString("delvillage"));
                       city.setText(obj1.optString("delcity"));
                        pincode.setText(obj1.optString("delzip"));
                        phone.setText(obj1.optString("delphone"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request6);


        proceedfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifydeldetail();

                URL4="http://34.66.129.129/deliveryaddress/?format=json";


                JSONObject object = new JSONObject();

                try {
                    object.put("userid",userid);
                    object.put("orderno",ORDERNO.toString());
                    object.put("delname",name.getText());
                    object.put("delstreet",street.getText());
                    object.put("delvillage",village.getText());
                    object.put("delcity",city.getText());
                    object.put("delzip",pincode.getText());
                    object.put("delphone",phone.getText());
                    object.put("delnote",delnote.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request5 = new JsonObjectRequest(Request.Method.POST, URL4, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(request5);
                
                JSONArray jsonarray = new JSONArray();

               URL2="http://34.66.129.129/orderdetail/?format=json";
                for(int i=0 ;i<tt;i++) {
                    JSONObject jsonobject1 = new JSONObject();
                    try {
                        jsonobject1.put("orderno",ORDERNO);
                        jsonobject1.put("itemid",ITEMID[i].toString());
                        jsonobject1.put("itemname",ITEMNAME[i].toString());
                        jsonobject1.put("qty",QTY[i].toString());
                        jsonobject1.put("amount",AMOUNT[i].toString());
                        Log.d("jobject1",ITEMID.toString());
                        Log.d("jobject2",QTY[i].toString());
                        Log.d("jobject3",AMOUNT[i].toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL2, jsonobject1, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })  ;
                    queue.add(request);

                }

                URL1="http://34.66.129.129/order/?format=json";


                JSONObject jsonobject = new JSONObject();
                    try {
                        jsonobject.put("orderno",ORDERNO);
                        jsonobject.put("userid",userid);
                        jsonobject.put("orderamount",TOTAL.toString());
                        jsonobject.put("orderdate","dbbfdb");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL1, jsonobject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })  ;
                  queue.add(request);

                Intent i = new Intent(CartFinal.this,ThankYou.class);
                startActivity(i);
            }
        });

    }

    boolean verifydeldetail() {
        NAME =  name.getText().toString();
        STREET = street.getText().toString();
        VILLAGE = village.getText().toString();
        CITY = city.getText().toString();
        PINCODE = pincode.getText().toString();
        PHONE = phone.getText().toString();
        DELNOTE = delnote.getText().toString();

        if(NAME.length()==0)
        {
            name.setError("Name can,t be empty");
            return false;
        }
        if(STREET.length()<=10)
        {
            street.setError("Street length must be greater than 10");
            return false;
        }
        if(VILLAGE.length()==0)
        {
            village.setError("Village can't be empty");
            return false;
        }
        if(PHONE.length() !=10)
        {  phone.setError("Phone can't be Empty");
            return false;
        }
        if(CITY!="HODAL")
        {
            city.setError("Currently in Hodal only");
            return false;
        }

        return true;


    }

}
