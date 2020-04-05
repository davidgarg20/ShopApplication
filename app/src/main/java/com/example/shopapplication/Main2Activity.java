package com.example.shopapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.example.shopapplication.ui.mainfront;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static android.provider.Telephony.Carriers.PASSWORD;

public class Main2Activity extends AppCompatActivity {
    TextView Signup, ForgetPassword;
    TextView Login;
    Button LoginSucess, SignupSucess;
    ImageView signupback, forgetback;
    RequestQueue QUEUE;
    LinearLayout registerform, loginform, forgetform;
    String loginmobileno, serveruserid = "0", serverusername = "0", servermobileno = "0", serverpassword, loginpassword;
    EditText textmobileno, textpassword;
    String URL, URL1;
    EditText signupname, signupmobileno, signuppassword;
    String SIGNUPNAME, SIGNUPMOBILENO, SIGNUPPASSWORD,url;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        QUEUE = Volley.newRequestQueue(this);



        LoginSucess = findViewById(R.id.login_phone_confirm);
        SignupSucess = findViewById(R.id.login_registration_confirm);
        Signup = findViewById(R.id.login_signup);
        signupback = findViewById(R.id.login_registration_close_button);
        forgetback = findViewById(R.id.login_phone_forgotten_back_button);
        ForgetPassword = findViewById(R.id.login_phone_forgotten_password);
        registerform = findViewById(R.id.login_registration_form);
        loginform = findViewById(R.id.login_phone_form);
        forgetform = findViewById(R.id.login_phone_forgotten_form);
        textmobileno = findViewById(R.id.login_phone_edittext);
        textpassword = findViewById(R.id.login_editext_password);

        signupname = findViewById(R.id.login_registration_name_auto);
        signupmobileno = findViewById(R.id.login_registration_email_text_auto);
        signuppassword = findViewById(R.id.login_register_password);

        SignupSucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 signup();
            }
        });
        LoginSucess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
               
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginform.setVisibility(View.GONE);
                registerform.setVisibility(View.VISIBLE);


            }
        });
        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginform.setVisibility(View.GONE);
                forgetform.setVisibility(View.VISIBLE);
            }
        });
        signupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginform.setVisibility(View.VISIBLE);
                registerform.setVisibility(View.GONE);
            }
        });
        forgetback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginform.setVisibility(View.VISIBLE);
                forgetform.setVisibility(View.GONE);
            }
        });

    }

    public void signup() {

        boolean b = signupvalidate();

        if (b == true) {
            final ProgressDialog progressDialog = new ProgressDialog(Main2Activity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
            url=  "https://www.fast2sms.com/dev/bulk" ;

            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("accessToken:", response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("error:", volleyError.toString());

                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("sender_id", "FSTSMS");
                    params.put("message", "8059976498: Your OTP is 4578");
                    params.put("language", "english");
                    params.put("route", "p");
                    params.put("numbers", "8059976498");
                    return params;

                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("authorization", "OsMyin4alxmAQSjBd39uw7zCEeD0rfLU82gcvVJPXkqHRZG1oYeu9kBVKJZq7sy3go2mN0Ptad8CwSIA");
                    return headers;
                }

            };

          //  QUEUE.add(postRequest);

            URL = "http://34.66.129.129/user/?format=json";
            JSONObject ob = new JSONObject();
            try { ob.put("userid","0");
                ob.put("username", SIGNUPNAME);
                ob.put("password", SIGNUPPASSWORD);
                ob.put("mobileno", SIGNUPMOBILENO);
                ob.put("gender", "Male");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, ob, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("Login", true); // Storing boolean - true/false
                    editor.putString("UserId", response.optString("userid")); // Storing string
                    editor.putString("UserName", SIGNUPNAME); // Storing integer
                    // Storing long
                    editor.commit();
                    Intent i = new Intent(Main2Activity.this, mainfront.class);
                    startActivity(i);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the reuest has either time out or there is no connection
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        //Error indicating that there was an Authentication Failure while performing the request
                        Toast.makeText(getApplicationContext(), "Authentication Failure", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                       // Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        //Indicates that there was network error while performing the request
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        // Indicates that the server response could not be parsed
                        Toast.makeText(getApplicationContext(), "Parsing Error", Toast.LENGTH_LONG).show();
                    }

                    progressDialog.dismiss();
                }
            });
            QUEUE.add(request);






        }


    };

    ;


    public boolean signupvalidate(){
        SIGNUPNAME= signupname.getText().toString(); SIGNUPMOBILENO = signupmobileno.getText().toString();SIGNUPPASSWORD= signuppassword.getText().toString();

        if(SIGNUPNAME.isEmpty()){
            signupname.setError("Name can,t be empty");
            return false;
        }
        if(SIGNUPMOBILENO.length()!=10){
            signupmobileno.setError("Invalid MobileNo.");
            return false;
        }
        if(SIGNUPPASSWORD.length()<=4 ){
            signupmobileno.setError("Password must be grater than four");
            return false;
        }
        return true;
    };

    public void login() {

        Log.d("DistributorLogin", "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        LoginSucess.setEnabled(false);


        final ProgressDialog progressDialog = new ProgressDialog(Main2Activity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        URL="http://34.66.129.129/user/?format=json&u="+loginmobileno;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int j=0;j<response.length();j++) {
                    JSONObject obj = null;
                    try {
                        obj = response.getJSONObject(j);
                        Log.d("continuation", "executed");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    serveruserid = obj.optString("id");
                    serverusername = obj.optString("name");
                    servermobileno = obj.optString("mobileno");
                    serverpassword = obj.optString("password");
                    Log.d("LoginDetailReceived",serverpassword);
                }



                    if(loginpassword.equals(serverpassword))
                    {

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("Login", true); // Storing boolean - true/false
                        editor.putString("UserId", serveruserid); // Storing string
                        editor.putString("UserName", serverusername); // Storing integer
                        // Storing long
                        editor.commit();
                        progressDialog.dismiss();
                        onLoginSuccess();
                    }
                    else
                    {   textpassword.setError("Invalid Password");
                        progressDialog.dismiss();
                        onLoginFailed();
                    }

                // this will block
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("requesterror",error.toString());
                textmobileno.setError("User not Registered");
                progressDialog.dismiss();
                onLoginFailed();

            }
        });
        QUEUE.add(request);





        // TODO: Implement your own authentication logic here.


    }



    public void onLoginSuccess() {
        LoginSucess.setEnabled(true);
        Intent i = new Intent(Main2Activity.this,mainfront.class);
        startActivity(i);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();


       LoginSucess.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        loginmobileno = textmobileno.getText().toString();
        loginpassword = textpassword.getText().toString();

        if (loginmobileno.isEmpty() || loginmobileno.length() !=10 ) {
            textmobileno.setError("enter a valid Mobile Number");
            valid = false;
        }

        if (loginpassword.isEmpty() || loginpassword.length() < 4 || loginpassword.length() > 10) {
            textpassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }

        return valid;
    };
}

