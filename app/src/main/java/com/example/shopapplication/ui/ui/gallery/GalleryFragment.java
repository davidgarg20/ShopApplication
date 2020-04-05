package com.example.shopapplication.ui.ui.gallery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapplication.PreviousOrder;
import com.example.shopapplication.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;


public class GalleryFragment extends Fragment  {

    LinearLayout layout,clicklayout;

    TextView orderno,orderdate,orderamount,orderstatus;

    String userid;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        userid = pref.getString("UserId","-1");

        final View view = inflater.inflate(R.layout.fragment_gallery,
                container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Previous Order");

        layout=view.findViewById(R.id.previousorder_layout);


        RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());

        String URL1="http://34.66.129.129/order/?format=json&u="+userid;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int k = response.length();
                JSONObject obj=new JSONObject();
                for(int i=0;i<k;i++)
                {
                    try {
                        obj = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LayoutInflater inflator = getLayoutInflater();
                    View view = inflator.inflate(R.layout.prevorder_itemmain,null);
                    layout.addView(view,layout.getChildCount());
                    orderno = view.findViewById(R.id.textView11);
                    orderno.setText(obj.optString("orderno"));
                    orderdate = view.findViewById(R.id.textView13);
                    orderdate.setText(obj.optString("orderdate"));
                    orderamount = view.findViewById(R.id.textView14);
                    orderamount.setText("Rs."+obj.optString("orderamount"));
                    orderstatus = view.findViewById(R.id.textView15);
                    orderstatus.setText(obj.optString("delstatus"));
                    final String orderid=obj.optString("orderno");
                    final String amount = obj.optString("orderamount");

                    clicklayout = view.findViewById(R.id.porderlayout);

                    clicklayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity().getBaseContext(), PreviousOrder.class);
                            i.putExtra("orderid",orderid);
                            i.putExtra("amount",amount);
                            startActivity(i);
                        }
                    });



                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

        return view;
    }



}
