package com.example.shopapplication.ui.ui.slideshow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapplication.R;
import com.example.shopapplication.ui.mainfront;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;


public class SlideshowFragment extends Fragment {

    TextView name,mobileno,gender,profilename;
    String userid,username;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");

        SharedPreferences pref = container.getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        userid = pref.getString("UserId","-1");
        username = pref.getString("UserName","-1");
           profilename = root.findViewById(R.id.profile_fragment_header_text);
           name = root.findViewById(R.id.profile_name);
           mobileno = root.findViewById(R.id.profile_mobileno);
           gender = root.findViewById(R.id.profile_gender);

           profilename.setText(username);

        RequestQueue queue= Volley.newRequestQueue(getActivity().getBaseContext());

        String URL1 = "http://34.66.129.129/user/?format=json&u="+userid;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject obj = new JSONObject();
                try {
                    obj = response.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("slideshoew error","gbjgv");
                }
                name.setText(obj.optString("username"));
                mobileno.setText(obj.optString("mobileno"));
                gender.setText(obj.optString("gender"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

        return root;
    }
}
