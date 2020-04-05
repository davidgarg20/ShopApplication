package com.example.shopapplication.ui.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapplication.R;
import com.example.shopapplication.datamodel;
import com.example.shopapplication.gridadapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopapplication.ui.Cart;
import com.example.shopapplication.ui.mainfront;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import static android.provider.Telephony.Carriers.PASSWORD;

public class HomeFragment extends Fragment {
    ArrayList<datamodel> data = new ArrayList<datamodel>();

    Integer pid,pprice,pdesprice;
    String pname,pimage,URL1;
    gridadapter customAdapter;
    RequestQueue QUEUE ;
    SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        data = new ArrayList<datamodel>();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int width = displaymetrics.widthPixels;
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        QUEUE = Volley.newRequestQueue(getActivity().getBaseContext());
        URL1 = "http://34.66.129.129/item/?format=json";

        RequestFuture<JSONArray> future = RequestFuture.newFuture();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int j = 0; j < response.length(); j++) {
                    JSONObject obj = null;
                    try {
                        obj = response.getJSONObject(j);
                        Log.d("continuation", "executed");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    pid = obj.optInt("itemid");
                    pname = obj.optString("itemname");
                    pprice = obj.optInt("maxprice");
                    pimage = obj.optString("itemimage");
                    pdesprice = obj.optInt("sellprice");
                    data.add(new datamodel(pid,pprice,pdesprice,pname,pimage));
                    Log.d("productid", pname);


                }
                ;// this will block
               customAdapter = new gridadapter(view.getContext(), data, width);
                recyclerView.setAdapter(customAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        QUEUE.add(request);

        return view;

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mainfront, menu);
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainfront, menu);
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_cart) {

            Intent intent = new Intent(getActivity().getApplicationContext(), Cart.class);
            this.startActivity(intent);
            return true;
        }

        if( id == R.id.action_search)
        {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    customAdapter.getFilter().filter(newText);
                    return false;
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {

                    return false;
                }
            });
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
